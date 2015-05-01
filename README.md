# neovim-client

A Neovim client library for creating plugins written in Clojure, using
msgpack-rpc.

Note that this is pretty much reference implementation quality at best -- nothing Clojars worthy as yet.

## TODO

* Response error handling.
* More higher-level nvim api stuff to show off.

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

### Is This Wrong?

This works, but is it the best road to creating Neovim plugins with Clojure?

neovim-client includes some functionality similar to the canonical neovim
plugin client library, [python-client](https://github.com/neovim/python-client).
However, so far, the documentation surrounding Neovim plugins is [still
congealing](https://github.com/neovim/neovim/issues/1929).

Likely, neovim-client is on the right track, while the included sample plugin
is seriosly flawed.

### msgpack-rpc

TODO links

Have we implemented msgpack-rpc? Are we close? If so, possibly extract into a
separate library. Would need to add in the join and getResult possibly.

### Leiningen Template

A Leiningen template for a Neovim plugin, using neovim-client, and including
the necessary VimScript hooks.

### Stop Using lein run

Using a VimScript hook, Neovim calls `lein run` to launch the plugin. This isn't
really Leiningen's job, but it's convenient for a few reasons:

* Single script to distribute, rather than clojure.jar, plugin jar, etc.
* Makes it possible to distribute .clj plugin source.

### First-Class Clojure Support?

Additionally, while remote plugins get the job done, what would it take to
provide first-class support for Clojure from within Neovim? In other words:

    :clj (conj [0 1 2] (+ 1 1 1)

## License

Distributed under the same license as Neovim.
