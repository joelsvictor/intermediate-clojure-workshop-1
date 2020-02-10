(defproject save-points "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring "1.8.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.9.0"]]
  :source-paths ["01" ;; simple hello world printer
                 "02" ;; hello world server with one handler
                 "03" ;; hello world server with routes
                 "04" ;; hello name server
                 "05" ;; server with person crud filled
                 "06" ;; server with interop task done
                 "07" ;; server with sqlite update and delete filled.
                 "08"])




