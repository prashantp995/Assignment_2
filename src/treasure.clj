(ns treasure
  (:require [clojure.string :as str])
  )

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
(def treasure_row_index)
(def treasure_column_index)
(def start_position_row 0)
(def start_position_column 0)
(def maze-array)
(def solution_found false)
(def valid_map true)
(def failed_step \!)
(def success_step \+)
(def treasure_char \@)
(def unexplored_step \-)
(def wall \#)
(def wall_at_start_message "Wall Found at starting position, can not go further,No Treasure found")
(def success_message "Treasure Found")
(def failure_message "Uh oh Treasure Not Found")
(def invalid_map_message "Number of columns mismatch  Map is not valid")
(def empty_map_message "Empty Map Found")
(def map_file "map.txt")                                    ;modify if map file with other name require

; Reference : https://en.wikipedia.org/wiki/Backtracking
; Reference : https://www.geeksforgeeks.org/rat-in-a-maze-backtracking-2/
; Reference For 2D array : https://clojuredocs.org/clojure.core/aset
; Reference :https://clojuredocs.org/clojure.pprint/pprint
; Reference :https://8thlight.com/blog/aaron-lahey/2016/07/20/relationship-between-clojure-functions-symbols-vars-namespaces.html
; Reference :https://purelyfunctional.tv/guide/clojure-collections/
; Reference :https://aphyr.com/posts/303-clojure-from-the-ground-up-functions

(defn print-final-output-array [array-to-print]
  (while (< temp_row_counter row)
    (def temp_column_counter 0)
    (println)                                               ;printing of one row completed
    (while (< temp_column_counter column)
      (print (aget array-to-print temp_row_counter temp_column_counter))
      (def temp_column_counter (+ temp_column_counter 1)))
    (def temp_row_counter (+ temp_row_counter 1)))
  )

(defn is_valid_position [current_row_index current_column_index]
  (if (and (>= current_row_index 0) (< current_row_index total_row) (>= current_column_index 0) (< current_column_index total_column))
    true
    )
  )

(defn is_valid_move [input-array current_row_index current_column_index]
  ;allow to go further with valid positions ,character and solution is not found yet.
  (if (and (is_valid_position current_row_index current_column_index) (= unexplored_step (aget input-array current_row_index current_column_index)) (= false solution_found))
    true
    ))

(defn final-result [the-array msg sol_found]
  (println msg)
  (def solution_found sol_found)
  (print-final-output-array the-array)
  )

(defn explore-tunnel [current_row_index current_column_index input-array]
  (if (and (= current_row_index treasure_row_index) (= current_column_index treasure_column_index))
    (do
      (final-result input-array success_message true)
      )
    )
  (if (= true (is_valid_move input-array current_row_index current_column_index))
    (do
      (aset input-array current_row_index current_column_index success_step)
      (explore-tunnel (+ current_row_index 1) current_column_index input-array) ;;down direction
      (explore-tunnel (- current_row_index 1) current_column_index input-array) ;;up direction
      (explore-tunnel current_row_index (+ current_column_index 1) input-array) ;;right direction
      (explore-tunnel current_row_index (- current_column_index 1) input-array) ;;left direction
      (aset input-array current_row_index current_column_index failed_step) ;; if can not make it to any direction it is failed step
      false
      )
    )
  )

(defn iterateOver [input-array]
  (if (= wall (aget input-array start_position_row start_position_column))
    (do (final-result input-array wall_at_start_message false))
    )
  (if (and (= false (explore-tunnel start_position_row start_position_column input-array)) (= false solution_found))
    (do
      (final-result input-array failure_message false)
      )

    )
  )
(defn addInToArray [row column]
  ;at this point number of row and column is known, this method populate array with the values
  (def maze-array (make-array Character/TYPE row column))
  (with-open [rdr (reader map_file)]
    (doseq [line (line-seq rdr)]
      (def temp_row (+ temp_row 1))
      (def temp_column 0)
      (doseq [character line]
        (def temp_column (+ temp_column 1))
        (let [row_index (- temp_row 1)
              column_index (- temp_column 1)]
          (if (= treasure_char character)
            (do
              (def treasure_row_index row_index)
              (def treasure_column_index column_index)
              )
            )
          (aset maze-array row_index column_index character)))
      )))

(defn treasure_hunt []
  ; this method calculates number of rows and column and validate the map
  (with-open [rdr (reader map_file)]
    (doseq [line (line-seq rdr)]
      (def row (+ row 1))
      (def column 0)
      (doseq [temp_character (str/split line #"")]
        ;(print temp_character)
        (def column (+ column 1))
        )
      (if (= row 1)
        (def previous_column_counter column))
      ;validation for number of column mismatch for given row
      (if (and (not= previous_column_counter column) (not= row 1) (not= false valid_map))
        (do (print invalid_map_message)
            (def valid_map false))
        )
      )

    )
  ;validation for empty map
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
