(defproject socket-repl-plugin "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [neovim-client "0.1.0-SNAPSHOT"]]
  :main ^:skip-aot socket-repl.socket-repl-plugin
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
