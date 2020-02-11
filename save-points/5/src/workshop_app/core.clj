(ns workshop-app.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [workshop-app.routes :as war]
            [workshop-app.db.sqlite :as wads]))


(defn -main
  [& _]
  (wads/create-table wads/conn)
  (raj/run-jetty (-> war/app-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))