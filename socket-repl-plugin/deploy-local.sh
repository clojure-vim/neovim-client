lein uberjar
cp target/uberjar/socket-repl-plugin-0.1.0-SNAPSHOT-standalone.jar .
rm -rf ~/.vim/bundle/neovim-client
cd ../..
cp -rf neovim-client ~/.vim/bundle
