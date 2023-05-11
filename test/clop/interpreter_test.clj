(ns clop.interpreter-test
  (:require [clojure.test :refer :all])
  (:use [clop.interpreter]
        [clop.environment]))

(deftest interpreter-special-form-test
  (testing "数値リテラルを評価できる"
    (is (= (interpret 3 {}) 3)))
  (testing "変数を定義できる"
    (let [env (atom {})]
      (is (= (interpret [:define :n 1] env) :n))
      (is (= (get @env :n) 1))))
  (testing "四則演算ができる"
    (is (= (interpret [:+ 1 2] global-environment) 3))
    (is (= (interpret [:- 10 2] global-environment) 8))
    (is (= (interpret [:* 3 3] global-environment) 9)))
  (testing "比較演算できる"
    (is (= (interpret [:> 4 2] global-environment) true))
    (is (= (interpret [:> 1 3] global-environment) false))
    (is (= (interpret [:>= 3 3] global-environment) true)))
  (testing "条件分岐ができる"
    ; bool型が用意されていないのでtrue: 1, false: 0とする
    (is (= (interpret [:if [:> 2 1] 1 0] global-environment) 1))
    (is (= (interpret [:if [:= [:* 3 5] 15] 1 0] global-environment) 1)))
  (testing "クォートを評価できる"
    (is (= (interpret [:quote 1] {}) 1)))
  (testing "定義したラムダを呼び出せる(引数一つ)"
    (let [l [:lambda [:n] [:* :n :n]]
          square [:define :square l]
          env (atom {:* (fn [v] (reduce * v))})]
      (is (= (interpret square env) :square))
      (is (not (nil? (get @env :square))))
      (is (= ((get @env :square) 4) 16))))
  (testing "変数に代入できる"
    (let [env (atom {})]
      (is (= (interpret [:define :n 1] env) :n))
      (is (= (interpret [:set! :n 10] env) :n))
      (is (= (get @env :n) 10))))
  (testing "逐次処理で変数を計算できる"
    ; テストに使う変数を事前に定義しておく
    (interpret [:define :n 0] global-environment)
    (is (= (interpret [:begin [:set! :n 5] [:+ :n 1]] global-environment) 6))
    (is (= (get @global-environment :n) 5))))

(deftest interpreter-list-test
  (testing "リストを定義できる"
    (is (= (interpret [:list 1 3 5] global-environment) [1 3 5])))
  (testing "要素にアクセスできる"
    (is (= (interpret [:first 1 3 5] global-environment) 1))
    (is (= (interpret [:last 1 3 5] global-environment) 5))))
