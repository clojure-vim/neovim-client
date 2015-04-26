# neovim-client

A Neovim client library for creating neovim plugins written in Clojure, using
msgpack-rpc to communicate with Neovim.

## TODO

* proper logging, debug which we can turn off
* more higher-level nvim api stuff to show off

## Dependencies

### Neovim

TODO

### Leiningen (master)

First, build Leiningen from master. Due to a
[bug in handling stdio](https://github.com/technomancy/leiningen/issues/1857),
the current Leiningen release won't cut it. So clone the repo, checkout master,
and build it. See Leiningen's
[CONTRIBUTING.md](https://github.com/technomancy/leiningen/blob/master/CONTRIBUTING.md#bootstrapping)
for instructions.

### Repl

Launch Neovim, explicitly setting the NVIM_LISTEN_ADDRESS

    NVIM_LISTEN_ADDRESS=127.0.0.1:7777 nvim

After cloning this repostiory

    lein repl
    neovim-client.nvim=> (connect! "localhost" 7777)
    ...
    neovim-client.nvim=> (run-command! "vsplit")
    ...

TODO video

### Neovim Plugin

TODO use included sample, uses stdio, .vim hooks

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
separate library.

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
