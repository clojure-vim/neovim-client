(ns neovim-client.0.api (:require [neovim-client.nvim]))

(defn buffer-get-line [component buffer index] (neovim-client.nvim/exec component "buffer_get_line" buffer index))

(defn buffer-get-line-async [component buffer index f] (neovim-client.nvim/exec-async component "buffer_get_line" buffer index f))

(defn buffer-set-line [component buffer index line] (neovim-client.nvim/exec component "buffer_set_line" buffer index line))

(defn buffer-set-line-async [component buffer index line f] (neovim-client.nvim/exec-async component "buffer_set_line" buffer index line f))

(defn buffer-del-line [component buffer index] (neovim-client.nvim/exec component "buffer_del_line" buffer index))

(defn buffer-del-line-async [component buffer index f] (neovim-client.nvim/exec-async component "buffer_del_line" buffer index f))

(defn buffer-get-line-slice [component buffer start end include_start include_end] (neovim-client.nvim/exec component "buffer_get_line_slice" buffer start end include_start include_end))

(defn buffer-get-line-slice-async [component buffer start end include_start include_end f] (neovim-client.nvim/exec-async component "buffer_get_line_slice" buffer start end include_start include_end f))

(defn buffer-set-line-slice [component buffer start end include_start include_end replacement] (neovim-client.nvim/exec component "buffer_set_line_slice" buffer start end include_start include_end replacement))

(defn buffer-set-line-slice-async [component buffer start end include_start include_end replacement f] (neovim-client.nvim/exec-async component "buffer_set_line_slice" buffer start end include_start include_end replacement f))

(defn buffer-set-var [component buffer name value] (neovim-client.nvim/exec component "buffer_set_var" buffer name value))

(defn buffer-set-var-async [component buffer name value f] (neovim-client.nvim/exec-async component "buffer_set_var" buffer name value f))

(defn buffer-del-var [component buffer name] (neovim-client.nvim/exec component "buffer_del_var" buffer name))

(defn buffer-del-var-async [component buffer name f] (neovim-client.nvim/exec-async component "buffer_del_var" buffer name f))

(defn buffer-insert [component buffer lnum lines] (neovim-client.nvim/exec component "buffer_insert" buffer lnum lines))

(defn buffer-insert-async [component buffer lnum lines f] (neovim-client.nvim/exec-async component "buffer_insert" buffer lnum lines f))

(defn tabpage-set-var [component tabpage name value] (neovim-client.nvim/exec component "tabpage_set_var" tabpage name value))

(defn tabpage-set-var-async [component tabpage name value f] (neovim-client.nvim/exec-async component "tabpage_set_var" tabpage name value f))

(defn tabpage-del-var [component tabpage name] (neovim-client.nvim/exec component "tabpage_del_var" tabpage name))

(defn tabpage-del-var-async [component tabpage name f] (neovim-client.nvim/exec-async component "tabpage_del_var" tabpage name f))

(defn ui-attach [component width height enable_rgb] (neovim-client.nvim/exec component "ui_attach" width height enable_rgb))

(defn ui-attach-async [component width height enable_rgb f] (neovim-client.nvim/exec-async component "ui_attach" width height enable_rgb f))

(defn set-var [component name value] (neovim-client.nvim/exec component "vim_set_var" name value))

(defn set-var-async [component name value f] (neovim-client.nvim/exec-async component "vim_set_var" name value f))

(defn del-var [component name] (neovim-client.nvim/exec component "vim_del_var" name))

(defn del-var-async [component name f] (neovim-client.nvim/exec-async component "vim_del_var" name f))

(defn window-set-var [component window name value] (neovim-client.nvim/exec component "window_set_var" window name value))

(defn window-set-var-async [component window name value f] (neovim-client.nvim/exec-async component "window_set_var" window name value f))

(defn window-del-var [component window name] (neovim-client.nvim/exec component "window_del_var" window name))

(defn window-del-var-async [component window name f] (neovim-client.nvim/exec-async component "window_del_var" window name f))

(defn ui-detach [component] (neovim-client.nvim/exec component "ui_detach"))

(defn ui-detach-async [component f] (neovim-client.nvim/exec-async component "ui_detach" f))

(defn ui-try-resize [component width height] (neovim-client.nvim/exec component "ui_try_resize" width height))

(defn ui-try-resize-async [component width height f] (neovim-client.nvim/exec-async component "ui_try_resize" width height f))

(defn command [component command] (neovim-client.nvim/exec component "vim_command" command))

(defn command-async [component command f] (neovim-client.nvim/exec-async component "vim_command" command f))

(defn feedkeys [component keys mode escape_csi] (neovim-client.nvim/exec component "vim_feedkeys" keys mode escape_csi))

(defn feedkeys-async [component keys mode escape_csi f] (neovim-client.nvim/exec-async component "vim_feedkeys" keys mode escape_csi f))

(defn input [component keys] (neovim-client.nvim/exec component "vim_input" keys))

(defn input-async [component keys f] (neovim-client.nvim/exec-async component "vim_input" keys f))

(defn replace-termcodes [component str from_part do_lt special] (neovim-client.nvim/exec component "vim_replace_termcodes" str from_part do_lt special))

(defn replace-termcodes-async [component str from_part do_lt special f] (neovim-client.nvim/exec-async component "vim_replace_termcodes" str from_part do_lt special f))

(defn command-output [component str] (neovim-client.nvim/exec component "vim_command_output" str))

(defn command-output-async [component str f] (neovim-client.nvim/exec-async component "vim_command_output" str f))

(defn eval [component expr] (neovim-client.nvim/exec component "vim_eval" expr))

(defn eval-async [component expr f] (neovim-client.nvim/exec-async component "vim_eval" expr f))

(defn call-function [component fname args] (neovim-client.nvim/exec component "vim_call_function" fname args))

(defn call-function-async [component fname args f] (neovim-client.nvim/exec-async component "vim_call_function" fname args f))

(defn strwidth [component str] (neovim-client.nvim/exec component "vim_strwidth" str))

(defn strwidth-async [component str f] (neovim-client.nvim/exec-async component "vim_strwidth" str f))

(defn list-runtime-paths [component] (neovim-client.nvim/exec component "vim_list_runtime_paths"))

(defn list-runtime-paths-async [component f] (neovim-client.nvim/exec-async component "vim_list_runtime_paths" f))

(defn change-directory [component dir] (neovim-client.nvim/exec component "vim_change_directory" dir))

(defn change-directory-async [component dir f] (neovim-client.nvim/exec-async component "vim_change_directory" dir f))

(defn get-current-line [component] (neovim-client.nvim/exec component "vim_get_current_line"))

(defn get-current-line-async [component f] (neovim-client.nvim/exec-async component "vim_get_current_line" f))

(defn set-current-line [component line] (neovim-client.nvim/exec component "vim_set_current_line" line))

(defn set-current-line-async [component line f] (neovim-client.nvim/exec-async component "vim_set_current_line" line f))

(defn del-current-line [component] (neovim-client.nvim/exec component "vim_del_current_line"))

(defn del-current-line-async [component f] (neovim-client.nvim/exec-async component "vim_del_current_line" f))

(defn get-var [component name] (neovim-client.nvim/exec component "vim_get_var" name))

(defn get-var-async [component name f] (neovim-client.nvim/exec-async component "vim_get_var" name f))

(defn get-vvar [component name] (neovim-client.nvim/exec component "vim_get_vvar" name))

(defn get-vvar-async [component name f] (neovim-client.nvim/exec-async component "vim_get_vvar" name f))

(defn get-option [component name] (neovim-client.nvim/exec component "vim_get_option" name))

(defn get-option-async [component name f] (neovim-client.nvim/exec-async component "vim_get_option" name f))

(defn set-option [component name value] (neovim-client.nvim/exec component "vim_set_option" name value))

(defn set-option-async [component name value f] (neovim-client.nvim/exec-async component "vim_set_option" name value f))

(defn out-write [component str] (neovim-client.nvim/exec component "vim_out_write" str))

(defn out-write-async [component str f] (neovim-client.nvim/exec-async component "vim_out_write" str f))

(defn err-write [component str] (neovim-client.nvim/exec component "vim_err_write" str))

(defn err-write-async [component str f] (neovim-client.nvim/exec-async component "vim_err_write" str f))

(defn report-error [component str] (neovim-client.nvim/exec component "vim_report_error" str))

(defn report-error-async [component str f] (neovim-client.nvim/exec-async component "vim_report_error" str f))

(defn get-buffers [component] (neovim-client.nvim/exec component "vim_get_buffers"))

(defn get-buffers-async [component f] (neovim-client.nvim/exec-async component "vim_get_buffers" f))

(defn get-current-buffer [component] (neovim-client.nvim/exec component "vim_get_current_buffer"))

(defn get-current-buffer-async [component f] (neovim-client.nvim/exec-async component "vim_get_current_buffer" f))

(defn set-current-buffer [component buffer] (neovim-client.nvim/exec component "vim_set_current_buffer" buffer))

(defn set-current-buffer-async [component buffer f] (neovim-client.nvim/exec-async component "vim_set_current_buffer" buffer f))

(defn get-windows [component] (neovim-client.nvim/exec component "vim_get_windows"))

(defn get-windows-async [component f] (neovim-client.nvim/exec-async component "vim_get_windows" f))

(defn get-current-window [component] (neovim-client.nvim/exec component "vim_get_current_window"))

(defn get-current-window-async [component f] (neovim-client.nvim/exec-async component "vim_get_current_window" f))

(defn set-current-window [component window] (neovim-client.nvim/exec component "vim_set_current_window" window))

(defn set-current-window-async [component window f] (neovim-client.nvim/exec-async component "vim_set_current_window" window f))

(defn get-tabpages [component] (neovim-client.nvim/exec component "vim_get_tabpages"))

(defn get-tabpages-async [component f] (neovim-client.nvim/exec-async component "vim_get_tabpages" f))

(defn get-current-tabpage [component] (neovim-client.nvim/exec component "vim_get_current_tabpage"))

(defn get-current-tabpage-async [component f] (neovim-client.nvim/exec-async component "vim_get_current_tabpage" f))

(defn set-current-tabpage [component tabpage] (neovim-client.nvim/exec component "vim_set_current_tabpage" tabpage))

(defn set-current-tabpage-async [component tabpage f] (neovim-client.nvim/exec-async component "vim_set_current_tabpage" tabpage f))

(defn subscribe [component event] (neovim-client.nvim/exec component "vim_subscribe" event))

(defn subscribe-async [component event f] (neovim-client.nvim/exec-async component "vim_subscribe" event f))

(defn unsubscribe [component event] (neovim-client.nvim/exec component "vim_unsubscribe" event))

(defn unsubscribe-async [component event f] (neovim-client.nvim/exec-async component "vim_unsubscribe" event f))

(defn name-to-color [component name] (neovim-client.nvim/exec component "vim_name_to_color" name))

(defn name-to-color-async [component name f] (neovim-client.nvim/exec-async component "vim_name_to_color" name f))

(defn get-color-map [component] (neovim-client.nvim/exec component "vim_get_color_map"))

(defn get-color-map-async [component f] (neovim-client.nvim/exec-async component "vim_get_color_map" f))

(defn get-api-info [component] (neovim-client.nvim/exec component "vim_get_api_info"))

(defn get-api-info-async [component f] (neovim-client.nvim/exec-async component "vim_get_api_info" f))