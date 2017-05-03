(ns neovim-client.2.api (:require [neovim-client.nvim]))

(defn ui-attach [component width height options] (neovim-client.nvim/exec component "nvim_ui_attach" width height options))

(defn ui-attach-async [component width height options f] (neovim-client.nvim/exec-async component "nvim_ui_attach" width height options f))

(defn ui-detach [component] (neovim-client.nvim/exec component "nvim_ui_detach"))

(defn ui-detach-async [component f] (neovim-client.nvim/exec-async component "nvim_ui_detach" f))

(defn ui-try-resize [component width height] (neovim-client.nvim/exec component "nvim_ui_try_resize" width height))

(defn ui-try-resize-async [component width height f] (neovim-client.nvim/exec-async component "nvim_ui_try_resize" width height f))

(defn ui-set-option [component name value] (neovim-client.nvim/exec component "nvim_ui_set_option" name value))

(defn ui-set-option-async [component name value f] (neovim-client.nvim/exec-async component "nvim_ui_set_option" name value f))

(defn command [component command] (neovim-client.nvim/exec component "nvim_command" command))

(defn command-async [component command f] (neovim-client.nvim/exec-async component "nvim_command" command f))

(defn feedkeys [component keys mode escape_csi] (neovim-client.nvim/exec component "nvim_feedkeys" keys mode escape_csi))

(defn feedkeys-async [component keys mode escape_csi f] (neovim-client.nvim/exec-async component "nvim_feedkeys" keys mode escape_csi f))

(defn input [component keys] (neovim-client.nvim/exec component "nvim_input" keys))

(defn input-async [component keys f] (neovim-client.nvim/exec-async component "nvim_input" keys f))

(defn replace-termcodes [component str from_part do_lt special] (neovim-client.nvim/exec component "nvim_replace_termcodes" str from_part do_lt special))

(defn replace-termcodes-async [component str from_part do_lt special f] (neovim-client.nvim/exec-async component "nvim_replace_termcodes" str from_part do_lt special f))

(defn command-output [component str] (neovim-client.nvim/exec component "nvim_command_output" str))

(defn command-output-async [component str f] (neovim-client.nvim/exec-async component "nvim_command_output" str f))

(defn eval [component expr] (neovim-client.nvim/exec component "nvim_eval" expr))

(defn eval-async [component expr f] (neovim-client.nvim/exec-async component "nvim_eval" expr f))

(defn call-function [component fname args] (neovim-client.nvim/exec component "nvim_call_function" fname args))

(defn call-function-async [component fname args f] (neovim-client.nvim/exec-async component "nvim_call_function" fname args f))

(defn strwidth [component str] (neovim-client.nvim/exec component "nvim_strwidth" str))

(defn strwidth-async [component str f] (neovim-client.nvim/exec-async component "nvim_strwidth" str f))

(defn list-runtime-paths [component] (neovim-client.nvim/exec component "nvim_list_runtime_paths"))

(defn list-runtime-paths-async [component f] (neovim-client.nvim/exec-async component "nvim_list_runtime_paths" f))

(defn set-current-dir [component dir] (neovim-client.nvim/exec component "nvim_set_current_dir" dir))

(defn set-current-dir-async [component dir f] (neovim-client.nvim/exec-async component "nvim_set_current_dir" dir f))

(defn get-current-line [component] (neovim-client.nvim/exec component "nvim_get_current_line"))

(defn get-current-line-async [component f] (neovim-client.nvim/exec-async component "nvim_get_current_line" f))

(defn set-current-line [component line] (neovim-client.nvim/exec component "nvim_set_current_line" line))

(defn set-current-line-async [component line f] (neovim-client.nvim/exec-async component "nvim_set_current_line" line f))

(defn del-current-line [component] (neovim-client.nvim/exec component "nvim_del_current_line"))

(defn del-current-line-async [component f] (neovim-client.nvim/exec-async component "nvim_del_current_line" f))

(defn get-var [component name] (neovim-client.nvim/exec component "nvim_get_var" name))

(defn get-var-async [component name f] (neovim-client.nvim/exec-async component "nvim_get_var" name f))

(defn set-var [component name value] (neovim-client.nvim/exec component "nvim_set_var" name value))

(defn set-var-async [component name value f] (neovim-client.nvim/exec-async component "nvim_set_var" name value f))

(defn del-var [component name] (neovim-client.nvim/exec component "nvim_del_var" name))

(defn del-var-async [component name f] (neovim-client.nvim/exec-async component "nvim_del_var" name f))

(defn get-vvar [component name] (neovim-client.nvim/exec component "nvim_get_vvar" name))

(defn get-vvar-async [component name f] (neovim-client.nvim/exec-async component "nvim_get_vvar" name f))

(defn get-option [component name] (neovim-client.nvim/exec component "nvim_get_option" name))

(defn get-option-async [component name f] (neovim-client.nvim/exec-async component "nvim_get_option" name f))

(defn set-option [component name value] (neovim-client.nvim/exec component "nvim_set_option" name value))

(defn set-option-async [component name value f] (neovim-client.nvim/exec-async component "nvim_set_option" name value f))

(defn out-write [component str] (neovim-client.nvim/exec component "nvim_out_write" str))

(defn out-write-async [component str f] (neovim-client.nvim/exec-async component "nvim_out_write" str f))

(defn err-write [component str] (neovim-client.nvim/exec component "nvim_err_write" str))

(defn err-write-async [component str f] (neovim-client.nvim/exec-async component "nvim_err_write" str f))

(defn err-writeln [component str] (neovim-client.nvim/exec component "nvim_err_writeln" str))

(defn err-writeln-async [component str f] (neovim-client.nvim/exec-async component "nvim_err_writeln" str f))

(defn list-bufs [component] (neovim-client.nvim/exec component "nvim_list_bufs"))

(defn list-bufs-async [component f] (neovim-client.nvim/exec-async component "nvim_list_bufs" f))

(defn get-current-buf [component] (neovim-client.nvim/exec component "nvim_get_current_buf"))

(defn get-current-buf-async [component f] (neovim-client.nvim/exec-async component "nvim_get_current_buf" f))

(defn set-current-buf [component buffer] (neovim-client.nvim/exec component "nvim_set_current_buf" buffer))

(defn set-current-buf-async [component buffer f] (neovim-client.nvim/exec-async component "nvim_set_current_buf" buffer f))

(defn list-wins [component] (neovim-client.nvim/exec component "nvim_list_wins"))

(defn list-wins-async [component f] (neovim-client.nvim/exec-async component "nvim_list_wins" f))

(defn get-current-win [component] (neovim-client.nvim/exec component "nvim_get_current_win"))

(defn get-current-win-async [component f] (neovim-client.nvim/exec-async component "nvim_get_current_win" f))

(defn set-current-win [component window] (neovim-client.nvim/exec component "nvim_set_current_win" window))

(defn set-current-win-async [component window f] (neovim-client.nvim/exec-async component "nvim_set_current_win" window f))

(defn list-tabpages [component] (neovim-client.nvim/exec component "nvim_list_tabpages"))

(defn list-tabpages-async [component f] (neovim-client.nvim/exec-async component "nvim_list_tabpages" f))

(defn get-current-tabpage [component] (neovim-client.nvim/exec component "nvim_get_current_tabpage"))

(defn get-current-tabpage-async [component f] (neovim-client.nvim/exec-async component "nvim_get_current_tabpage" f))

(defn set-current-tabpage [component tabpage] (neovim-client.nvim/exec component "nvim_set_current_tabpage" tabpage))

(defn set-current-tabpage-async [component tabpage f] (neovim-client.nvim/exec-async component "nvim_set_current_tabpage" tabpage f))

(defn subscribe [component event] (neovim-client.nvim/exec component "nvim_subscribe" event))

(defn subscribe-async [component event f] (neovim-client.nvim/exec-async component "nvim_subscribe" event f))

(defn unsubscribe [component event] (neovim-client.nvim/exec component "nvim_unsubscribe" event))

(defn unsubscribe-async [component event f] (neovim-client.nvim/exec-async component "nvim_unsubscribe" event f))

(defn get-color-by-name [component name] (neovim-client.nvim/exec component "nvim_get_color_by_name" name))

(defn get-color-by-name-async [component name f] (neovim-client.nvim/exec-async component "nvim_get_color_by_name" name f))

(defn get-color-map [component] (neovim-client.nvim/exec component "nvim_get_color_map"))

(defn get-color-map-async [component f] (neovim-client.nvim/exec-async component "nvim_get_color_map" f))

(defn get-api-info [component] (neovim-client.nvim/exec component "nvim_get_api_info"))

(defn get-api-info-async [component f] (neovim-client.nvim/exec-async component "nvim_get_api_info" f))

(defn call-atomic [component calls] (neovim-client.nvim/exec component "nvim_call_atomic" calls))

(defn call-atomic-async [component calls f] (neovim-client.nvim/exec-async component "nvim_call_atomic" calls f))