(ns clop.parser-test
  (:require [clojure.test :refer :all])
  (:use [clop.parser]))

(deftest parser-atomize-test
  (testing "キーワードにできる"
    (is (= (to-atom "define") :define))
    (is (= (to-atom "lambda") :lambda)))
  (testing "数値にできる"
    (is (= (to-atom "1") 1))))

(deftest parser-tokenize-test
  (testing "文字列を意味のある区切りで分解できる"
    (is (= (tokenize "(define n 1)") ["(" "define" "n" "1" ")"]))))

(deftest parser-parsing-test
  (testing "シンボルと数値の配列にできる"
    (is (= (parse "(define n 1)") [:define :n 1]))))

(deftest debug
  (testing "debug"
    (let [tokens (tokenize "(define n 1)")]
      (println (parse tokens)))))
