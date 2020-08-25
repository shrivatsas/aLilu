(ns alilu.protocol
    (:require [clojure.tools.logging :as log]
              [clojure.string :as str]
              [manifold.stream :as s]
              [manifold.deferred :as d]
              [alilu.helper :as helper]))

(defmulti parse (fn [cmd & other] cmd))

(defn run-cmd
    [command s]
    (let [args (str/split command #" ")]
      (apply parse (conj args s))))
  
(defn process-commands [stream batch-commands f]
    (let [cmds (str/split-lines batch-commands)]
      (dorun
        (for [cmd cmds]
            (f stream cmd)))))

(defn consume-cmds-from-socket [stream f f_close]
    "It processes any new connection.
     The code is based on this example
     https://github.com/aleph-io/aleph/blob/master/examples/src/aleph/examples/tcp.clj#L119"
    (d/loop [cmd []]
      (-> (s/take! stream ::none)
          (d/chain
            (fn [b]
              (when (not= b ::none)
                (let [text (helper/to-string b)
                      acc (conj cmd text)]
                (when (= \newline (last text))
                    (process-commands stream (str/join acc) f)
                    (d/recur []))))))
          (d/catch
              (fn [ex]
                (log/error  ex)
                (s/close! stream)
                (f_close stream ex))))))

(defn consume-cmds [stream end-callback]
    (consume-cmds-from-socket stream
                              (fn [s cmd]
                                (run-cmd cmd s))
                              end-callback))