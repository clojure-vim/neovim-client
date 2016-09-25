(defproject neovim-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 ;; TODO: upgrade to this
                 ;; [org.clojure/core.async "0.2.391"]
                 [org.clojure/tools.logging "0.3.1"]
                 [clj-logging-config "1.9.12"]
                 [clojure-msgpack "1.0.0"]]
  :repl-options {:init-ns neovim-client.nvim}
  ;; TODO: remove this
  ;;:jvm-opts ["-Dclojure.server.repl={:port 5555 :accept clojure.core.server/repl}"]
  :main ^:skip-aot neovim-client.socket-repl-plugin
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
