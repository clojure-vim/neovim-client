(ns user
  (:require
    [neovim-client.nvim :as nvim]
    [clojure.tools.namespace.repl :refer [refresh]]))

(defn go
  "Start the plugin."
  []
  (nvim/connect! "localhost" 7777))
