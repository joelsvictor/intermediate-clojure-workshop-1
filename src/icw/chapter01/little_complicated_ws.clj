(ns icw.chapter01.little-complicated-ws
  (:require [ring.adapter.jetty :as raj]
            [compojure.core :refer [defroutes GET ANY]]
            [clojure.string :as s]))

;; Task modify this to say to hello to the person whose name is coming from
;; the request
(defn hello-world-handler
  [r]
  {:status 200
   :body   "Hello, World!"})


(defroutes crud-routes
           (GET "/" _ hello-world-handler)
           (ANY "*" _ {:status 404}))


(defn -main [& _]
  (raj/run-jetty crud-routes
                 {:port 65535
                  :join? false}))