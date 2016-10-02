(ns user
  (:require
    [socket-repl.socket-repl-plugin :as plugin]))

(defn go
  "Start the plugin."
  []
  (plugin/start true))
