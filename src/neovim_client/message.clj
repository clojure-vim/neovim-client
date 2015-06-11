(ns neovim-client.message)

(def +request+ 0)
(def +response+ 1)

(defn gen-msg-id
  "Get a unique message id."
  []
  (System/nanoTime))

(defn ->request-msg
  "Construct a msgpack-rpc request message."
  [type args]
  [0 (gen-msg-id) type args])

(defn ->response-msg
  "Construct a msgpack-rpc response message."
  [id result]
  [1 id nil result])


;; TODO - find better way to get [B type.
(def byte-array-type (type (.getBytes "foo")))

(defn bytes->str
    [t]

    (condp = (type t)

      byte-array-type
      (String. t)

      t))

;; ***** Accessor fns *****
(def msg-type first)
(def id second)

(defn request? [msg] (= +request+ (msg-type msg)))
(defn response? [msg] (= +response+ (msg-type msg)))

(defn value
  [msg]
  {:pre [(response? msg)]}
  (bytes->str (last msg)))

(defn method
  [msg]
  {:pre [(request? msg)]}
  (bytes->str (nth msg 2)))

(defn params
  [msg]
  {:pre [(request? msg)]}
  (last msg))
