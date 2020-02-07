(ns icw.chapter03.mocking-fns
  (:require [clojure.test :refer :all]
            [icw.chapter03.testable-ws-1 :as ictw]
            [icw.chapter02.sqlite :as ics]))


;; Pattern 2
(deftest add-person-test
  (with-redefs [ics/create (fn [_ k v] {:name "Joel Victor"
                                        :dob  "2001-01-01"})]
    (is (= {:status 201
            :body   "Created profile."}
        (ictw/add-person {:params {:name "Joel Victor"
                                   :dob  "2001-01-01"}})))))


;; Pattern 2 contd. but this test is flaky since you cannot mock interop'ed operations
;; like (LocalDate/now)
(deftest get-person-test
  (with-redefs [ics/read (fn [_ k] "2001-01-01")]
    (is (= {:status 200
            :headers {"content-type" "application/json"}
            :body   "{\"dob\":\"2001-01-01\",\"age\":19}"}
           (ictw/get-person {:params {:name "Joel Victor"}})))))