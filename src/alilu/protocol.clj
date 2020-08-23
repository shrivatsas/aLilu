(ns alilu.protocol)

(defmulti parse (fn [cmd & other] cmd))