# neovim-client

A client library for creating Neovim plugins written in Clojure.

[![Repling Neovim](http://img.youtube.com/vi/pCuEDiKXV5Q/0.jpg)](https://www.youtube.com/watch?v=pCuEDiKXV5Q)

## Dependencies

* [Neovim](https://github.com/neovim/neovim)

* [Java Development Kit](http://www.oracle.com/technetwork/java/javase/overview/)
  (JDK) Standard Edition (SE) version 8

## Usage

### Repl

Launch Neovim, explicitly setting the `NVIM_LISTEN_ADDRESS`

```
NVIM_LISTEN_ADDRESS=127.0.0.1:7777 nvim

```

From the repository directory:

```
$> lein repl
user=> (def c (tcp-connection))
user=> (require '[neovim-client.1.api :as api])
user=> (api/command ":echo 'Hello Neovim!'")
...
```

Alternatively, if you've got tmux installed, you can use run the script
`./tmux-run-dev.sh`, which will start Neovim, a repl, and execute similar
setup code.

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
rpcnotify().

```
:echo SamplePluginCount()
```

#### Socket Repl Plugin

A simple alternative to vim-fireplace. Send code in vim buffers to a built-in
Clojure socket repl.

https://github.com/jebberjeb/clojure-socketrepl.nvim

## Neovim API Versions (Levels)

Neovim's RPC API is
[versioned separately](https://github.com/neovim/neovim/pull/5535) from
Neovim itslef, using a monotonically increasing integer. The API's version is
tracked using a value called `api_level`, which can be found by examining the
output of `:echo api_info()`.

Currently, levels 0 - 1 are supported.

In the future, this library can be updated to support a new level by:

* Updating the API metadata `$> nvim --api-info > resources/api-info.mp`

* Generating code from the metadata using `neovim-client.parser/generate`

## Changes

### Version 0.1.0

* Generate code for api_level 0 - 1 from metadata generated using Neovim
version 0.2.0

* Hand-written functions moved to `-ext` namespaces

* Added unit tests which use `nvim --embed` process

* Support Unix Domain Sockets

* Verify api_level available on connection

## Future

### Tighter Integration with Neovim

Neovim's strategy for [remote plugins](http://neovim.io/doc/user/remote_plugin.html#remote-plugin) says that it's ok to create a remote plugins as "arbitrary programs that communicate directly with the high-level Nvim API and are called via [msgpack-rpc]". That's exactly what the included sample-plugin does (utilizing neovim-client).

However, it goes on to outline a better approach, utilizing a "plugin host". While neovim-client could potentially fill that role, considerably more work is needed.

### Performance

Individual plugins can be distributed as jars using AOT compilation. Additionally, porting this client library to Clojurescript would facilitate a fast startup time, via Node.

## License

Distributed under the same license as Neovim.
