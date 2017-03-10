lein uberjar
cp target/uberjar/sample-plugin-0.1.0-standalone.jar .
rm -rf ~/.vim/bundle/sample-plugin
cd ..
cp -rf sample-plugin ~/.vim/bundle
