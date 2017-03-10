(ns neovim-client.1.api.buffer-ext-test
  (:require [clojure.core.async :as async]
            [clojure.test :as test :refer [deftest use-fixtures is]]
            [neovim-client.1.api :as api]
            [neovim-client.1.api.buffer :as api.buffer]
            [neovim-client.1.api.buffer-ext :as api.buffer-ext]
            [neovim-client.nvim :as client.nvim]
            [neovim-client.test-helper :refer [with-neovim]]))

(deftest get-lines
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)]
      (api/command conn "norm ifoo\nbar\nbaz\nqux")
      (is (= ["bar" "baz"] (api.buffer-ext/get-lines
                             conn
                             (api/get-current-buf conn)
                             1 3))))))

(deftest get-lines-async
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)
          p (promise)]
      (api/command conn "norm ifoo\nbar\nbaz\nqux")
      (api.buffer-ext/get-lines-async
        conn
        (api/get-current-buf conn)
        1 3
        #(deliver p %))
      (is (= ["bar" "baz"] @p)))))
