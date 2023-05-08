(ns clop.parser
  (:require [clojure.string :as s])
  (:import [java.lang Integer Float]))

(defn to-atom [element]
  "文字列の要素から数値、キーワードに順番に変換を試す"
  (try
    (Integer/parseInt element)
    (catch NumberFormatException e
      (try
        (Float/parseFloat element)
        (catch NumberFormatException e (keyword element))))))

(defn tokenize [text]
  "トークナイザ、文字列を要素に分解"
  (filter
    #(not (s/blank? %))
    (s/split
      (s/replace text #"[\(\)]" " $0 ")
      #" ")))

; 評価していない残りのトークン文字列
(def current-tokens (ref nil))
; 次に評価対称となるトークン文字列
(def subject (ref nil))

(defn parse [tokens]
  "パーサ、要素の配列からASTを生成"
  (when (or (empty? tokens) (nil? tokens))
    (throw (RuntimeException. "unexpected EOF while reading")))
  ; 再帰で評価するので、はじめに評価対象と残評価を更新
  (dosync (ref-set current-tokens (rest tokens)))
  (dosync (ref-set subject (first @current-tokens)))
  (let [identifier (first tokens)]
    (cond
      (= identifier "(")
        (let [symbols (atom [])]
          (while (not (= @subject ")"))
            (swap! symbols conj (parse @current-tokens)))
          ; 直前の閉じカッコ(")")を省いて制御を戻す
          (dosync (ref-set current-tokens (rest @current-tokens)))
          (dosync (ref-set subject (first @current-tokens)))
          @symbols)
      (= identifier ")")
        (throw (RuntimeException. "unexpected ')'"))
      :else
         (to-atom identifier))))
