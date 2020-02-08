(ns workshop-app.fixtures
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [workshop-app.handlers.users :as wahu]
            [workshop-app.db.sqlite :as wads])
  (:import (java.sql DriverManager)
           (java.time LocalDateTime LocalDate)))


;; Pattern 1
;; Using fixtures to actually to bootstrap and teardown.
;; I want to test against a actual instance but I want to test
;; against a different database.
(use-fixtures :each (fn [t]
                      (with-redefs [wads/conn (DriverManager/getConnection "jdbc:sqlite:test_database_1")]
                        (let [statement (.createStatement wads/conn)]
                          (.executeUpdate statement "create table if not exists person(name string primary key, dob string)")
                          (.close statement))
                        (t)
                        (io/delete-file (io/file "test_database_1")))))


(deftest add-person-test
  (is (= {:status 201
          :body   "Created profile."}
         (wahu/add-person {:params {:name "Joel Victor"
                                    :dob  "2001-01-01"}}))))


(deftest get-person-test
  (is (= {:status  200
          :headers {"content-type" "application/json"}
          :body    "{\"dob\":\"2000-01-01\",\"age\":20}"}
         (wahu/get-person-details "2000-01-01"
                                  (LocalDate/parse "2020-02-14")))))