(ns neovim-client.nvim-test
  (:require [clojure.test :as test :refer [deftest use-fixtures is]]
            [neovim-client.1.api :as api]
            [neovim-client.1.api.buffer :as api.buffer]
            [neovim-client.nvim :as client.nvim]
            [neovim-client.test-helper :refer [with-neovim stop-neovim]]))

;; TODO - this one will be hard to test. From the client-library's perspective,
;; it will always have access to standard out. If it runs at all, standard
;; out will exist. So is this worth testing?
(deftest connect-fail-not-running
  (is true))

(deftest connect
  (with-neovim
    (let [{:keys [in out]} *neovim*]
      (is (thrown-with-msg?
            Throwable
            #"version not supported"
            (client.nvim/new* -1 in out false)))))

  (with-neovim
    (let [{:keys [in out]} *neovim*]
      (is (thrown-with-msg?
            Throwable
            #"version not supported"
            (client.nvim/new* 2 in out false)))))

  (with-neovim
    (let [{:keys [in out]} *neovim*]
      (is (client.nvim/new* 0 in out false))))

  (with-neovim
    (let [{:keys [in out]} *neovim*]
      (is (client.nvim/new* 1 in out false)))))

(deftest change-buffer-text
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)]
      (let [b1 (api/get-current-buf conn)
            _ (api.buffer/set-lines conn b1 0 1 false ["foo"])
            _ (api/command conn "new")
            b2 (api/get-current-buf conn)
            _ (api.buffer/set-lines conn b2 0 1 false ["bar"])]
        (is (= ["foo"] (api.buffer/get-lines conn b1 0 1 false)))
        (is (= ["bar"] (api.buffer/get-lines conn b2 0 1 false)))))))
