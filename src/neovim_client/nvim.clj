(ns neovim-client.nvim
  (:require [neovim-client.message :refer [->request-msg]]
            [neovim-client.rpc :as rpc]))

;; ***** Public *****

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

(defn get-current-buffer
  []
  (rpc/send-message! (->request-msg "vim_get_current_buffer" [])))

(defn get-buffers
  []
  (rpc/send-message! (->request-msg "vim_get_buffers" [])))

(defn get-buffer-line-count
  [buffer]
  (rpc/send-message! (->request-msg "buffer_line_count" [buffer])))

(defn buffer-set-line!
  [buffer line-num line]
  (rpc/send-message! (->request-msg "buffer_set_line" [buffer line-num line])))

;; ***** Experimental *****

(defn hsplit! [] (run-command! "split"))
(defn vsplit! [] (run-command! "vsplit"))

;; TODO
;; the rest of the functions in (get_api_info)
