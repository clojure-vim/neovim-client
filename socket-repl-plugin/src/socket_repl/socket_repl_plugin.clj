(ns socket-repl.socket-repl-plugin
  "A plugin which connects to a running socket repl and sends output back to
  Neovim."
  (:require
    [clojure.java.io :as io]
    [clojure.core.async :as async :refer [go go-loop >! <!]]
    [neovim-client.nvim :as nvim])
  (:import
    (java.net Socket)
    (java.io PrintStream)))

(def current-connection (atom nil))

(defn connection
  "Create a connection to a socket repl."
  [host port]
  (let [socket (java.net.Socket. "localhost" 5555)]
    {:host host
     :port port
     :out (-> socket
              io/output-stream
              PrintStream.)
     :in (io/reader socket)}))

(defn write-code
  "Writes a string of code to the socket repl connection."
  [{:keys [:out]} code-string]
  (.println out code-string)
  (.flush out))

(defn connect!
  "Connect to a socket repl. Adds the connection to the `current-connection`
  atom. Creates `go-loop`s to delegate input from the socket to `handler` one
  line at a time.

  `handler` is a function which accepts one string argument."
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

(defn write-code!
  "Like `write-code`, but uses the current socket repl connection."
  [code-string]
  (write-code @current-connection code-string))

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
           (write-code! x)))))

  ;; TODO: Rather than an arbitrary timeout, the plugin should shut down
  ;; when it has received no input for some time.
  (comment
    (dotimes [n 60]
      (if (= 0 (mod n 10))
        (nvim/run-command! (str ":echo 'plugin alive for " n " seconds.'")))
      (Thread/sleep 1000))

    ;; Let nvim know we're shutting down.
    (nvim/run-command! ":let g:is_running=0")
    (nvim/run-command! ":echo 'plugin stopping.'")))

