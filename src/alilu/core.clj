(ns alilu.core
  (:require [aleph.tcp :as tcp]
            [manifold.stream :as s]
            [clojure.tools.logging :as log])
  (:gen-class))

(defn echo-handler [s info]
  (s/connect s s))
  
(defn start-server [port]
  (tcp/start-server echo-handler
    {:port port} )
  (log/info "Listening.."))

(defn start-up []
  (start-server 10001))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (start-up))
