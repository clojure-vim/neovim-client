let s:p_dir = expand('<sfile>:p:h')
let g:sample_is_running = 0
let g:nvim_tcp_plugin_channel = 0

function! SampleStartIfNotRunning()
    if g:sample_is_running == 0
        echo 'starting plugin...'
        let jar_file_path = s:p_dir . '/../' . 'sample-plugin-0.1.0-standalone.jar'
        call jobstart(['java', '-jar', jar_file_path], {'rpc': v:true})
        let g:sample_is_running = 1
    endif
endfunction

function! SamplePluginCount()
    call SampleStartIfNotRunning()
    let res = rpcnotify(g:nvim_tcp_plugin_channel, 'count', [])
    return res
endfunction

echo 'sample plugin counter loaded!'
