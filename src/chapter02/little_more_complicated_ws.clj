(ns chapter02.little-more-complicated-ws
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [chapter02.sqlite :as cs]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]))

(defn add-person
  [r]
  (do (cs/create cs/conn
                   (get-in r [:params :name])
                   (get-in r [:params :dob]))
      {:status 201
       :body "Created profile."}))


(defn get-person
  [r]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (json/generate-string {:dob (cs/read cs/conn (get-in r [:params :name]))})})


(defn update-person
  [r]
  (do (cs/update cs/conn (get-in r [:params :name]) (get-in r [:params :dob]))
      {:status 200
       :body "Updated profile."}))


(defn delete-person
  [n]
  (do (cs/delete cs/conn n)
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