(ns neovim-client.1.api-ext
  (:require
    [clojure.core.async :as async]
    [clojure.string :as string]
    [neovim-client.1.api :as api]
    [neovim-client.1.api.buffer :as api.buffer]
    [neovim-client.1.api.buffer-ext :as api.buffer-ext]
    [neovim-client.1.api.window :as api.window]))

(defn get-cursor-location
  "Gets the cursor's current position as a tuple (row, col).
  row - starts at 1
  col - starts at 0"
  [nvim]
  (api.window/get-cursor nvim (api/get-current-win nvim)))

(defn get-current-buffer-text
  "Convenience function to get the current buffer's text."
  [nvim]
  (string/join "\n" (api.buffer-ext/get-lines
                      nvim (api/get-current-buf nvim) 0 -1)))

(defn get-current-word-async
  "Get the current word asynchronously."
  [nvim f]
  (api/call-function-async nvim "expand" ["<cword>"] f))

(defn buffer-visible?-async
  "Returns a channel which will contain true if the buffer is currently
  visible."
  [nvim buffer-name]
  (async/thread
    (let [visible-buffers (map (comp #(api.buffer/get-name nvim %)
                                     #(api.window/get-buf nvim %))
                               (api/list-wins nvim))]
      ((set visible-buffers) buffer-name))))
