(ns clop.core
  (:use [clop.parser])
  (:use [clop.interpreter])
  (:use [clop.environment]))

; エントリポイントとなる名前空間。REPLなど式の読み込み、評価のインターフェースとなる

(defn evaluate [text]
    (-> text
        (tokenize)
        (parse)
        (interpret global-environment)))
