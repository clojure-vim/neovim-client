(ns user
  (:require
    [clojure.test]
    [clojure.tools.namespace.repl :refer [refresh]]
    [neovim-client.nvim :as nvim]))

(defn tcp-connection
  "Get an nvim connection."
  []
  (nvim/new 1 "localhost" 7777))

(defn run-tests
  "Run all tests with clojure.test"
  []
  (refresh)
  (clojure.test/run-all-tests))
