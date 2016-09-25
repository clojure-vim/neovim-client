(ns neovim-client.socket-repl-plugin
  "A plugin which connects to a running socket repl and sends output back to
  Neovim."
  (:require
    [clojure.java.io :as io]
    [clojure.core.async :as async :refer [go go-loop >! <!]]
    [neovim-client.nvim :as nvim])
  (:import
    (java.net Socket)
    (java.io PrintStream)))

(defn connection
  [host port]
  (let [socket (java.net.Socket. "localhost" 5555)]
    {:host host
     :port port
     :out (-> socket
              io/output-stream
              PrintStream.)
     :in (io/reader socket)}))

(defn eval*
  [{:keys [:out]} code-string]
  (.println out code-string)
  (.flush out))

(def current-connection (atom nil))

(defn connect!
  [host port handler]
  (let [conn (connection host port)
        chan (async/chan 10)]
    (reset! current-connection (assoc conn
                                      :handler handler
                                      :chan chan))

    ;; input producer
    (go-loop []
             (when-let [line (.readLine (:in conn))]
               (>! chan line)
               (recur)))

    ;; input consumer
    (go-loop []
             (when-let [x (<! chan)]
               (handler x)
               (recur))))
  "success")

(defn eval*!
  [code-string]
  (eval* @current-connection code-string))

(defn -main
  [& args]
  ;; TODO: remove params for STDIO
  (nvim/connect! "localhost" 7777)

  (nvim/register-method!
    "connect"
    (fn [msg]
      ;; TODO: Get host/port from message
      (connect! "localhost" "5555"
                (fn [x]
                  (nvim/run-command-async!
                    ;; TODO: Actually, append to a buffer?
                    (format ":echo '%s'" (pr-str x))
                    (fn [_] nil))))))

   (nvim/register-method!
     "eval-code"
     (fn [msg]
       ;; TODO: Get the cursor location, find current form
       ))

   (nvim/register-method!
     "eval-buffer"
     (fn [msg]
       (nvim/get-current-buffer-text-async
         (fn [[x & _]]
           ;; TODO: send code being executed
           ;; back to vim, to show what actually
           ;; happened in the repl buffer
           (eval*! x)))))

  ;; TODO: When do we disconnect?
  (comment
    (dotimes [n 60]
      (if (= 0 (mod n 10))
        (nvim/run-command! (str ":echo 'plugin alive for " n " seconds.'")))
      (Thread/sleep 1000))

    ;; Let nvim know we're shutting down.
    (nvim/run-command! ":let g:is_running=0")
    (nvim/run-command! ":echo 'plugin stopping.'")))

