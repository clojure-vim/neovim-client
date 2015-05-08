(ns neovim-client.message)

;; TODO - do this right!
(defn msg-id
  "Get a unique message id."
  []
  (System/currentTimeMillis))

(defn ->request-msg
  "Construct a msgpack-rpc request message."
  [type args]
  [0 (msg-id) type args])

(defn ->response-msg
  [id result]
  [1 id nil result])

(def id second)

;; TODO - find better way to get [B type.
(def byte-array-type (type (.getBytes "foo")))

(defn bytes->str
    [t]

    (condp = (type t)

      byte-array-type
      (String. t)

      t))

(def value (comp bytes->str last))

(def msg-type first)

;; TODO going to need to do bytes->str here too!
(defn method [msg] (bytes->str (nth msg 2)))

(defn params [msg] (nth msg 3))
