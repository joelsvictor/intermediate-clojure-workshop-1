(ns workshop-app.utils
  (:import (java.time LocalDate)
           (java.time.temporal ChronoUnit)))


(defn parse-dt-str
  [dt-str]
  (when (seq dt-str)
    (LocalDate/parse dt-str)))


(defn dt-after?
  [d1 d2]
  (.isAfter d1 d2))


(defn days-between
  [d1 d2]
  (.between ChronoUnit/YEARS d1 d2))