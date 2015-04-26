(ns neovim-client.message)

;; TODO - find better way to get [B type.
(def byte-array-type (type (.getBytes "foo")))

(defn unpack-rec
    [t]
    (if (coll? t)
      (cons (unpack-rec (first t)) (unpack-rec (next t)))
      (if (= byte-array-type (type t))
        (String. t)
        t)))

;; TODO - do this right!
(defn msg-id
  "Get a unique message id."
  []
  (System/currentTimeMillis))

(defn ->request-msg
  "Construct a msgpack-rpc request message."
  [type args]
  [0 (msg-id) type args])
