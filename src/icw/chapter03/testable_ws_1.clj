(ns icw.chapter03.testable-ws-1
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [icw.chapter02.sqlite :as ics]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]])
  (:import (java.time LocalDate)
           (java.time.temporal ChronoUnit)))


(defn add-person
  [r]
  (do (ics/create ics/conn
                   (get-in r [:params :name])
                   (get-in r [:params :dob]))
      {:status 201
       :body "Created profile."}))


(defn get-person
  [r]
  (let [dob (ics/read ics/conn (get-in r [:params :name]))
        parsed-dob (when (seq dob)
                     (LocalDate/parse dob))]
    {:status  200
     :headers {"content-type" "application/json"}
     :body    (json/generate-string {:dob dob
                                     :age (when (.isAfter (LocalDate/now) parsed-dob)
                                            (.between ChronoUnit/YEARS parsed-dob (LocalDate/now)))})}))


(defn update-person
  [r]
  (do (ics/update ics/conn (get-in r [:params :name]) (get-in r [:params :dob]))
      {:status 200
       :body "Updated profile."}))


(defn delete-person
  [n]
  (do (ics/delete ics/conn n)
      {:status 200
       :body "Deleted profile."}))


(defroutes crud-routes
           (POST "/:name" _ add-person)
           (GET "/:name" _ get-person)
           (PUT "/:name" _ update-person)
           (DELETE "/:name" {:keys [params]} (delete-person (:name params)))
           (ANY "*" _ {:status 404}))


(defn -main [& _]
  (raj/run-jetty (-> crud-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))