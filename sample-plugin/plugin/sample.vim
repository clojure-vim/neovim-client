let s:p_dir = expand('<sfile>:p:h')
let s:lein_pre = 'cd ' . s:p_dir . '; lein run'

function! <SID>RunSamplePlugin()
    let cmd = s:jar_pre
    exec('!echo "' . cmd . '"; ' . cmd)
endfunction

echo "sample plugin loaded!"
