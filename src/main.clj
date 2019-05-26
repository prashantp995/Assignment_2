(ns main
  (:require [clojure.string :as str])
  (:require [clojure.pprint :as pp]))

(use 'clojure.java.io)
(def user)
(def myVec [])
(def row 0)
(def column 0)
(def temp_column 0)
(def temp_row 0)
(def temp_row_counter 0)
(def temp_column_counter 0)
(def temp_character "")

(defn iterateOver [row column the-array]
  (while (< temp_row_counter row)
    (def temp_column_counter 0)
    (println)
    (while (< temp_column_counter column)
      (print (aget the-array temp_row_counter temp_column_counter))
      (def temp_character (aget the-array temp_row_counter temp_column_counter))
      (def temp_character (str temp_character))
      (if (= "@" temp_character)
        (print "treasure found")
        )
      (def temp_column_counter (+ temp_column_counter 1)))
    (def temp_row_counter (+ temp_row_counter 1))))
(defn addInToArray [row column]
  (let [the-array (make-array Character/TYPE row column)]
    (with-open [rdr (reader "map.txt")]
      (doseq [line (line-seq rdr)]
        (def temp_row (+ temp_row 1))
        (def temp_column 0)
        (doseq [character line]
          (def temp_column (+ temp_column 1))
          (let [ii (- temp_row 1)
                jj (- temp_column 1)]
            (aset the-array ii jj character)
            ))
        ) (pp/pprint the-array)) (iterateOver row column the-array)))

(defn treasure_hunt []
  (with-open [rdr (reader "map.txt")]
    (doseq [line (line-seq rdr)]
      (def row (+ row 1))
      (def column 0)
      (doseq [character (str/split line #"")]
        (def myVec (alength (to-array-2d [line])))
        (def column (+ column 1))
        ))
    (addInToArray row column)
    ))

(treasure_hunt)
