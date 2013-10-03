(ns net.unit8.tower.utils-test
  (:require-macros [cemerick.cljs.test :refer (is deftest with-test runtests testing)])
  (:require 
    [cemerick.cljs.test :as t]
    [net.unit8.tower.utils :as utils]))

(deftest test-fq-name
  (testing "Convert symbol to string"
    (is (= "hoge" (utils/fq-name :hoge))))
  (testing "Convert string to string"
    (is (= "hoge" (utils/fq-name "hoge")))))

(deftest test-explode-keyword
  (testing ""
    (is (= ["hoge" "fuga"] (utils/explode-keyword :hoge.fuga)))))



