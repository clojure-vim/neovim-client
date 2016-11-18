tmux new-window -n "nvim logs"
tmux send-keys "tail -f ~/.nvimlog" C-m
tmux split
tmux send-keys "tail -f /tmp/nvim-clojure.log" C-m
