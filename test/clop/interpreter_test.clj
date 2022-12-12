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
  (testing "クォートを評価できる"
    (is (= (interpret [:quote 1] {}) 1)))
  (testing "定義したラムダを呼び出せる(引数一つ)"
    (let [l [:lambda [:n] [:* :n :n]]
          square [:define :square l]
          env (atom {:* (fn [v] (reduce * v))})]
      (is (= (interpret square env) :square))
      (is (not (nil? (get @env :square))))
      (is (= ((get @env :square) 4) 16)))))

(deftest interpreter-list-test
  (testing "リストを定義できる"
    (is (= (interpret [:cons 1 3 5] global-environment) [1 3 5])))
  (testing "要素にアクセスできる"
    (is (= (interpret [:first 1 3 5] global-environment) 1))
    (is (= (interpret [:last 1 3 5] global-environment) 5))))
