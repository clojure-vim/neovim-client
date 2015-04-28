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

(defn get-current-line
  []
  (rpc/send-message! (->request-msg "vim_get_current_line" [])))

(defn set-current-line!
  [line]
  (rpc/send-message! (->request-msg "vim_set_current_line" [line])))

;; ***** Experimental *****

(defn hsplit! [] (run-command! "split"))
(defn vsplit! [] (run-command! "vsplit"))

;; TODO
;; vim_get_current_buffer
;; vim_get_buffers
;; (see get_api_info)
 
