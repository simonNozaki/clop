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

(def current-tokens (ref nil))
(def rest-subjects (ref nil))
(def subject (ref nil))

(defn parse [tokens]
  ; whileで参照するもとのトークン文字配列を状態管理する(ソフトウェアトランザクションメモリ)
  (dosync (ref-set current-tokens tokens))
  "パーサ、要素の配列からASTを生成"
  (when (or (empty? tokens) (nil? tokens))
    (throw (RuntimeException. "unexpected EOF while reading")))
  ; スペースを入れて分割、空文字を除いたリストを生成
  (let [identifier (first @current-tokens)]
    (cond
      (= identifier "(")
        (let [symbols (atom [])]
          (dosync (ref-set rest-subjects (rest tokens)))
          (dosync (ref-set subject (rest @rest-subjects)))
          (while (not (= subject ")"))
            (swap! symbols conj (parse rest)
             ; subjectのカーソルを一つ動かす
             (dosync (ref-set rest-subjects (rest tokens)))
             (dosync (ref-set subject (rest @rest))))
          @symbols)
      (= identifier ")")
        (throw (RuntimeException. "unexpected ')'"))
      :else
         (to-atom identifier)))))
