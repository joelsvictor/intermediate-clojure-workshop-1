(ns workshop-app.routes
  (:require [workshop-app.handlers.users :as wahu]
            [workshop-app.db.sqlite :as wads]
            [workshop-app.middlewares.users :as wamu]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]])
  (:import (java.time LocalDate)))


(defroutes crud-routes
           (GET "/" _ wahu/get-handler)
           (POST "/:name" _ (-> wahu/add-person
                                wamu/is-dob-before-today?
                                wamu/validate-dob-format))
           (GET "/:name" [name] (wahu/get-person-details (get (wads/read wads/conn name) "dob")
                                                         (LocalDate/now)))
           (PUT "/:name" _ (-> wahu/update-person
                               wamu/is-dob-before-today?
                               wamu/validate-dob-format))
           (DELETE "/:name" {:keys [params]} (wahu/delete-person (:name params)))
           (ANY "*" _ {:status 404}))
