(ns neovim-client.nvim
  (:require [clojure.core.async :as async]
            [clojure.string :as str]
            [neovim-client.message :as message :refer [->request-msg]]
            [neovim-client.rpc :as rpc]))

;; ***** Public *****

(def connect! rpc/connect!)
(def register-method! rpc/register-method!)

;; TODO - Check for connection, before allowing commands?

(defmacro defvim
  [fn-name vim-command & args]
  `(do
     (defn ~(symbol (str fn-name "-async"))
       [~@args f#]
       (rpc/send-message-async!
         (message/->request-msg ~vim-command [~@args])
         f#))
     (defn ~fn-name
       [~@args]
       (rpc/send-message!
         (message/->request-msg ~vim-command [~@args])))))

;; :let g:foo=1
;; (vim-get-var-async "foo" println) => 1
(defvim buffer-get-line "buffer_get_line" buffer line-num)
(defvim buffer-line-count "buffer_line_count" buffer)
(defvim buffer-get-name "buffer_get_name" buffer)
(defvim buffer-get-var "buffer_get_var" buffer var-name)
(defvim buffer-set-line "buffer_set_line" buffer line-num line)
(defvim vim-call-function "vim_call_function" f-name args)
(defvim vim-command "vim_command" cmd-str)
(defvim vim-get-api-info "vim_get_api_info")
(defvim vim-get-current-buffer "vim_get_current_buffer")
(defvim vim-get-current-line "vim_get_current_line")
(defvim vim-get-current-window "vim_get_current_window")
(defvim vim-get-buffers "vim_get_buffers")
(defvim vim-get-var "vim_get_var" var-name)
(defvim vim-get-vvar "vim_get_vvar" var-name)
(defvim vim-get-windows "vim_get_windows")
(defvim vim-set-current-line "vim_set_current_line" line)
(defvim window-get-buffer "window_get_buffer" window)
(defvim window-get-cursor "window_get_cursor" window)

;; Special cases
;; TODO give x, y correct names
(defvim buffer-get-line-slice* "buffer_get_line_slice" buffer start end x y)
(defn buffer-get-line-slice
  [buffer start end]
  (buffer-get-line-slice* buffer start end true true))
(defn buffer-get-line-slice-async
  [buffer start end f]
  (buffer-get-line-slice*-async buffer start end true true f))

;; ***** Custom *****

;; TODO - fix custom names, move to another ns?

(defn get-cursor-location-async
  "Gets the cursor's current position as a tuple (row, col) asynchronously."
  [f]
  (vim-get-current-window-async #(window-get-cursor-async % f)))

(defn buffer-update-lines!
  "Alter each line of the buffer using the function."
  [buffer update-fn]
  (doseq [n (range (buffer-line-count buffer))]
    (buffer-get-line-async buffer n #(buffer-set-line-async buffer n
                                                            (update-fn %)
                                                            identity))))
(defn get-current-buffer-text-async
  "Convenience function to get the current buffer's text asynchronously."
  [f]
  (vim-get-current-buffer-async
    (fn [buffer] (buffer-get-line-slice-async
                   buffer 0 -1 (fn [lines] (f (str/join "\n" lines)))))))

(defn buffer-set-text!
  "Replace the contents of the buffer with `text`."
  [buffer text]
  (let [lines (str/split text #"\n")]
    (doseq [n (range (count lines))]
      (buffer-set-line buffer n (nth lines n)))))

(defn get-current-word-async
  "Get the current word asynchronously."
  [f]
  (vim-call-function-async "expand" ["<cword>"] f))

(defn buffer-visible?-async
  "Returns a channel which will contain true if the buffer is currently
  visible."
  [buffer-name]
  (async/go
    (let [visible-buffers (map (comp buffer-get-name window-get-buffer)
                               (vim-get-windows))]
      ((set visible-buffers) buffer-name))))
