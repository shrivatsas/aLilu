For sockets and streaming i utilize the aleph and manifold libraries.

Aleph exposes data from the network as a Manifold stream, which can easily be transformed into a java.io.InputStream, core.async channel, Clojure sequence, or many other byte representations.

Manifold library provides basic building blocks for asynchronous programming, and can be used as a translation layer between libraries which use similar but incompatible abstractions.

Manifold provides two core abstractions: deferreds, which represent a single asynchronous value, and streams, which represent an ordered sequence of asynchronous values.