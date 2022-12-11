(ns clop.environment)

(def global-environment
  (atom {
   :+ (fn [v] (reduce + v)),
   :- (fn [v] (reduce - v)),
   :* (fn [v] (reduce * v)),
   :/ (fn [v] (reduce / v)),
   :first #(first %),
   :second #(second %),
   :last #(last %),
   ; リストで入ってくるので、ベクタに直す
   :cons (fn [v] (into [] v)),
   }))
