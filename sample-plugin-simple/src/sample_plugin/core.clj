(ns sample-plugin.core
  (:require [neovim-client.nvim :as nvim])
  (:gen-class))

(defn -main
  [& args]
  (let [nvim (nvim/new)
        cur-buf (nvim/vim-get-current-buffer nvim)]
    (nvim/vim-command nvim ":new")
    (nvim/buffer-set-text! nvim cur-buf "Sample plugin was here!")))
