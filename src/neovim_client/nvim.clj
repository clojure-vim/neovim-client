(ns neovim-client.nvim
  (:require [neovim-client.message :refer [->request-msg]]
            [neovim-client.rpc :as rpc]))

(def connect! rpc/connect!)

;; TODO - Check for connection, before allowing commands?

(defn run-command!
  [cmd-str]
  (rpc/send-message! (->request-msg "vim_command" [cmd-str])))

(defn get-api-info
  []
  (rpc/send-message! (->request-msg "vim_get_api_info" [])))

;; TODO
;msg [0 (System/currentTimeMillis) "vim_get_current_line" []]
;msg [0 (System/currentTimeMillis) "vim_get_current_buffer" []]
;msg [0 (System/currentTimeMillis) "vim_get_buffers" []]
 
