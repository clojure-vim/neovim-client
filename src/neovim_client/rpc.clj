(ns neovim-client.rpc
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :as log]
            [msgpack.core :as msgpack]
            [neovim-client.message :refer [id value msg-type method params
                                           ->response-msg]
                                   :as msg])
  (:import [java.io DataInputStream DataOutputStream]
           [java.net Socket]))

(defn- method-not-found
  [msg]
  (log/error "method not found for msg " msg)
  (str "method not found - " (method msg)))

(defn- create-input-channel
  "Read messages from the input stream, put them on a channel."
  [input-stream]
  (let [chan (async/chan 1024)
        input-stream (DataInputStream. input-stream)]
    (async/go-loop
      []
      (when-let [msg (msgpack/unpack-stream input-stream)]
        (log/info "stream -> msg -> in chan: " msg)
        (async/>! chan msg)
        (recur)))
    [chan input-stream]))

(defn- write-msg!
  [packed-msg out-stream]
  (doseq [b packed-msg]
    (.writeByte out-stream b))
  (.flush out-stream))

(defn- create-output-channel
  "Make a channel to read messages from, write to output stream."
  [output-stream]
  (let [chan (async/chan 1024)
        output-stream (DataOutputStream. output-stream)]
    (async/go-loop
      []
      (when-let [msg (async/<! chan)]
        (log/info "stream <- msg <- out chan: " msg)
        (write-msg! (msgpack/pack msg) output-stream)
        (recur)))
    chan))

;; ***** Public *****

(defn send-message-async!
  [{:keys [message-table out-chan]} msg callback-fn]
  (if (= msg/+request+ (msg-type msg))
    (swap! message-table assoc (id msg) {:msg msg :fn callback-fn}))
  (async/put! out-chan msg))

(defn send-message!
  [component msg]
  (let [p (promise)]
    (send-message-async! component msg (partial deliver p))
    @p))

(defn register-method!
  [{:keys [method-table]} method f]
  (swap! method-table assoc method f))

(defn stop
  "Stop the connection. Right now, this probably only works for debug, when
  connected to socket. Don't think we should be trying to .close STDIO streams."
  [{:keys [input-stream output-stream out-chan in-chan data-stream]}]
  (async/close! out-chan)
  (async/close! in-chan)
  (.close data-stream)
  (.close input-stream)
  (.close output-stream))

(defn new*
  [input-stream output-stream]
  (let [[in-chan data-stream] (create-input-channel input-stream)
        message-table (atom {})
        method-table (atom {})
        component {:input-stream input-stream
                   :data-stream data-stream
                   :output-stream output-stream
                   :out-chan (create-output-channel output-stream) 
                   :in-chan in-chan
                   :message-table message-table
                   :method-table method-table}]
    (future (loop
      []
      (when-let [msg (async/<!! in-chan)]
        (condp = (msg-type msg)

          msg/+response+
          (let [f (:fn (get @message-table (id msg)))]
            (swap! message-table dissoc (id msg))
            (f (value msg)))

          msg/+request+
          (let [f (get @method-table (method msg) method-not-found)
                result (f msg)]
            (send-message-async!
              component (->response-msg (id msg) result) nil)))
        (recur))))
    component))

(defn new
  "Connect to msgpack-rpc channel via standard io or TCP socket."
  ([] (new* System/in System/out))
  ([host port]
   (let [socket (java.net.Socket. host port)]
     (.setTcpNoDelay socket true)
     (new* (.getInputStream socket) (.getOutputStream socket)))))
