(ns workshop-app.mocking-fns
  (:require [clojure.test :refer :all]
            [workshop-app.handlers.users :as wahu]
            [workshop-app.db.sqlite :as wads])
  (:import (java.time LocalDate)))


;; Pattern 2
(deftest add-person-test
  (with-redefs [wads/create! (fn [_ k v] {:name "Joel Victor"
                                          :dob   "2001-01-01"})]
    (is (= {:status 201
            :body   "Created profile."}
         (wahu/add-person {:params {:name "Joel Victor"
                                    :dob  "2001-01-01"}})))))


;; Pattern 2 contd. but this test is flaky since you cannot mock interop'ed operations
;; like (LocalDate/now)
(deftest get-person-test
  (with-redefs [wads/read (fn [_ k] "2001-01-01")]
    (is (= {:status 200
            :headers {"content-type" "application/json"}
            :body   "{\"dob\":\"2000-01-01\",\"age\":20}"}
           (wahu/get-person-details "2000-01-01"
                                    (LocalDate/parse "2020-02-14"))))))