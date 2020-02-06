(ns icw.chapter02.little-more-complicated-ws
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [icw.chapter02.sqlite :as ics]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]))

(defn add-person
  [r]
  (do (ics/create ics/conn
                   (get-in r [:params :name])
                   (get-in r [:params :dob]))
      {:status 201
       :body "Created profile."}))


(defn get-person
  [r]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (json/generate-string {:dob (ics/read ics/conn (get-in r [:params :name]))})})


(defroutes crud-routes
           (POST "/:name" _ add-person)
           (GET "/:name" _ get-person)
           ;; Task 1: update the details about a person
           #_(_ _ _ _)
           ;; Task 2: delete the details about a person, because #gdpr
           #_(_ _ _ _)
           (ANY "*" _ {:status 404}))


(defn -main [& _]
  (raj/run-jetty (-> crud-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))