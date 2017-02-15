(ns neovim-client.test-helper)

(defn neovim
  "Make a neovim subprocess."
  []
  (let [p (.exec (Runtime/getRuntime) "nvim --embed")]
    {:process p
     :in (.getInputStream p)
     :out (.getOutputStream p)}))

(defn stop-neovim
  [{:keys [process]}]
  (.destroy process))

(defmacro with-neovim
  [& body]
  `(let [~'*neovim* (neovim)]
     (try
       ~@body
       (finally (stop-neovim ~'*neovim*)))))
