(ns alilu.store)

(def ^:private storage (atom {}))

(defn get-key [key]
    (get @storage key "Not Found"))

(defn put! [key value]
    (swap! storage assoc key value))