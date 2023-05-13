(ns clop.environment
  (:import (java.util List)))

(defn unwrap-list [col]
  "mapなどによりListインスタンスになることがある。ListがPersistentVectorを内包している場合アンラップして返す"
  (if (instance? List (first col)) (first col) col))

(def global-environment
  (atom {
         :+ #(reduce + (unwrap-list %)),
         :- #(reduce - (unwrap-list %)),
         :* #(reduce * (unwrap-list %)),
         :/ #(reduce / (unwrap-list %)),
         :> (fn [v] (> (first v) (second v))),
         :>= (fn [v] (>= (first v) (second v))),
         :< (fn [v] (< (first v) (second v))),
         :<= (fn [v] (<= (first v) (second v))),
         := (fn [v] (= (first v) (second v))),
         ; interpreterで、mapの結果list（ISeqのサブクラスインスタンス）が流れてくる可能性があるので
         ; PersistentVectorが含まれているかを確認してからintoで変換
         ; second, last, listについても同様
         :first #(first (unwrap-list %)),
         :second #(second (unwrap-list %)),
         :last #(last (unwrap-list %)),
         :rest #(rest (unwrap-list %)),
         ; リストで入ってくるので、ベクタに直す
         :vector #(into [] (unwrap-list %))
         }
        ))
