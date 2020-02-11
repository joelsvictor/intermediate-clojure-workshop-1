(ns workshop-app.state-&-identity)

(def a 10)

['a #'a a]

(alter-var-root #'a (constantly 20))

;; you don't call my mom mom
(def b a)

['b #'a b]

(= a b)

(= #'a #'b)

(= (String.) (String.))

(identical? (String.) (String.))

(let [a (String.)]
  (identical? a a))