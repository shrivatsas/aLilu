(ns alilu.core-test
  (:require [clojure.test :refer :all]
            [alilu.core :refer :all :as core]
            [manifold.stream :as s]
            [alilu.store :as store]))

(deftest test-get-value
  (testing "testing a command is well parsed."
    (let [s (s/stream)]
      (store/put! "k1" "hello")
      (store/put! "k2" "world")
      (core/get-value "k2" s)
      (is (= "world\r\n" @(s/take! s))))))
