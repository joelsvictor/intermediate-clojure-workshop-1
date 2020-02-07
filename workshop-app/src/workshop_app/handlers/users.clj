(ns workshop-app.handlers.users
  (:require [workshop-app.db.sqlite :as wads]
            [workshop-app.utils :as wau]
            [cheshire.core :as json]))



(defn add-person
  [r]
  (do (wads/create wads/conn
                   (get-in r [:params :name])
                   (get-in r [:params :dob]))
      {:status 201
       :body "Created profile."}))


(defn get-person-resp
  [dob now]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (json/generate-string {:dob dob
                                   :age (wau/days-between (wau/parse-dt-str dob)
                                                      now)})})


(defn update-person
  [r]
  (do (wads/update wads/conn
                   (get-in r [:params :name])
                   (get-in r [:params :dob]))
      {:status 200
       :body "Updated profile."}))


(defn delete-person
  [n]
  (do (wads/delete wads/conn n)
      {:status 200
       :body "Deleted profile."}))

