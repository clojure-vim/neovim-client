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
        (swap! x inc)
        @x)))

  ;; Stay alive for a minute!
  (dotimes [n 60]
    (if (= 0 (mod n 10))
      (nvim/run-command! (str ":echo 'plugin alive for " n " seconds.'")))
    (Thread/sleep 1000))

  (nvim/run-command! ":echo 'plugin stopping.'"))
