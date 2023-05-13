(ns clop.e2e-test
  (:require
    [clojure.test :refer :all]
    [clop.core :refer :all]
    [clop.environment :refer :all]
    [clop.interpreter :refer :all]
    [clop.parser :refer :all]))


(deftest e2e-single-values
  (testing "数値を評価できる"
    (is (= (evaluate "1") 1)))
  (testing "整数計算できる"
    (is (= (evaluate "(* 3 3)")) 9)
    (is (= (evaluate "(/ 6 2)"))) 3)
  (testing "論理演算を評価できる"
    (is (= (evaluate "(if (= 4 (* 2 2)) 1 0)") 1))
    (is (= (evaluate "(+ 1 2 3 4 5)") 15))
    (is (= (evaluate "(/ (+ 3 4 8) 3)") 5)))
  (testing "配列リテラルを評価できる"
    (is (= (evaluate "(vector 1 3 5)") [1 3 5])))
  (testing "クォートを評価できる"
    (is (= (evaluate "(quote (1 3 5))") '(1 3 5))))
  (testing "配列を操作できる"
    (is (= (evaluate "(first (vector 1 3 5))") 1))
    (is (= (evaluate "(last (vector 1 3 5))") 5))))


(deftest e2e-multiple-definitions
  (testing "定義した配列を操作できる"
    (evaluate "(define even (vector 2 4 6))")
    (is (= (evaluate "(last even)") 6))
    (is (= (evaluate "(+ even)") 12)))
  (testing "関数を定義して呼び出せる: 単純"
    (evaluate "(define average (lambda (m n) (/ (+ m n) 2)))")
    (is (= (evaluate "(average 3 7)") 5)))
  ;(testing "関数を定義して呼び出せる: 再帰"
  ;  (evaluate "(define factorial (lambda (n) (if (< n 2) n (* n (factorial (- n 1))))))")
  ;  (is (= (evaluate "(factorial 5)") 120)))
  )
