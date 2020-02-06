(ns icw.chapter03.fixtures
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [icw.chapter03.testable-ws-1 :as ictw]
            [icw.chapter02.sqlite :as ics])
  (:import (java.sql DriverManager)))


;; Pattern 1
;; Using fixtures to actually to bootstrap and teardown.
;; I want to test against a actual instance but I want to test
;; against a different database.
(use-fixtures :each (fn [t]
                      (with-redefs [ics/conn (DriverManager/getConnection "jdbc:sqlite:test_database_1") ]
                        (let [statement (.createStatement ics/conn)]
                          (.executeUpdate statement "create table if not exists person(name string primary key, dob string)")
                          (.close statement))
                        (t)
                        (io/delete-file (io/file "test_database_1")))))


(deftest add-person-test
  (is (= {:status 201
          :body   "Created profile."}
         (ictw/add-person {:params {:name "Joel Victor"
                                    :dob  "2001-01-01"}}))))


(deftest get-person-test
  (is (= {:status  200
          :headers {"content-type" "application/json"}
          :body    "{\"dob\":\"2001-01-01\",\"age\":19}"}
         (do (ictw/add-person {:params {:name "Joel Victor"
                                        :dob  "2001-01-01"}})
             (ictw/get-person {:params {:name "Joel Victor"}})))))