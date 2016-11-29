(defproject neovim-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [org.clojure/core.async "0.2.391"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                 javax.jms/jms
                                                 com.sun.jmdk/jmxtools
                                                 com.sun.jmx/jmxri]]
                 [clj-logging-config "1.9.12"]
                 [clojure-msgpack "1.2.0"]]
  :repl-options {:init-ns neovim-client.nvim}
  :target-path "target/%s")
