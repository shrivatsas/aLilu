(ns alilu.store
    (:require [clojure.string :as str])
    (:import [java.nio ByteBuffer]
        [java.io RandomAccessFile File FileOutputStream]))

(def ^:private storage (atom {}))

(defn key-value-to-buffer [k v]
    (ByteBuffer/wrap (.getBytes (str k  #"«" v "\0"))))
  
(def ag-out-file (agent (.getChannel
    (FileOutputStream. (File. "store.txt") true))))

(defn update-key [ch key value]
    (let [offset (.position ch)]
      (.write ch (key-value-to-buffer key value))
      (swap! storage assoc key offset)
      ch))

(defn parse-key-value [buffer-bytes]
    (str/split (String. buffer-bytes) #"«"))

(defn char-seq [rdr]
    (let [chr (.read rdr)]
        (if (> chr 0)
            (cons chr (lazy-seq (char-seq rdr))))))

(defn read-from-file [offset]
    (let [raf (RandomAccessFile. "store.txt" "r")
          _ (.seek raf offset)
          buf (char-seq raf)
          data (parse-key-value (byte-array buf))]
    (.close raf)
    data))

(defn get-key [key]
    (if-let [offset (get @storage key)]
        (-> offset
            read-from-file
            last)
        "Not Found"))
    ; (get @storage key "Not Found"))

(defn put! [key value]
    (await (send ag-out-file update-key key value)))
    ; (swap! storage assoc key value))