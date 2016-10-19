(ns sample-plugin.core
  (:require [neovim-client.nvim :as nvim])
  (:gen-class))

(defn -main
  [& args]
  (nvim/connect!)
  (nvim/vim-command ":new")
  (let [cur-buf (nvim/vim-get-current-buffer)]
    (nvim/buffer-set-text! cur-buf "Sample plugin was here!")))
