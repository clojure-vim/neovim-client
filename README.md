# neovim-client

A Neovim client library for creating plugins written in Clojure.

## Dependencies

### Neovim

I've been building from [master](https://github.com/neovim/neovim). Currently,
I'm using `NVIM v0.1.2-220-g3b94756`.

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
neovim-client.nvim=> (connect! "localhost" 7777)
...
neovim-client.nvim=> (run-command! ":echo 'Hello Neovim!'")
...
```

[![Repling Neovim](http://img.youtube.com/vi/g-9DdVwbSTo/0.jpg)](https://www.youtube.com/watch?v=g-9DdVwbSTo)

### Sample Plugins

Several sample plugins -- consumers of neovim-client -- are included.

#### Installation

While these plugins can be installed locally, the easiest thing to do is to
run them from the repl, as described above. Simply use `lein repl` from the
plugin project's root directory.

#### Simple Plugin

```
:call RunSamplePluginSimple()
```

#### Count Plugin

This plugin stays running, and maintains state. Additionally, it shows how
plugins are actually servers, which Neovim can make requests to via
rpcrequest().

```
:echo SamplePluginCount()
```

#### Socket Repl Plugin

TODO

## Future

### Tighter Integration with Neovim

Neovim's strategy for [remote plugins](http://neovim.io/doc/user/remote_plugin.html#remote-plugin) says that it's ok to create a remote plugins as "arbitrary programs that communicate directly with the high-level Nvim API and are called via [msgpack-rpc]". That's exactly what the included sample-plugin does (utilizing neovim-client).

However, it goes on to outline a better approach, utilizing a "plugin host". While neovim-client could potentially fill that role, considerably more work is needed.

### Performance

Individual plugins can be distributed as jars using AOT compilation, as is the
case for the socket repl plugin. Additionally, porting this client library to Clojurescript would facilitate a fast startup time, via Node.

## License

Distributed under the same license as Neovim.
