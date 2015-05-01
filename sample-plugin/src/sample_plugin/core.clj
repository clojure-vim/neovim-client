(ns sample-plugin.core
  (:require [neovim-client.nvim :as nvim])
  (:gen-class))

(defn -main
  [& args]
  (nvim/connect!)
  (nvim/hsplit!)
  (let [cur-buf (nvim/get-current-buffer)]
    (nvim/buffer-set-text! cur-buf "Sample plugin was here!")))

;; TODO docs
;; add following line to your .vimrc for vundle
