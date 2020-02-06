(ns chapter01.simple-web-server
  (:require [ring.adapter.jetty :as raj]))


(defn hello-world-handler
  [r]
  {:status 200
   :body "Hello World"})


(defn -main
  [& _]
  (raj/run-jetty hello-world-handler {:port 65535
                                      :join? false}))