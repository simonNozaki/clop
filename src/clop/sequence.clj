(ns clop.sequence)

(defn v-to-l [vector]
  "PersistentVectorをListに変換する。引数がVectorではない場合空のListを返す"
  (concat (quote ()) vector))
