(defproject uuid-plugin "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [datascript "0.15.2"]
                 [neovim-client "0.1.0-SNAPSHOT"]]
  :main ^:skip-aot uuid-plugin.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
