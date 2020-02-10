(ns workshop-app.handlers.users
  (:require [workshop-app.db.in-mem :as wadim]
            [cheshire.core :as json]))

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
    (do (wadim/create wadim/conn name dob)
        {:status 201
         :body "Create user."})
    {:status 400
     :body "User name or date of birth missing."}))


(defn get-person
  [name]
  (if name
    (let [dob (wadim/read wadim/conn name)]
      {:status  200
       :headers {"content-type" "application/json"}
       :body    (json/generate-string {:name name :dob dob})})
    {:status 400
     :body "Bad request. Missing name."}))


(defn update-person
  [{:keys [name dob]}]
  (if (and name dob)
    (do (wadim/update wadim/conn name dob)
        {:status 200
         :body "Updated user."})
    {:status 400
     :body "Bad request. Missing name or date of birth."}))


(defn delete-person
  [name]
  (if name
    (do (wadim/delete wadim/conn name)
      {:status  200
       :body "Deleted user."})
    {:status 400
     :body "Bad request. Missing name."}))
