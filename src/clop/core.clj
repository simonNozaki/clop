(ns clop.core
  (:require
    [clop.environment :refer :all]
    [clop.interpreter :refer :all]
    [clop.parser :refer :all]))

;; エントリポイントとなる名前空間。REPLなど式の読み込み、評価のインターフェースとなる

(defn evaluate
  "字句を解析して評価の結果を返す"
  [text]
  (-> text
      (tokenize)
      (parse)
      (interpret global-environment)))
