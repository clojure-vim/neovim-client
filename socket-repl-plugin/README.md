# Neovim Socket Repl Plugin

A [Neovim](https://github.com/neovim/neovim) plugin for Clojure which uses the built in [socket repl](http://clojure.org/reference/repl_and_main#_launching_a_socket_server) introduced in version 1.8 of Clojure.

This plugin uses the [Neovim Clojure plugin host](https://github.com/jebberjeb/neovim-client). While intended to be a demonstration of the plugin host library, it may also be suitable for daily Clojure development.

Simply, this plugin uses a socket connection to send code to the repl. Nothing (very little) more. All (most) of the plugin code is written using Clojure. There's no (some) VimScript to sift through. This plugin is probably a bit too transparent. You see absolutely everything that's sent to the repl. If you eval an entire buffer...

TODO: architecture diagram

## Usage

Start a Clojure program with a socket repl server. This can be done by
adding the following JVM options to any Clojure application.

```
-Dclojure.server.repl="{:port 5555 :accept clojure.core.server/repl}"
```

Then, from within Neovim, connect to the socket repl.

```
:Connect <host> <port>
```

Create a buffer for displaying interaction with the repl.

```
:vnew
:ReplLog
```

From there, to eval any buffer or form under cursor use:

```
:EvalBuffer
```

or

```
:EvalCode
```

TODO: demo video

## Installation

Install however you normally do it. For example, using Vundle you'd add the
following line to your `.vimrc`:

```
Plugin 'jebberjeb/neovim-client', {'rtp': 'socket-repl-plugin/'}
```

Then (after sourcing `.vimrc`), from neovim:

```
:VundleInstall
```

## Dependencies

This plugin requires a version of the Java version 1.6 or higher. You've probably already got this if you're using Clojure.

## Developing

Start Neovim using, open the debug plugin script.

```
NVIM_LISTEN_ADDRESS=127.0.0.1:7777 nvim plugin/socketrepl.vim.debug
```

From within Neovim, source the debug plugin script. This causes it to use
a socket connection, rather than stdio to communicate with the plugin.

```
:so %
```

Start the plugin. Then you'll need to connect to Neovim from the repl.

```
$> ./run-dev.sh
$> nc localhost 5555
user=> (go)
```

or, with Leiningen

```
$> lein repl
$> (go)
```

You can now use plugin commands from within Neovim `:Connect`, `:EvalBuffer`,
etc.

Note that you'll probably want to rely heavily on the asynchronous
neovim-client functions when you want (the plugin) to make a request
of neovim. This is because the Neovim function `rpcrequest` blocks until
it has received a response (from your plugin). Using async on the plugin
side is the easiest way to avoid deadlock.
