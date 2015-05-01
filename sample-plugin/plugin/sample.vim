let s:p_dir = expand('<sfile>:p:h')
let s:lein_pre = 'cd ' . s:p_dir . '; lein run'

function! RunSamplePlugin()
    call rpcstart('lein', ['run'])
endfunction

echo "sample plugin loaded!"
