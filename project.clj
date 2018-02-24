(defproject neovim-client "0.1.2"
  :description "Client library for the Neovim RPC API"
  :url "https://github.com/clojure-vim/neovim-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [org.clojure/core.async "0.3.442"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                 javax.jms/jms
                                                 com.sun.jmdk/jmxtools
                                                 com.sun.jmx/jmxri]]
                 [clj-logging-config "1.9.12"]
                 [clojure-msgpack "1.2.0"]
                 [uk.co.caprica/juds "0.94.1"]]
  :src-paths ["src" "test"]
  :target-path "target/%s")
