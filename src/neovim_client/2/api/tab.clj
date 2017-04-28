(ns neovim-client.2.api.tab (:require [neovim-client.nvim]))

(defn list-wins [component tabpage] (neovim-client.nvim/exec component "nvim_tabpage_list_wins" tabpage))

(defn list-wins-async [component tabpage f] (neovim-client.nvim/exec-async component "nvim_tabpage_list_wins" tabpage f))

(defn get-var [component tabpage name] (neovim-client.nvim/exec component "nvim_tabpage_get_var" tabpage name))

(defn get-var-async [component tabpage name f] (neovim-client.nvim/exec-async component "nvim_tabpage_get_var" tabpage name f))

(defn set-var [component tabpage name value] (neovim-client.nvim/exec component "nvim_tabpage_set_var" tabpage name value))

(defn set-var-async [component tabpage name value f] (neovim-client.nvim/exec-async component "nvim_tabpage_set_var" tabpage name value f))

(defn del-var [component tabpage name] (neovim-client.nvim/exec component "nvim_tabpage_del_var" tabpage name))

(defn del-var-async [component tabpage name f] (neovim-client.nvim/exec-async component "nvim_tabpage_del_var" tabpage name f))

(defn get-win [component tabpage] (neovim-client.nvim/exec component "nvim_tabpage_get_win" tabpage))

(defn get-win-async [component tabpage f] (neovim-client.nvim/exec-async component "nvim_tabpage_get_win" tabpage f))

(defn get-number [component tabpage] (neovim-client.nvim/exec component "nvim_tabpage_get_number" tabpage))

(defn get-number-async [component tabpage f] (neovim-client.nvim/exec-async component "nvim_tabpage_get_number" tabpage f))

(defn is-valid [component tabpage] (neovim-client.nvim/exec component "nvim_tabpage_is_valid" tabpage))

(defn is-valid-async [component tabpage f] (neovim-client.nvim/exec-async component "nvim_tabpage_is_valid" tabpage f))