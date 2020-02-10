(ns workshop-app.routes
  (:require [workshop-app.handlers.users :as wahu]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]))


(defroutes crud-routes
           (GET "/" _ wahu/get-handler)
           (POST "/:name" {:keys [params]} (wahu/add-person params))
           (GET "/:name" [name] (wahu/get-person name))
           ;; Task: Add PUT and DELETE routes to update and delete respectively.
           (ANY "*" _ {:status 404}))
