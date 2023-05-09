(ns clop.e2e-test
  (:require [clojure.test :refer :all])
  (:use [clop.core]
        [clop.interpreter]
        [clop.environment]
        [clop.parser]))

(deftest e2e-single-values
  (testing "数値を評価できる"
    (is (= (evaluate "1") 1)))
  (testing "整数計算できる"
    (is (= (evaluate "(* 3 3)")) 9)
    (is (= (evaluate "(/ 6 2)"))) 3)
  (testing "論理演算を評価できる"
    (is (= (evaluate "(if (= 4 (* 2 2)) 1 0)") 1))))

(deftest e2e-tests
  (testing "定義した関数を呼び出せる"
    (evaluate "(define square (lambda (n) (* n n)))")
    (is (= (evaluate "(square 3)") 9))))
