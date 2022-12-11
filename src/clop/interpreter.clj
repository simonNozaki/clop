(ns clop.interpreter
  (:use [clop.environment]))

(defn interpret [token, environment]
  (cond
    (keyword? token) (get environment token)
    (vector? token)
      (cond
           (= :quote (first token)) (second token)
           (= :if (first token)) (let [
                     _ (first token),
                     condition (second token),
                     conseq (get token 2),
                     alt (get token 3)
                     result (when (interpret condition environment) conseq alt)]
                 (interpret result environment))
           (= :define (first token)) (let [_ (first token),
                         variable (second token),
                         exp (get token 2),
                         value (interpret exp environment)]
                     (swap! environment assoc variable value)
                     variable)
           (= :lambda (first token)) (let [_ (first token),
                                          vars (second token),
                                          exp (get token 2)]
                                      ; 無名関数（ラムダ）を返す
                                      (fn [& args] (let [var-kvs (zipmap vars (into-array args))
                                                         lambda-env (map #(assoc @environment (first %) (second %)) var-kvs)]
                                                     (interpret exp lambda-env))))
           :else (let [vals (map #(interpret % @environment) token),
                       ; リストの1つめはグローバルに定義された変数ないしは関数に紐づく関数のはず
                       proc (first vals),
                       args (rest vals)]
                   (println args)
                   (proc args)))
    :else token))
