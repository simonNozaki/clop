(ns clop.repl
  (:use [clop.core])
  (:gen-class))


;; REPL。sigkillを受信するまで対話的環境を提供する

(defn start-repl
  []
  (println "Clop 0.1.0")
  (while true
    (do
      (print "-> ")
      (flush)
      (let [newline (read-line)]
        (println (evaluate newline))))))

(defn -main
  []
  (start-repl))
