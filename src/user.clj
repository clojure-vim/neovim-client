(ns user
  (:require
    [neovim-client.nvim :as nvim]))

(defn go
  "Start the plugin."
  []
  (nvim/connect! "localhost" 7777))
