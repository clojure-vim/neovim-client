(defproject sample-plugin "0.1.0"
  :description "Sample Neovim plugin"
  :url "https://github.com/clojure-vim/neovim-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [neovim-client "0.1.0"]]
  :main ^:skip-aot sample-plugin.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
