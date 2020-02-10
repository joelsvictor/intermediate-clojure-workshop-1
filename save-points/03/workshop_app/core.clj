(ns workshop-app.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]
            [clojure.string :as s]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]))


;; Task: Make changes to this handler to say Hello <name>
;; You can see what request is being bound to a variable and
;; you can use the functions provided by clojure.string to parse
;; the query string. Use functions like keys and get to inspect
;; the request in the REPL.
(defn get-handler
  [request]
  (def r* request)
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