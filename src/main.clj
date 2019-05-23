(ns main)
(use 'clojure.java.io)
(with-open [rdr (reader "map.txt")]
  (doseq [line (line-seq rdr)]
    (println line)))
