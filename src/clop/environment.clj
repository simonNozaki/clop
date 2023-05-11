(ns clop.environment)

(def global-environment
  (atom {
   :+ (fn [v] (reduce + v)),
   :- (fn [v] (reduce - v)),
   :* (fn [v] (reduce * v)),
   :/ (fn [v] (reduce / v)),
   :> (fn [v] (> (first v) (second v))),
   :>= (fn [v] (>= (first v) (second v))),
   :< (fn [v] (< (first v) (second v))),
   :<= (fn [v] (<= (first v) (second v))),
   := (fn [v] (= (first v) (second v))),
   :first #(first %),
   :second #(second %),
   :last #(last %),
   ; リストで入ってくるので、ベクタに直す
   :list (fn [v] (into [] v)),
   }))
