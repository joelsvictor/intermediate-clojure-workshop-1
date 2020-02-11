(ns workshop-app.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [workshop-app.routes :as war]))


(defn -main [& _]
  (raj/run-jetty (-> war/crud-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))