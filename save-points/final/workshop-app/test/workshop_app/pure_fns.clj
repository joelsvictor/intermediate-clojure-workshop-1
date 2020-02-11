(ns workshop-app.pure-fns
  (:require [clojure.test :refer :all]
            [workshop-app.handlers.users :as wahu])
  (:import (java.time LocalDate)))


;; Write more tests.
;; Intro to generative testing.
;; Use testing, assertion message
;; Explain deftest, is, testing, are
(deftest pure-get-person-test
  (is (= {:status 200
          :headers {"content-type" "application/json"}
          :body "{\"dob\":\"2000-01-01\",\"age\":20}"}
         (wahu/get-person "2000-01-01" (LocalDate/parse "2020-02-14")))))