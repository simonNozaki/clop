(ns clop.sequence
  (:import
    (clojure.lang
      PersistentVector))
  (:import
    (java.util
      List)))


(defn v-to-l
  [vector]
  "PersistentVectorをListに変換する。引数がVectorではない場合空のListを返す"
  (if (instance? PersistentVector vector)
    (concat (quote ()) vector)
    '()))


(defn l-to-raw
  [col]
  "mapなどによりListインスタンスになることがある。Listに意図せずラップされる場合アンラップして返す"
  (if (instance? List (first col)) (first col) col))
