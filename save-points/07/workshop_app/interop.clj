(ns workshop-app.interop
  (:refer-clojure :rename {get cc-get})
  (:import (java.util Calendar)))

;; class access
;; HashMap.class
java.util.HashMap                                           ;; a class

;; new HashMap();
(java.util.HashMap.)                                        ;; an object created from the class

;; HashMap hm = new HashMap();
(def hm (java.util.HashMap.))                               ;; bind the object to a var

;; hm.put("a", 10);
(.put hm "a" 10)

;; hm.put("b", 10);
(.put ^java.util.HashMap hm "b" 10)                         ;; the first gives us a warning of how it cannot be resolved.

;; Member access
;; method acess
;; hm.get("a")
(.get hm "a")

;; hm.get("b")
(.get hm "b")

;; "joel".toUpperCase();
(.toUpperCase "joel")

;; field access
;; new Point(10,20).x;
(.-x (java.awt.Point. 10 20))

;; static variables or methods access
;; Calendar.ERA
Calendar/ERA

;; Math.PI
Math/PI

;; System.getProperties()
(System/getProperties)

;; dot special form
;; hm.get("a");
(. hm get "a")

;; "joel".toUpperCase();
(. "joel" toUpperCase)

;; System.getProperties().get("os.name")
(. (. System (getProperties)) (get "os.name"))

;; double dot
(.. System getProperties (get "os.name"))

;; doto
;; HashMap dotoHm = new HashMap();
;; dotoHm.put("a", 10);
;; dotoHm.put("b", 20);
(def doto-hm (doto (java.util.HashMap.)
               (.put "a" 10)
               (.put "b" 20)))

;; new
;; HashMap newSyntaxHm = new HashMap();
(def new-syntax-hm (new java.util.HashMap))

;; accessing inner classes
;; AbstractMap.SimpleEntry("a", "b")
(java.util.AbstractMap$SimpleEntry. "a" "b")

;; a small interop task. of finding the days between today and first January
;; following is a sample java code that you should translate to clojure interop code.
;;   LocalDate dateOne = LocalDate.of(2020,1,1);
;;   LocalDate dateTwo = LocalDate.now();
;;   long daysBetween = ChronoUnits.DAYS.between(dateOne, dateTwo);
(defn days-between [d1 d2]
  (.between java.time.temporal.ChronoUnit/DAYS d1 d2))