(ns icw.chapter03.testable-ws-2
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


(defn parse-dt-str
  [dt-str]
  (when (seq dt-str)
    (LocalDate/parse dt-str)))


(defn days-between
  [d1 d2]
  (when (.isAfter d2 d1)
    (.between ChronoUnit/YEARS d1 d2)))


(defn get-person
  [dob now]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (json/generate-string {:dob dob
                                   :age (days-between (parse-dt-str dob)
                                                      now)})})


(defn update-person
  [r]
  (do (ics/update ics/conn
                  (get-in r [:params :name])
                  (get-in r [:params :dob]))
      {:status 200
       :body "Updated profile."}))


(defn delete-person
  [n]
  (do (ics/delete ics/conn n)
      {:status 200
       :body "Deleted profile."}))


(defroutes crud-routes
           (POST "/:name" _ add-person)
           (GET "/:name" {:keys [params]} (get-person (ics/read ics/conn (:name params))
                                                      (LocalDate/now)))
           (PUT "/:name" _ update-person)
           (DELETE "/:name" {:keys [params]} (delete-person (:name params)))
           (ANY "*" _ {:status 404}))


(defn -main [& _]
  (raj/run-jetty (-> crud-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))