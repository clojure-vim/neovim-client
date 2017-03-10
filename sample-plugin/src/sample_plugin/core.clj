(ns sample-plugin.core
  (:require
    [neovim-client.1.api :as api]
    [neovim-client.nvim :as nvim])
  (:gen-class))

(defn -main
  [& args]
  (let [nvim (nvim/new 1)
        x (atom 0)]
    (nvim/register-method!
      nvim
      "count"
      (fn [msg]
        ;; Plugin can call back to nvim if it wants to, while
        ;; its doing its own thing.
        (swap! x inc)
        (api/command-async nvim
                           (format ":echo 'incrementing ... %s'" @x)
                           (fn [_] nil))))

    ;; Stay alive for a minute!
    (dotimes [n 60]
      (if (= 0 (mod n 10))
        (api/command nvim (str ":echo 'plugin alive for " n " seconds.'")))
      (Thread/sleep 1000))

    ;; Let nvim know we're shutting down.
    (api/command nvim ":let g:is_running=0")
    (api/command nvim ":echo 'plugin stopping.'")))
