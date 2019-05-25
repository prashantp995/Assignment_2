(ns main
  (:require [clojure.string :as str])
  (:require [clojure.pprint :as pp]))

(use 'clojure.java.io)
(def user)
(def myVec [])
(def row 0)
(def column 0)
(defn ppri2nt [the-array]
  (doseq [a the-array]
    (print a)
    ))

(defn treasure_hunt []
  (with-open [rdr (reader "map.txt")]
    (doseq [line (line-seq rdr)]
      (def row (+ row 1))
      (def column 0)
      (doseq [character (str/split line #"")]
        (def myVec (alength (to-array-2d [line])))
        (def column (+ column 1))
        (println character)))
    (let [the-array (make-array Long/TYPE row column)]
      (dotimes [nn (* row column)]
        (let [ii (quot nn row)
              jj (rem nn column)]
          (aset the-array ii jj nn)
          ))
      (pp/pprint the-array)
      (pp/pprint row)
      (pp/pprint column)
      )))

(treasure_hunt)
