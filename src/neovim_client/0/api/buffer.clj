(ns neovim-client.0.api.buffer (:require [neovim-client.nvim]))

(defn line-count [component buffer] (neovim-client.nvim/exec component "buffer_line_count" buffer))

(defn line-count-async [component buffer f] (neovim-client.nvim/exec-async component "buffer_line_count" buffer f))

(defn get-lines [component buffer start end strict_indexing] (neovim-client.nvim/exec component "buffer_get_lines" buffer start end strict_indexing))

(defn get-lines-async [component buffer start end strict_indexing f] (neovim-client.nvim/exec-async component "buffer_get_lines" buffer start end strict_indexing f))

(defn set-lines [component buffer start end strict_indexing replacement] (neovim-client.nvim/exec component "buffer_set_lines" buffer start end strict_indexing replacement))

(defn set-lines-async [component buffer start end strict_indexing replacement f] (neovim-client.nvim/exec-async component "buffer_set_lines" buffer start end strict_indexing replacement f))

(defn get-var [component buffer name] (neovim-client.nvim/exec component "buffer_get_var" buffer name))

(defn get-var-async [component buffer name f] (neovim-client.nvim/exec-async component "buffer_get_var" buffer name f))

(defn get-option [component buffer name] (neovim-client.nvim/exec component "buffer_get_option" buffer name))

(defn get-option-async [component buffer name f] (neovim-client.nvim/exec-async component "buffer_get_option" buffer name f))

(defn set-option [component buffer name value] (neovim-client.nvim/exec component "buffer_set_option" buffer name value))

(defn set-option-async [component buffer name value f] (neovim-client.nvim/exec-async component "buffer_set_option" buffer name value f))

(defn get-number [component buffer] (neovim-client.nvim/exec component "buffer_get_number" buffer))

(defn get-number-async [component buffer f] (neovim-client.nvim/exec-async component "buffer_get_number" buffer f))

(defn get-name [component buffer] (neovim-client.nvim/exec component "buffer_get_name" buffer))

(defn get-name-async [component buffer f] (neovim-client.nvim/exec-async component "buffer_get_name" buffer f))

(defn set-name [component buffer name] (neovim-client.nvim/exec component "buffer_set_name" buffer name))

(defn set-name-async [component buffer name f] (neovim-client.nvim/exec-async component "buffer_set_name" buffer name f))

(defn is-valid [component buffer] (neovim-client.nvim/exec component "buffer_is_valid" buffer))

(defn is-valid-async [component buffer f] (neovim-client.nvim/exec-async component "buffer_is_valid" buffer f))

(defn get-mark [component buffer name] (neovim-client.nvim/exec component "buffer_get_mark" buffer name))

(defn get-mark-async [component buffer name f] (neovim-client.nvim/exec-async component "buffer_get_mark" buffer name f))

(defn add-highlight [component buffer src_id hl_group line col_start col_end] (neovim-client.nvim/exec component "buffer_add_highlight" buffer src_id hl_group line col_start col_end))

(defn add-highlight-async [component buffer src_id hl_group line col_start col_end f] (neovim-client.nvim/exec-async component "buffer_add_highlight" buffer src_id hl_group line col_start col_end f))

(defn clear-highlight [component buffer src_id line_start line_end] (neovim-client.nvim/exec component "buffer_clear_highlight" buffer src_id line_start line_end))

(defn clear-highlight-async [component buffer src_id line_start line_end f] (neovim-client.nvim/exec-async component "buffer_clear_highlight" buffer src_id line_start line_end f))