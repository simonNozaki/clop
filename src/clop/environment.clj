(ns clop.environment)


(def global-environment
  (atom {:+ #(reduce + %),
         :- #(reduce - %),
         :* #(reduce * %),
         :/ #(reduce / %),
         :> (fn [v] (> (first v) (second v))),
         :>= (fn [v] (>= (first v) (second v))),
         :< (fn [v] (< (first v) (second v))),
         :<= (fn [v] (<= (first v) (second v))),
         := (fn [v] (= (first v) (second v))),
         :first #(first %),
         :second #(second %),
         :last #(last %),
         :rest #(rest %),
         ;; リストで入ってくるので、ベクタに直す
         :vector #(into [] %)}))
