# neovim-client

A Neovim client library for creating plugins written in Clojure.

## Supported API Versions

;; TODO table, showing version of neovim-client version, neovim version (used
;; to generate metadata, and api levels supported

## Dependencies

### Neovim

;; TODO finish this section
[Neovim](https://github.com/neovim/neovim)

### Java

You've probably already got this if you're using Clojure. Version 1.6 or later is required.

## Usage

### Repl

Launch Neovim, explicitly setting the `NVIM_LISTEN_ADDRESS`

```
NVIM_LISTEN_ADDRESS=127.0.0.1:7777 nvim
```

From repository:

```
$> lein repl
neovim-client.nvim=> (def c (new "localhost" 7777))
neovim-client.nvim=> (require '[neovim-client.1.api :as api])
neovim-client.nvim=> (api/command ":echo 'Hello Neovim!'")
...
```

[![Repling Neovim](http://img.youtube.com/vi/g-9DdVwbSTo/0.jpg)](https://www.youtube.com/watch?v=g-9DdVwbSTo)

### Examples

#### Included Sample Plugin

##### Installation

One way to install the sample plugin is by running `./deploy-local.sh` script,
included, which copies the sample plugin to `~/.vim/bundle`, and then ensure
Neovim's runtimepath is set correctly by adding `set
runtimepath^=~/.vim/bundle/sample-plugin` to `.vimrc`.

##### Running

This plugin stays running, and maintains state. Additionally, it shows how
plugins are actually servers, which Neovim can make requests to via
rpcrequest().

```
:echo SamplePluginCount()
```

#### Socket Repl Plugin

A simple alternative to vim-fireplace. Send code in vim buffers to a built-in
Clojure socket repl.

https://github.com/jebberjeb/clojure-socketrepl.nvim

## Future

### Tighter Integration with Neovim

Neovim's strategy for [remote plugins](http://neovim.io/doc/user/remote_plugin.html#remote-plugin) says that it's ok to create a remote plugins as "arbitrary programs that communicate directly with the high-level Nvim API and are called via [msgpack-rpc]". That's exactly what the included sample-plugin does (utilizing neovim-client).

However, it goes on to outline a better approach, utilizing a "plugin host". While neovim-client could potentially fill that role, considerably more work is needed.

### Performance

Individual plugins can be distributed as jars using AOT compilation. Additionally, porting this client library to Clojurescript would facilitate a fast startup time, via Node.

## License

Distributed under the same license as Neovim.
