(ns neovim-client.rpc
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :as log]
            [msgpack.core :as msgpack]
            [neovim-client.message :refer [id value msg-type method params
                                           ->response-msg]])
  (:import [java.io DataInputStream DataOutputStream]
           [java.net Socket]))

(def out-chan (atom nil))
(def in-chan (atom nil))
(def msg-table (atom {}))
(def method-table (atom {}))

(defn method-not-found
  [msg]
  (log/error "method not found for msg " msg)
  (str "method not found - " (method msg)))

(defn create-input-channel
  "Read messages from the input stream, put them on a channel."
  [input-stream]
  (let [chan (async/chan)
        input-stream (DataInputStream. input-stream)]
    (async/go
      (while true
        (log/info "waiting for input stream")
        (let [msg (msgpack/unpack-stream input-stream)]
          (log/info "stream -> msg -> in chan: " msg)
          (async/>! chan msg))))
    chan))

(defn write-msg!
  [packed-msg out-stream]
  (doseq [b packed-msg]
    (.writeByte out-stream b))
  (.flush out-stream))

(defn create-output-channel
  "Make a channel to read messages from, write to output stream."
  [output-stream]
  (let [chan (async/chan)
        output-stream (DataOutputStream. output-stream)]
    (async/go
      (while true
        (let [msg (async/<! chan)]
          (log/info "stream <- msg <- out chan: " msg)
          (write-msg! (msgpack/pack msg) output-stream))))
    chan))

(declare send-message-async!)

(defn connect*
  [input-stream output-stream]
  (reset! in-chan (create-input-channel input-stream))
  (reset! out-chan (create-output-channel output-stream))

  ;; Handle stuff on the input channel -- where should this live?
  (async/go
    (while true
      ;; TODO - let the in-chan, if we leave this code here.
      (let [msg (async/<! @in-chan)]
        ;; TODO magic numbers!
        (cond (= 1 (msg-type msg)) ;; Response
              (let [f (:fn (get @msg-table (id msg)))]
                (swap! msg-table dissoc (id msg))
                (f (value msg)))

              (= 0 (msg-type msg)) ;; Request
              (let [f (get @method-table (method msg) method-not-found)
                    result (f msg)]
                (send-message-async! (->response-msg (id msg) result) nil)))))))
;; ***** Public *****

(defn connect!
  "Connect to msgpack-rpc channel via standard io or TCP socket."
  ([] (connect* System/in System/out))
  ([host port]
   (let [socket (java.net.Socket. host port)]
     (connect* (.getInputStream socket) (.getOutputStream socket)))))

(defn send-message-async!
  [msg callback-fn]
  ;; TODO magic number!
  (if (= 0 (msg-type msg))
    (swap! msg-table assoc (id msg) {:msg msg :fn callback-fn}))
  (async/put! @out-chan msg))

(defn send-message!
  [msg]
  (let [p (promise)]
    (send-message-async! msg (partial deliver p))
    @p))

(defn register-method!
  [method f]
  (swap! method-table assoc method f))
