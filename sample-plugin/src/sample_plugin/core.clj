(ns sample-plugin.core
  (:require [neovim-client.nvim :as nvim])
  (:gen-class))

(defn -main
  [& args]
  (nvim/connect!)

  (let [x (atom 0)]
    (nvim/register-method!
      "count"
      (fn [msg]
        ;; Plugin can call back to nvim if it wants to, while
        ;; its doing its own thing.
        (nvim/vim-command-async ":echo 'incrementing'"
                                 (fn [_] nil))
        (swap! x inc)
        @x)))

  ;; Stay alive for a minute!
  (dotimes [n 60]
    (if (= 0 (mod n 10))
      (nvim/vim-command (str ":echo 'plugin alive for " n " seconds.'")))
    (Thread/sleep 1000))

  ;; Let nvim know we're shutting down.
  (nvim/vim-command ":let g:is_running=0")
  (nvim/vim-command ":echo 'plugin stopping.'"))
