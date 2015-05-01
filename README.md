# neovim-client

A Neovim client library for creating plugins written in Clojure, using
msgpack-rpc.

Note that this is pretty much reference implementation quality at best -- nothing Clojars worthy as yet.

## TODO

* Response error handling.
* More higher-level nvim api.

## Dependencies

### Neovim

I've been building from [master](https://github.com/neovim/neovim).

### Leiningen (master)

First, build Leiningen from master. Due to a
[bug in handling stdio](https://github.com/technomancy/leiningen/issues/1857),
the current Leiningen release won't cut it. So clone the repo, checkout master,
and build it. See Leiningen's
[CONTRIBUTING.md](https://github.com/technomancy/leiningen/blob/master/CONTRIBUTING.md#bootstrapping)
for instructions.

### Java

You've probably already got this if you're using Clojure. If not, whatever JVM your package manager installs should work.

## Usage

### Repl

Launch Neovim, explicitly setting the NVIM_LISTEN_ADDRESS

    NVIM_LISTEN_ADDRESS=127.0.0.1:7777 nvim

From the cloned repository's directory:

    lein repl
    neovim-client.nvim=> (connect! "localhost" 7777)
    ...
    neovim-client.nvim=> (run-command! ":echo 'Hello Neovim!'")
    ...

[![Repling Neovim](http://img.youtube.com/vi/g-9DdVwbSTo/0.jpg)](https://www.youtube.com/watch?v=g-9DdVwbSTo)

### Neovim Plugin

For now (no Clojars yet), we need neovim-client installed locally, so from
the cloned repository's directory:

    lein install

Install sample-plugin however you normally do it. For example, using Vundle
you'd add the following line to your .vimrc:

    Plugin 'file:///path/to/neovim-client', {'rtp': 'sample-plugin/'}

Finally, to invoke the plugin launch nvim and run:

    :VundleInstall
    :call RunSamplePlugin()

## Future

### Tighter Integration with Neovim

Neovim's strategy for [remote plugins](http://neovim.io/doc/user/remote_plugin.html#remote-plugin) says that it's ok to create a remote plugins as "arbitrary programs that communicate directly with the high-level Nvim API and are called via [msgpack-rpc]". That's exactly what the included sample-plugin does (utilizing neovim-client). 

However, it goes on to outline a better approach, utilizing a "plugin host". While neovim-client could potentially fill that role, considerably more work is needed.

The bottom line is this works, and possibly using ClojureScript w/ node.js, it could be fast too!

### Leiningen Template

A Leiningen template for a Neovim plugin, using neovim-client, and including
the necessary VimScript hooks.

### Stop Using lein run

Using a VimScript hook, Neovim calls `lein run` to launch the plugin. This isn't
really Leiningen's job, but it's convenient for a few reasons:

* Single script to distribute, rather than clojure.jar, plugin jar, etc.
* Makes it easy for a plugin user to modify it - just edit .clj source.

## License

Distributed under the same license as Neovim.
