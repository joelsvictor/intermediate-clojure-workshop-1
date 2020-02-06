(ns chapter01.little-complicated-ws
  (:require [ring.adapter.jetty :as raj]
            [compojure.core :refer [defroutes GET ANY]]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]))


(defn hello-world-handler
  [r]
  {:status 200
   :body   (str "Hello, "
                (get-in r [:params :name])
                "!")})


(defroutes crud-routes
           (GET "/" _ hello-world-handler)
           (ANY "*" _ {:status 404}))


(defn -main [& _]
  (raj/run-jetty (-> crud-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))