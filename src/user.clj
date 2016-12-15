(ns user
  (:require
    [neovim-client.nvim :as nvim]
    [clojure.tools.namespace.repl :refer [refresh]]))

(defn tcp-connection
  "Get an nvim connection."
  []
  (nvim/new "localhost" 7777))
