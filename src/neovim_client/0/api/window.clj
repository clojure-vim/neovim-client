(ns neovim-client.0.api.window (:require [neovim-client.nvim]))

(defn get-buffer [component window] (neovim-client.nvim/exec component "window_get_buffer" window))

(defn get-buffer-async [component window f] (neovim-client.nvim/exec-async component "window_get_buffer" window f))

(defn get-cursor [component window] (neovim-client.nvim/exec component "window_get_cursor" window))

(defn get-cursor-async [component window f] (neovim-client.nvim/exec-async component "window_get_cursor" window f))

(defn set-cursor [component window pos] (neovim-client.nvim/exec component "window_set_cursor" window pos))

(defn set-cursor-async [component window pos f] (neovim-client.nvim/exec-async component "window_set_cursor" window pos f))

(defn get-height [component window] (neovim-client.nvim/exec component "window_get_height" window))

(defn get-height-async [component window f] (neovim-client.nvim/exec-async component "window_get_height" window f))

(defn set-height [component window height] (neovim-client.nvim/exec component "window_set_height" window height))

(defn set-height-async [component window height f] (neovim-client.nvim/exec-async component "window_set_height" window height f))

(defn get-width [component window] (neovim-client.nvim/exec component "window_get_width" window))

(defn get-width-async [component window f] (neovim-client.nvim/exec-async component "window_get_width" window f))

(defn set-width [component window width] (neovim-client.nvim/exec component "window_set_width" window width))

(defn set-width-async [component window width f] (neovim-client.nvim/exec-async component "window_set_width" window width f))

(defn get-var [component window name] (neovim-client.nvim/exec component "window_get_var" window name))

(defn get-var-async [component window name f] (neovim-client.nvim/exec-async component "window_get_var" window name f))

(defn get-option [component window name] (neovim-client.nvim/exec component "window_get_option" window name))

(defn get-option-async [component window name f] (neovim-client.nvim/exec-async component "window_get_option" window name f))

(defn set-option [component window name value] (neovim-client.nvim/exec component "window_set_option" window name value))

(defn set-option-async [component window name value f] (neovim-client.nvim/exec-async component "window_set_option" window name value f))

(defn get-position [component window] (neovim-client.nvim/exec component "window_get_position" window))

(defn get-position-async [component window f] (neovim-client.nvim/exec-async component "window_get_position" window f))

(defn get-tabpage [component window] (neovim-client.nvim/exec component "window_get_tabpage" window))

(defn get-tabpage-async [component window f] (neovim-client.nvim/exec-async component "window_get_tabpage" window f))

(defn is-valid [component window] (neovim-client.nvim/exec component "window_is_valid" window))

(defn is-valid-async [component window f] (neovim-client.nvim/exec-async component "window_is_valid" window f))