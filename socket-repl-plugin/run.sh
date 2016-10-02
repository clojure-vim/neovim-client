# Development run script.

if [[ ! -e project.clj ]]; then
    echo "This script must be run from the top-level"
    exit 1
fi

# Generate .classpath from Leiningen if project.clj has changed.
if [[ project.clj -nt .classpath ]]; then
    lein classpath > .classpath
fi

# Use server-daemon false, so socket repl server thread will block exit.
exec java \
     -cp "$(cat .classpath)" \
     -Djava.awt.headless=true \
     -Dclojure.server.repl="{:port 5555 :accept clojure.core.server/repl :server-daemon false}" \
     -XX:-OmitStackTraceInFastThrow \
     clojure.main \
     ./src/socket_repl/socket_repl_plugin.clj --debug true
