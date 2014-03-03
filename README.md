# instaweb

A Clojure tool for interactive hacking of web pages.

[![Build Status](https://travis-ci.org/friemen/instaweb.png?branch=master)](https://travis-ci.org/friemen/instaweb)

## Usage

Add a dependency to your project.clj

[instaweb/viewer "1.0.0"]


See the [sample](sample/src/sample/page.clj) on how to start the viewer and exchange content/style.

You can change the port with the expression `(v/reset-port! NNNN)`, the server will automatically
be restarted.

## License

Copyright © 2014 F.Riemenschneider

Distributed under the Eclipse Public License, the same as Clojure.
