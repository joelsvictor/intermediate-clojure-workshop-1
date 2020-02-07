(ns icw.chapter03.pure-fns
  (:require [clojure.test :refer :all]
            [icw.chapter03.testable-ws-2 :as ict2])
  (:import (java.time LocalDate)))


(deftest pure-get-person-test
  (is (= {:status 200
          :headers {"content-type" "application/json"}
          :body "{\"dob\":\"2000-01-01\",\"age\":20}"}
         (ict2/get-person "2000-01-01" (LocalDate/parse "2020-01-01")))))