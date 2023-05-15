(ns clop.repl
  (:use [clop.core])
  (:gen-class))


;; REPL。sigkillを受信するまで対話的環境を提供する

(defn evaluate-script
  []
  (print "-> ")
  (flush)
  (let [newline (read-line)]
    (if (= newline (or "exit" "quit"))
      (println "Bye now!"))
    (println (evaluate newline))))

(defn start-repl
  []
  (println "Clop 0.1.0")
  (loop []
    (do
      (try
        (evaluate-script)
        (catch Exception e
          (do
            (.printStackTrace e)
            (evaluate-script)))))))

(defn -main
  []
  (start-repl))
