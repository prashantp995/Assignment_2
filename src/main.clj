(ns main)
(use 'clojure.java.io)
(with-open [read (reader "map.txt")]
  (doseq [line (line-seq read)]
    (if (re-find #"@" line) (print line)))
  )
