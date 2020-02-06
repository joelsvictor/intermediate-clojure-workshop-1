(ns icw.chapter02.interop-syntax
  (:refer-clojure :rename {get cc-get})
  (:import (java.util Calendar)))

;; class access
java.util.HashMap                                           ;; a class

(java.util.HashMap.)                                        ;; an object created from the class

(def hm (java.util.HashMap.))                               ;; bind the object to a var

(.put hm "a" 10)

(.put ^java.util.HashMap hm "b" 10)                         ;; the first gives us a warning of how it cannot be resolved.

;; Member access
;; method acess
(.get hm "a")

(.get hm "b")

(.toUpperCase "joel")

;; field access
(.-x (java.awt.Point. 10 20))

;; static variables or methods access
Calendar/ERA

Math/PI

(System/getProperties)

;; dot special form
(. hm get "a")

(. "joel" toUpperCase)

(. (. System (getProperties)) (get "os.name"))

;; double dot
(.. System getProperties (get "os.name"))

;; doto
(def doto-hm (doto (java.util.HashMap.)
   (.put "a" 10)
   (.put "b" 20)))

;; new
(def new-syntax-hm (new java.util.HashMap))

;; accessing inner classes
(java.util.AbstractMap$SimpleEntry. "a" "b")

;; a small interop task.