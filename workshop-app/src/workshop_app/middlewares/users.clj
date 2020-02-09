(ns workshop-app.middlewares.users
  (:require [workshop-app.utils :as wau])
  (:import (java.time LocalDate)))


(defn is-dob-before-today?
  [handler]
  (fn [request]
    (if (wau/dt-after? (LocalDate/now)
                       (wau/parse-dt-str (get-in request [:params :dob])))

      (handler request)
      {:status 400
       :body "Date of birth cannot be after current date."})))


(defn validate-dob-format
  [handler]
  (fn [request]
    (if-let [dob (get-in request [:params :dob])]
      (try (do (wau/parse-dt-str dob)
               (handler request))
           (catch Exception e
             (.printStackTrace e)
             {:status 400
              :body "Invalid date format."}))
      {:status 400
       :body "Date is missing."})))


(defn handle-any-exception
  [handler]
  (fn [request]
    (try (handler request)
         (catch Exception e
           (.printStackTrace e)
           {:status 500
            :body "Internal server exception."}))))