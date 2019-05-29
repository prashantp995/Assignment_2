(ns main
  (:require [clojure.string :as str])
  (:require [clojure.pprint :as pp]))

(use 'clojure.java.io)
(def row 0)
(def column 0)
(def total_row 0)
(def total_column 0)
(def temp_column 0)
(def temp_row 0)
(def temp_row_counter 0)
(def temp_column_counter 0)
(def previous_column_counter 0)
(def solution_row 0)
(def solution_column 0)
(def maze-array)
(def solution_found false)
(def valid_map true)


(defn print-maze-to-output [the-array]
  (while (< temp_row_counter row)
    (def temp_column_counter 0)
    (println)
    (while (< temp_column_counter column)
      (print (aget the-array temp_row_counter temp_column_counter))
      (def temp_column_counter (+ temp_column_counter 1)))
    (def temp_row_counter (+ temp_row_counter 1)))
  ;(System/exit 1)
  )

(defn valid_move [the-array x y]
  (if (and (>= x 0) (< x total_row) (>= y 0) (< y total_column) (= (char (first (.getBytes "-"))) (aget the-array x y)) (= false solution_found))
    true
    ))

(defn maze-solution [x y the-array]
  (if (and (= y solution_column) (= x solution_row))
    (do
      (println "I found the treasure")
      (print-maze-to-output the-array)
      (def solution_found true)
      )
    )
  (if (= true (valid_move the-array x y))
    (do
      (aset the-array x y \+)
      (maze-solution (+ x 1) y the-array)                   ;;down direction
      (maze-solution (- x 1) y the-array)                   ;;up direction
      (maze-solution x (+ y 1) the-array)                   ;;right direction
      (maze-solution x (- y 1) the-array)                   ;;left direction
      (aset the-array x y \!)
      false
      )
    )
  )

(defn iterateOver [the-array]
  (if (and (= false (maze-solution 0 0 the-array)) (= false solution_found))
    (do (println "No treasure found")
        (print-maze-to-output the-array))

    )
  )
(defn addInToArray [row column]
  (def maze-array (make-array Character/TYPE row column))
  (with-open [rdr (reader "map.txt")]
    (doseq [line (line-seq rdr)]
      (def temp_row (+ temp_row 1))
      (def temp_column 0)
      (doseq [character line]
        (def temp_column (+ temp_column 1))
        (let [ii (- temp_row 1)
              jj (- temp_column 1)]
          (if (= (char (first (.getBytes "@"))) character)
            (do
              (def solution_row ii)
              (def solution_column jj)
              )
            )
          (aset maze-array ii jj character)))
      )))

(defn treasure_hunt []
  (with-open [rdr (reader "map.txt")]
    (doseq [line (line-seq rdr)]
      (def row (+ row 1))
      (def column 0)
      (doseq [character (str/split line #"")]
        (def column (+ column 1))
        )
      (if (= row 1)
        (def previous_column_counter column))
      (if (and (not= previous_column_counter column) (not= row 1) (not= false valid_map))
        (do (print "number of columns mismatch  map is not valid")
            (def valid_map false))
        )
      )

    )
  (if (= true valid_map)
    (do (addInToArray row column)
        (def total_row row)
        (def total_column column)
        (iterateOver maze-array))

    ))


(treasure_hunt)
