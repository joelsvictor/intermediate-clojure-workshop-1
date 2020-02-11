(ns workshop-app.handlers.users
  (:require [workshop-app.db.sqlite :as wads]
            [workshop-app.interop :as wai]
            [cheshire.core :as json])
  (:import (java.time LocalDate)))

(defn get-handler
  [request]
  {:status 200
   :body   (str "Hello, "
                (get-in request [:params :name])
                " "
                (get-in request [:params :surname]) "!!!")})


(defn add-person
  [{:keys [name dob]}]
  (if (and name dob)
    (do (wads/create wads/conn name dob)
        {:status 201
         :body "Create user."})
    {:status 400
     :body "Bad request. Missing name or date of birth."}))


(defn get-person
  [name]
  (if name
    (let [dob (wads/read wads/conn name)
          age (when (.isAfter dob now)
                (.between java.time.temporal.ChronoUnit/DAYS (java.time.LocalDate/parse dob) (java.time.LocalDate/now)))]
          ;; age (wai/days-between (LocalDate/parse dob) (LocalDate/now))]
      {:status  200
       :headers {"content-type" "application/json"}
       :body    (json/generate-string {:name name
                                       :dob dob
                                       :age age})})
    {:status 400
     :body "Bad request. Missing name."}))


(defn update-person
  [{:keys [name dob]}]
  (if (and name dob)
    (do (wads/update wads/conn name dob)
        {:status 200
         :body "Updated user."})
    {:status 400
     :body "Bad request. Missing name or date of birth."}))


(defn delete-person
  [name]
  (if name
    (do
      (wads/delete wads/conn name)
      {:status  200
       :body "Deleted user."})
    {:status 400
     :body "Bad request. Missing name."}))
