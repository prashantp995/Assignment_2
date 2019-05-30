(ns treasure
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
(def failed_step \!)
(def success_step \+)
(def treasure_char \@)
(def unexplored_step \-)
(def success_message "Treasure Found")
(def failure_message "Uh oh Treasure Not Found")
(def invalid_map_message "Number of columns mismatch  Map is not valid")
(def empty_map_message "Empty Map Found")

; Reference : https://en.wikipedia.org/wiki/Backtracking
; Reference : https://www.geeksforgeeks.org/rat-in-a-maze-backtracking-2/
; Reference For 2D array : https://clojuredocs.org/clojure.core/aset
; Reference :https://clojuredocs.org/clojure.pprint/pprint
; Reference :https://8thlight.com/blog/aaron-lahey/2016/07/20/relationship-between-clojure-functions-symbols-vars-namespaces.html
; Reference :https://purelyfunctional.tv/guide/clojure-collections/
; Reference :https://aphyr.com/posts/303-clojure-from-the-ground-up-functions

(defn print-final-output-array [the-array]
  (while (< temp_row_counter row)
    (def temp_column_counter 0)
    (println)                                               ;printing of one row completed
    (while (< temp_column_counter column)
      (print (aget the-array temp_row_counter temp_column_counter))
      (def temp_column_counter (+ temp_column_counter 1)))
    (def temp_row_counter (+ temp_row_counter 1)))
  )

(defn validate_position [x y]
  (if (and (>= x 0) (< x total_row) (>= y 0) (< y total_column))
    true
    )
  )

(defn is_valid_move [input-array x y]
  (if (and (validate_position x y) (= unexplored_step (aget input-array x y)) (= false solution_found))
    true
    ))

(defn final-result [the-array msg sol_found]
  (println msg)
  (def solution_found sol_found)
  (print-final-output-array the-array)
  )

(defn explore-tunnel [x y input-array]
  (if (and (= y solution_column) (= x solution_row))
    (do
      (final-result input-array success_message true)
      )
    )
  (if (= true (is_valid_move input-array x y))
    (do
      (aset input-array x y success_step)
      (explore-tunnel (+ x 1) y input-array)                ;;down direction
      (explore-tunnel (- x 1) y input-array)                ;;up direction
      (explore-tunnel x (+ y 1) input-array)                ;;right direction
      (explore-tunnel x (- y 1) input-array)                ;;left direction
      (aset input-array x y failed_step)                    ;; if can not make it to any direction it is failed step
      false
      )
    )
  )

(defn iterateOver [the-array]
  (if (and (= false (explore-tunnel 0 0 the-array)) (= false solution_found))
    (do
      (final-result the-array failure_message false)
      )

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
        (let [row_index (- temp_row 1)
              column_index (- temp_column 1)]
          (if (= treasure_char character)
            (do
              (def solution_row row_index)
              (def solution_column column_index)
              )
            )
          (aset maze-array row_index column_index character)))
      )))

(defn treasure_hunt []
  (with-open [rdr (reader "map.txt")]
    (doseq [line (line-seq rdr)]
      (def row (+ row 1))
      (def column 0)
      (doseq [temp_character (str/split line #"")]
        ;(print temp_character)
        (def column (+ column 1))
        )
      (if (= row 1)
        (def previous_column_counter column))
      (if (and (not= previous_column_counter column) (not= row 1) (not= false valid_map))
        (do (print invalid_map_message)
            (def valid_map false))
        )
      )

    )
  (if (= row 0)
    (do
      (def valid_map false)
      (final-result "" empty_map_message false))
    )
  (if (= true valid_map)
    (do (addInToArray row column)
        (def total_row row)
        (def total_column column)
        (iterateOver maze-array))

    ))


(treasure_hunt)
