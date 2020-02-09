(ns workshop-app.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]))


(defn -main [& _]
  (raj/run-jetty (fn [_]
                   {:status 200
                    :body "Hello world!"})
                 {:port 65535
                  :join? false}))