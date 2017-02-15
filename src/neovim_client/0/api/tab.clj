(ns neovim-client.0.api.tab (:require [neovim-client.nvim]))

(defn get-windows [component tabpage] (neovim-client.nvim/exec component "tabpage_get_windows" tabpage))

(defn get-windows-async [component tabpage f] (neovim-client.nvim/exec-async component "tabpage_get_windows" tabpage f))

(defn get-var [component tabpage name] (neovim-client.nvim/exec component "tabpage_get_var" tabpage name))

(defn get-var-async [component tabpage name f] (neovim-client.nvim/exec-async component "tabpage_get_var" tabpage name f))

(defn get-window [component tabpage] (neovim-client.nvim/exec component "tabpage_get_window" tabpage))

(defn get-window-async [component tabpage f] (neovim-client.nvim/exec-async component "tabpage_get_window" tabpage f))

(defn is-valid [component tabpage] (neovim-client.nvim/exec component "tabpage_is_valid" tabpage))

(defn is-valid-async [component tabpage f] (neovim-client.nvim/exec-async component "tabpage_is_valid" tabpage f))