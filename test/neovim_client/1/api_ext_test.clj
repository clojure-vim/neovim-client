(ns neovim-client.1.api-ext-test
  (:require [clojure.core.async :as async]
            [clojure.test :as test :refer [deftest use-fixtures is]]
            [neovim-client.1.api :as api]
            [neovim-client.1.api.buffer :as api.buffer]
            [neovim-client.1.api-ext :as api-ext]
            [neovim-client.nvim :as client.nvim]
            [neovim-client.test-helper :refer [with-neovim]]))

(deftest get-cursor-location
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)]
      (api/command conn "norm ifoo\nbar\nbaz")
      (let [[row col] (api-ext/get-cursor-location conn)]
        (is (= row 3))
        (is (= col 2))))))

(deftest get-current-buffer-text
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)]
      (api/command conn "norm ifoo\nbar\nbaz")
      (api/command conn "new")
      (api/command conn "norm iqux")
      (let [text (api-ext/get-current-buffer-text conn)]
        (is (= text "qux"))))))

(deftest get-current-word-async
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)]
      (api/command conn "norm ifoo\nbar\nbaz")
      (api/command conn "norm bb")
      (let [p (promise)] (api-ext/get-current-word-async conn #(deliver p %))
        (is (= @p "bar"))))))

(deftest buffer-visible?-async
  (with-neovim
    (let [{:keys [in out]} *neovim*
          conn (client.nvim/new* 1 in out false)
          filename (str "/tmp/" (java.util.UUID/randomUUID))]

      (spit filename "test")
      (api/command conn (str "e " filename))
      (is (= filename
             (async/<!! (api-ext/buffer-visible?-async conn filename)))))))
