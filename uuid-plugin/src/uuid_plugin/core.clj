(ns uuid-plugin.core
  (:require [neovim-client.nvim :as nvim]
            [datascript.core :as d])
  #_(:gen-class))

(defn -main
  [& args]
  (nvim/connect!)

  (nvim/register-method!
    "uuid"
    (fn [msg] (pr-str (d/squuid))))

  ;; Stay alive for a minute!
  (dotimes [n 60]
    (if (= 0 (mod n 10))
      (nvim/run-command! (str ":echo 'plugin alive for " n " seconds.'")))
    (Thread/sleep 1000))

  ;; Let nvim know we're shutting down.
  (nvim/run-command! ":let g:is_running=0")
  (nvim/run-command! ":echo 'plugin stopping.'"))
