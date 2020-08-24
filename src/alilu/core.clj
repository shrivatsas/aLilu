(ns alilu.core
  (:require [aleph.tcp :as tcp]
            [manifold.stream :as s]
            [clojure.tools.logging :as log]
            [alilu.protocol :as protocol :refer [parse]]
            [alilu.store :as store])
  (:gen-class))

(defn close [s]
  (s/close! s))
  
(defn get-value [k s]
  (log/debug "MASTER::GET:: received " k)
  (let [value (str (store/get-key k) "\r\n")]
    (s/put! s value)))

(defn write-operation [k v s]
  (log/debug "MASTER::SET:: received " k v)
    (do (store/put! k v)
        (s/put! s "OK\r\n")))

(defmethod parse "GET"
  [cmd & args]
  (apply get-value args))

(defmethod parse "SET"
  [cmd & args]
  (apply write-operation args))

(defmethod parse "CLOSE"
  [cmd & args]
  (apply close args))
  
(defn echo-handler [s info]
  (s/connect s s))

(defn close-server []
  (println "closing"))
  
(defn process-cmds [stream info]
  (protocol/consume-cmds stream close-server))

(defn start-server [port]
  (tcp/start-server process-cmds
    {:port port} )
  (log/info "Listening.."))

(defn start-up []
  (start-server 10001))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (start-up))
