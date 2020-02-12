(ns workshop-app.handlers.users
  (:require [workshop-app.db.sqlite :as wads]
            [workshop-app.utils :as wau]
            [cheshire.core :as json])
  (:import (org.sqlite SQLiteException SQLiteErrorCode)
           (java.time LocalDate)))


(defn get-handler
  [{:keys [params] :as request}]
  (def r* request)
  (let [{:keys [name surname]} params]
    (if (and name surname)
      {:status 200
       :body   (str "Hello, "
                    name
                    " "
                    surname
                    "!!!")}
      {:status 400
       :body "Missing name and surname."})))


;; try {
;;   create profile ...;
;; } catch (SQLiteException sqle) {
;;  if (SQLiteErrorCode.SQLITE_CONSTRAINT == sqle.getResultCode()) {
;;    return ...;
;;  } else {
;;    throw sqle;
;;  }
;; }
(defn add-person
  [{:keys [name dob]}]
  (if (and name dob)
    (do (wads/create! wads/conn
                      name
                      dob)
        {:status 201
         :body   "Created user."})
    {:status 400
     :body   "User name or date of birth missing."}))


(defn get-person
  [dob now]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (when dob
              (json/generate-string {:dob dob
                                     :age (wau/days-between (wau/parse-dt-str dob)
                                                            now)}))})


(defn update-person
  [{:keys [name dob]}]
  (if (and name dob)
    (do (wads/update! wads/conn
                      name
                      dob)
        {:status 200
         :body   "Updated user."})
    {:status 400
     :body "User name or dob is missing."}))


(defn delete-person
  [name]
  (if name
    (do (wads/delete! wads/conn name)
        {:status 200
         :body   "Deleted user."})
    {:status 400
     :body "User name missing, cannot delete person without username."}))