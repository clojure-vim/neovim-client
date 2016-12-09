(ns neovim-client.message)

(def +request+ 0)
(def +response+ 1)
(def +notify+ 2)

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

;; Only true for request & response, notify has no id
(def id second)

(defn request? [msg] (= +request+ (msg-type msg)))
(defn response? [msg] (= +response+ (msg-type msg)))
(defn notify? [msg] (= +notify+ (msg-type msg)))

(defn value
  [msg]
  {:pre [(response? msg)]}
  (bytes->str (last msg)))

(defn method
  [msg]
  {:pre [(or (request? msg) (notify? msg))]}
  (bytes->str (nth (reverse msg) 1)))

(defn params
  [msg]
  {:pre [(or (request? msg) (response? msg) (notify? msg))]}
  (last msg))
