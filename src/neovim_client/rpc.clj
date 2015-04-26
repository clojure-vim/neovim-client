(ns neovim-client.rpc
  (:require [clojure.core.async :as async]
            [msgpack.core :as msgpack])
  (:import [java.io DataInputStream DataOutputStream]
           [java.net Socket]))

(def out-chan (atom nil))
(def in-chan (atom nil))

(defn create-input-channel
  "Read messages from the input stream, put them on a channel."
  [input-stream]
  nil)

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
          (println "msg out: " msg)
          (println "***")
          (write-msg! (msgpack/pack msg) output-stream))))
    chan))

(defn connect*
  [input-stream output-stream]
  (reset! in-chan (create-input-channel input-stream))
  (reset! out-chan (create-output-channel output-stream)))

;; ***** Public *****

(defn connect!
  "Connect to msgpack-rpc channel via standard io or TCP socket."
  ([] (connect* System/in System/out))
  ([host port]
   (let [socket (java.net.Socket. host port)]
     (connect* (.getInputStream socket) (.getOutputStream socket)))))

(defn send-message!
  [msg]
  (async/put! @out-chan msg))
