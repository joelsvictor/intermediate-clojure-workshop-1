(ns workshop-app.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]
            [clojure.string :as s]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]))


;; Task: Make changes to this handler to say Hello <name>
;; You can see what request is being printed and you can use functions provided
;; by clojure.string
(defn get-handler
  [request]
  (println request)
  {:status 200
   :body "Hello world!!!"})


(defroutes app-routes
           (GET "/" _ get-handler)
           (ANY "*" _ {:status 400
                       :body "Not found"}))


(defn -main [& _]
  (raj/run-jetty app-routes
                 {:port 65535
                  :join? false}))