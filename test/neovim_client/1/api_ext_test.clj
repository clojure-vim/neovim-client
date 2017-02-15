(ns neovim-client.1.api-ext-test
  (:require [clojure.test :as test :refer [deftest use-fixtures is]]
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

;; TODO get-current-buffer-text
;; TODO get-current-word-async
;; TODO buffer-visible?-async
