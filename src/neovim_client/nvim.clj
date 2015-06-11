(ns neovim-client.nvim
  (:require [clojure.string :as str]
            [neovim-client.message :refer [->request-msg]]
            [neovim-client.rpc :as rpc]))

;; ***** Public *****

(def connect! rpc/connect!)
(def register-method! rpc/register-method!)

;; TODO - Check for connection, before allowing commands?

(defn send-message!
  [vim-command & args]
  (rpc/send-message! (->request-msg vim-command (or args []))))

(defn run-command!  [cmd-str] (send-message! "vim_command" cmd-str))

(defn run-command-async!
  [cmd-str f]
  (rpc/send-message-async! (->request-msg "vim_command" [cmd-str]) f))

(defn get-api-info [] (send-message! "vim_get_api_info"))

(defn get-current-line [] (send-message! "vim_get_current_line"))

(defn set-current-line!  [line] (send-message! "vim_set_current_line" line))

(defn get-current-buffer [] (send-message! "vim_get_current_buffer"))

(defn get-buffers [] (send-message! "vim_get_buffers"))

(defn get-buffer-line-count [buffer] (send-message! "buffer_line_count" buffer))

(defn buffer-set-line!
  [buffer line-num line]
  (send-message! "buffer_set_line" buffer line-num line))

(defn buffer-set-line-async!
  ([buffer line-num line]
   (buffer-set-line-async! buffer line-num line identity))
  ([buffer line-num line f]
   (rpc/send-message-async! (->request-msg "buffer_set_line"
                                           [buffer line-num line]) f)))

(defn buffer-get-line
  [buffer line-num]
  (send-message! "buffer_get_line" buffer line-num))

(defn buffer-get-line-async
  [buffer line-num f]
  (rpc/send-message-async! (->request-msg "buffer_get_line"
                                          [buffer line-num]) f))

;; ***** Experimental *****

(defn buffer-update-lines!
  "Alter each line of the buffer using the function."
  [buffer update-fn]
  (doseq [n (range (get-buffer-line-count buffer))]
    (buffer-get-line-async buffer n #(buffer-set-line-async! buffer n
                                                             (update-fn %)))))

(defn buffer-get-text
  "Get the buffer's text as a single string."
  [buffer]
  (let [buf-size (get-buffer-line-count buffer)
        lines (map (partial buffer-get-line buffer) (range buf-size))]
    (str/join "\n" lines)))

;; TODO - make it wipe out text in the buffer after the last line of new text.
(defn buffer-set-text!
  [buffer text]
  (let [lines (str/split text #"\n")]
    (doseq [n (range (count lines))]
      (buffer-set-line! buffer n (nth lines n)))))

(defn hsplit! [] (run-command! "new"))
(defn vsplit! [] (run-command! "vnew"))

;; TODO
;; the rest of the functions in (get_api_info)
