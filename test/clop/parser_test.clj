(ns clop.parser-test
  (:require
    [clojure.test :refer :all]
    [clop.parser :refer :all]))


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
  (testing "一番シンプル"
    (is (= (parse (tokenize "(5)"))), [5]))
  (testing "シンボルと数値の配列にできる"
    (is (= (parse (tokenize "(define n 1)")) [:define :n 1])))
  (testing "簡単な四則演算"
    (is (= (parse (tokenize "(* 2 4)")) [:* 2 4])))
  (testing "論理演算"
    (is (= (parse (tokenize "(= 4 (* 2 2))")) [:= 4 [:* 2 2]])))
  (testing "整数演算"
    (is (= (parse (tokenize "(+ 1 (* 2 (/ 9 3)))")) [:+ 1 [:* 2 [:/ 9 3]]])))
  (testing "整数演算2"
    (is (= (parse (tokenize "(+ 1 (* 2 2) (/ 8 2))")) [:+ 1 [:* 2 2] [:/ 8 2]])))
  (testing "関数定義: シンプル"
    (is (= (parse (tokenize "(define true (lambda () (1)))")) [:define :true [:lambda [] [1]]])))
  (testing "関数定義: 引数あり"
    (is (= (parse (tokenize "(define square (lambda (n) (* n n)))")) [:define :square [:lambda [:n] [:* :n :n]]]))))
