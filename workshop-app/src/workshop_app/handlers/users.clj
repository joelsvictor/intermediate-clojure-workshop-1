(ns workshop-app.handlers.users
  (:require [workshop-app.db.sqlite :as wads]
            [workshop-app.utils :as wau]
            [cheshire.core :as json])
  (:import (org.sqlite SQLiteException SQLiteErrorCode)))



(defn add-person
  [r]
  (try
    (do (wads/create wads/conn
                     (get-in r [:params :name])
                     (get-in r [:params :dob]))
        {:status 201
         :body   "Created profile."})
    (catch SQLiteException sqle
      (if (= SQLiteErrorCode/SQLITE_CONSTRAINT (.getResultCode sqle))
        {:status 400
         :body "User already exists."}
        (do (.printStackTrace sqle)
            {:status 500
             :body   "Internal server error."})))))


(defn get-person-details
  [dob now]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (when dob
              (json/generate-string {:dob dob
                                     :age (wau/days-between (wau/parse-dt-str dob)
                                                            now)}))})


(defn update-person
  [r]
  (try
    (do (wads/update wads/conn
                     (get-in r [:params :name])
                     (get-in r [:params :dob]))
        {:status 200
         :body "Updated profile."})
    (catch SQLiteException sqle
      (do (.printStackTrace sqle)
          {:status 500
           :body "Internal server error."}))))


(defn delete-person
  [n]
  (try
    (do (wads/delete wads/conn n)
        {:status 200
         :body   "Deleted profile."})
    (catch SQLiteException sqle
      (do (.printStackTrace sqle)
          {:status 500
           :body "Internal server error."}))))


(defn get-handler
  [request]
  {:status 200
   :body   (str "Hello, " (get-in request [:params :name]) " " (get-in request [:params :surname]) "!!!")})