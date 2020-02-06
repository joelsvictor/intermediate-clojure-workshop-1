(ns chapter02.sqlite
  (:refer-clojure :rename {update cc-update
                           read cc-read})
  (:import (java.sql DriverManager Connection PreparedStatement)))

(def conn (DriverManager/getConnection "jdbc:sqlite::memory:"))

(let [statement (.createStatement conn)]
  (.executeUpdate statement "create table if not exists person(name string primary key, dob string)"))


(defn create
  [conn k v]
  (let [statement (.prepareStatement ^Connection conn "insert into person values(?, ?)")]
    (.setString ^PreparedStatement statement 1 k)
    (.setString ^PreparedStatement statement 2 v)
    (.executeUpdate statement)
    (.close statement)))


;; task 1
(defn update
  [conn k v]
  (with-open [statement (.prepareStatement ^Connection conn "update person set dob=? where name=?")]
    (.setString ^PreparedStatement statement 2 k)
    (.setString ^PreparedStatement statement 1 v)
    (.executeUpdate statement)))


;; task 2
(defn delete
  [conn k]
  (with-open [statement (.prepareStatement ^Connection conn "delete from person where name=?")]
    (.setString ^PreparedStatement statement 1 k)
    (.executeUpdate statement)))


;; task 3
(defn read
  [conn k]
  (with-open [statement (doto (.prepareStatement ^Connection conn "select name, dob from person where name=?")
                    (.setString 1 k))]
    (with-open [rs (.executeQuery statement)]
      (when-let [rs* (.next rs)]
        (.getString rs 2)))))