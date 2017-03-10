(ns neovim-client.nvim
  (:require [clojure.core.async :as async]
            [clojure.string :as str]
            [clojure.tools.logging :as log]
            [neovim-client.message :as message :refer [->request-msg]]
            [neovim-client.parser :as parser]
            [neovim-client.rpc :as rpc])
  (:import (java.net Socket)
           (com.etsy.net UnixDomainSocketClient JUDS)))

(defn version-supported?
  [api-info desired-version]
  (let [min-version (parser/min-level api-info)
        max-version (parser/max-level api-info true)]
    (<= min-version desired-version max-version)))

(defn exec
  [component op & args]
  (rpc/send-message!
    component
    (message/->request-msg op (vec args))))

(defn exec-async
  [component op & args]
  (rpc/send-message-async!
    component
    (message/->request-msg op (vec (butlast args)))
    (last args)))

(def register-method! rpc/register-method!)
(def stop rpc/stop)

(defn new*
  [version input output update-nvim-channel?]
  (let [component (rpc/new input output)
        ;; This could be a problem, making a synchronous call here, but we
        ;; want a way from preventing attempts against a bad api version.
        [channel api-info] (exec component "vim_get_api_info")]
    (if-not (version-supported? api-info version)
      (throw (ex-info "version not supported" {:version version
                                               :api-info api-info})))
    ;; Each time you connect to the same nvim instance using a socket, nvim
    ;; allocates a new channel.
    (when update-nvim-channel?
      (exec component
            "vim_command"
            (format "let g:nvim_tcp_plugin_channel = %s"
                    channel)))
    component))

(defn new
  "Connect to msgpack-rpc channel via standard io, TCP socket, Unix domain
  sockets."
  ([version] (new* version System/in System/out true))
  ([version uds-filepath]
   (let [socket (UnixDomainSocketClient. uds-filepath JUDS/SOCK_STREAM)]
     (new* version (.getInputStream socket) (.getOutputStream socket) true)))
  ([version host port]
   (log/info "plugin host connecting to nvim at " host ":" port)
   (let [socket (java.net.Socket. host port)]
     (.setTcpNoDelay socket true)
     (new* version (.getInputStream socket) (.getOutputStream socket) true))))
