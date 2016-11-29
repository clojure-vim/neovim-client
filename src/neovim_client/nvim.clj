(ns neovim-client.nvim
  (:require [clojure.core.async :as async]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [neovim-client.message :as message :refer [->request-msg]]
            [neovim-client.rpc :as rpc])
  (:import (java.net Socket)))

(defmacro defvim
  [fn-name vim-command & args]
  `(do
     (defn ~(symbol (str fn-name "-async"))
       [nvim# ~@args f#]
       (rpc/send-message-async!
         nvim#
         (message/->request-msg ~vim-command [~@args])
         f#))
     (defn ~fn-name
       [nvim# ~@args]
       (rpc/send-message!
         nvim#
         (message/->request-msg ~vim-command [~@args])))))

;; :let g:foo=1
;; (vim-get-var-async "foo" println) => 1
(defvim buffer-get-line "buffer_get_line" buffer line-num)
(defvim buffer-line-count "buffer_line_count" buffer)
(defvim buffer-get-name "buffer_get_name" buffer)
(defvim buffer-get-number "buffer_get_number" buffer)
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
(defvim vim-set-current-buffer "vim_set_current_buffer" buffer)
(defvim vim-set-current-line "vim_set_current_line" line)
(defvim vim-set-current-window "vim_set_current_window" window)
(defvim window-get-buffer "window_get_buffer" window)
(defvim window-get-cursor "window_get_cursor" window)

;; Special cases
;; TODO give x, y correct names
(defvim buffer-get-line-slice* "buffer_get_line_slice" buffer start end x y)
(defn buffer-get-line-slice
  [nvim buffer start end]
  (buffer-get-line-slice* nvim buffer start end true true))
(defn buffer-get-line-slice-async
  [nvim buffer start end f]
  (buffer-get-line-slice*-async nvim buffer start end true true f))

;; ***** Custom *****

;; TODO - fix custom names, move to another ns?

(defn get-cursor-location
  "Gets the cursor's current position as a tuple (row, col)."
  [nvim]
  (window-get-cursor nvim (vim-get-current-window nvim)))

(defn get-cursor-location-async
  "Gets the cursor's current position as a tuple (row, col) asynchronously."
  [nvim f]
  (vim-get-current-window-async nvim #(window-get-cursor-async nvim % f)))

(defn buffer-update-lines!
  "Alter each line of the buffer using the function."
  [nvim buffer update-fn]
  (doseq [n (range (buffer-line-count nvim buffer))]
    (buffer-get-line-async nvim buffer n #(buffer-set-line-async nvim
                                                                 buffer n
                                                                 (update-fn %)
                                                                 identity))))
(defn get-current-buffer-text
  "Convenience function to get the current buffer's text."
  [nvim]
  (str/join "\n" (buffer-get-line-slice nvim (vim-get-current-buffer nvim)
                                        0 -1)))

(defn get-current-buffer-text-async
  "Convenience function to get the current buffer's text asynchronously."
  [nvim f]
  (vim-get-current-buffer-async
    nvim
    (fn [buffer] (buffer-get-line-slice-async
                   nvim buffer 0 -1 (fn [lines] (f (str/join "\n" lines)))))))

(defn buffer-set-text!
  "Replace the contents of the buffer with `text`."
  [nvim buffer text]
  (let [lines (str/split text #"\n")]
    (doseq [n (range (count lines))]
      (buffer-set-line nvim buffer n (nth lines n)))))

(defn get-current-word-async
  "Get the current word asynchronously."
  [nvim f]
  (vim-call-function-async nvim "expand" ["<cword>"] f))

(defn buffer-visible?-async
  "Returns a channel which will contain true if the buffer is currently
  visible."
  [nvim buffer-name]
  (async/go
    (let [visible-buffers (map (comp #(buffer-get-name nvim %)
                                     #(window-get-buffer nvim %))
                               (vim-get-windows nvim))]
      ((set visible-buffers) buffer-name))))

(def register-method! rpc/register-method!)
(def stop rpc/stop)

(defn new*
  [input output debug]
  (let [component (rpc/new input output)]
    ;; Each time you connect to the same nvim instance using a tcp socket, nvim
    ;; allocates a new channel.
    (when debug
      (let [api-info (vim-get-api-info component)]
        (vim-command component (format "let g:nvim_tcp_plugin_channel = %s"
                                       (first api-info)))))
    component))

(defn new
  "Connect to msgpack-rpc channel via standard io or TCP socket."
  ([] (new* System/in System/out) false)
  ([host port]
   (log/info "plugin host connecting to nvim at " host ":" port)
   (let [socket (java.net.Socket. host port)]
     (.setTcpNoDelay socket true)
     (new* (.getInputStream socket) (.getOutputStream socket) true))))
