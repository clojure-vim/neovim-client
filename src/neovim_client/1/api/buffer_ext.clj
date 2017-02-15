(ns neovim-client.1.api.buffer-ext
  (:require [neovim-client.1.api.buffer :as api.buffer]))

(defn get-lines
  [nvim buffer start end]
  (api.buffer/get-lines nvim buffer start end false))

(defn get-lines-async
  [nvim buffer start end f]
  (api.buffer/get-lines-async nvim buffer start end false f))
