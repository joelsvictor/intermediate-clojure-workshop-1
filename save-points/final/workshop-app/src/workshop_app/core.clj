(ns workshop-app.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [workshop-app.routes :as war]
            [workshop-app.middlewares.users :as wamu]))


(defn -main [& _]
  (raj/run-jetty (-> war/app-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params
                     wamu/handle-any-exception
                     wamu/append-slash)
                 {:port 65535
                  :join? false}))