(ns neovim-client.rpc
  (:require [clojure.core.async :as async]
            [msgpack.core :as msgpack]
            [neovim-client.message :refer [unpack-rec id value]])
  (:import [java.io DataInputStream DataOutputStream]
           [java.net Socket]))

(def out-chan (atom nil))
(def in-chan (atom nil))
(def msg-table (atom {}))

(defn create-input-channel
  "Read messages from the input stream, put them on a channel."
  [input-stream]
  (let [chan (async/chan)
        input-stream (DataInputStream. input-stream)]
    (async/go
      (while true
        (println "*** waiting for input stream ***")
        (let [msg (unpack-rec (msgpack/unpack-stream input-stream))]
          (println "***")
          (println "stream -> msg -> in chan: " msg)
          (println "***")
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
          (println "***")
          (println "stream <- msg <- out chan: " msg)
          (println "***")
          (write-msg! (msgpack/pack msg) output-stream))))
    chan))

(defn connect*
  [input-stream output-stream]
  (reset! in-chan (create-input-channel input-stream))
  (reset! out-chan (create-output-channel output-stream))

  ;; Handle stuff on the input channel -- where should this live?
  (async/go
    (while true
      ;; TODO - let the in-chan, if we leave this code here.
      ;; TODO - support requests from the server?
      (let [msg (async/<! @in-chan)
            fn (:fn (get @msg-table (id msg)))]
        (swap! msg-table dissoc (id msg))
        (fn (value msg))))))

;; ***** Public *****

(defn connect!
  "Connect to msgpack-rpc channel via standard io or TCP socket."
  ([] (connect* System/in System/out))
  ([host port]
   (let [socket (java.net.Socket. host port)]
     (connect* (.getInputStream socket) (.getOutputStream socket)))))

(defn send-message-async!
  [msg callback-fn]
  (swap! msg-table assoc (id msg) {:msg msg :fn callback-fn})
  (async/put! @out-chan msg))

(defn send-message!
  [msg]
  (let [p (promise)]
    (send-message-async! msg (partial deliver p))
    @p))
