(ns workshop-app.routes
  (:require [workshop-app.handlers.users :as wahu]
            [workshop-app.db.sqlite :as wads]
            [compojure.core :refer [defroutes GET POST PUT DELETE ANY]])
  (:import (java.time LocalDate)))


(defroutes crud-routes
           (POST "/:name" _ wahu/add-person)
           (GET "/:name" {:keys [params]} (wahu/get-person-details (get (wads/read wads/conn (:name params)) "dob")
                                                                   (LocalDate/now)))
           (PUT "/:name" _ wahu/update-person)
           (DELETE "/:name" {:keys [params]} (wahu/delete-person (:name params)))
           (ANY "*" _ {:status 404}))
