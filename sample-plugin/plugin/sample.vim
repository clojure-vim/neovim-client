let s:p_dir = expand('<sfile>:p:h')

function! RunSamplePlugin()
    "TODO - This is a dirty hack. We should launch things without changing
    "the working directory.
    exec ':cd ' . s:p_dir
    call rpcstart('lein', ['run'])
endfunction

echo "sample plugin loaded!"
