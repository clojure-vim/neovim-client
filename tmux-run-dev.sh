# Development run script #

# Generate .classpath from Leiningen if project.clj has changed.
if [[ project.clj -nt .classpath ]]; then
    lein classpath > .classpath
fi

tmux new-window -n 'nvim-debug'
tmux send-keys "NVIM_LISTEN_ADDRESS=127.0.0.1:7777 nvim" C-m
tmux split
tmux send-keys 'java -cp "$(cat .classpath)" clojure.main' C-m
tmux send-keys '(println "Plugin repl connecting to nvim")' C-m
tmux send-keys '(go)' C-m
