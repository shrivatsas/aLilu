(ns alilu.helper
    (:import (java.nio.charset StandardCharsets)))

(defn to-string [bytes]
    (String. bytes StandardCharsets/UTF_8))