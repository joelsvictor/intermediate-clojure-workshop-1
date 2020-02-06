(ns chapter01.little-more-complicated-ws
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [chapter01.in-mem-database :as cims]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]))

;; following functions are available from in-mem-database namespace
;; cims/create cims/conn key value
;; cims/read cims/conn key value
;; cims/update cims/conn key value
;; cims/delete cims/conn key value
(defn add-person
  [r]
  (do (cims/create cims/conn
                   (get-in r [:params :name])
                   (dissoc (:params r) :name))
      {:status 201
       :body "Created profile."}))


(defn get-person
  [r]
  {:status  200
   :headers {"content-type" "application/json"}
   :body    (json/generate-string (cims/read cims/conn (get-in r [:params :name])))})


(defn update-person
  [r]
  (do (cims/update cims/conn (get-in r [:params :name]) (dissoc (:params r) :name))
      {:status 200
       :body "Updated profile."}))


(defn delete-person
  [n]
  (do (cims/delete cims/conn n)
      {:status 200
       :body "Deleted profile."}))


(defroutes crud-routes
           (POST "/:name" _ add-person)
           (GET "/:name" _ get-person)
           ;; Task 1: update the details about a person
           (PUT "/:name" _ update-person)
           ;; Task 2: delete the details about a person, because #gdpr
           (DELETE "/:name" {:keys [params]} (delete-person (:name params)))
           (ANY "*" _ {:status 404}))


(defn -main [& _]
  (raj/run-jetty (-> crud-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))