(defproject workshop-app "0.1.0"
            :dependencies [[org.clojure/clojure "1.10.1"]
                           [ring/ring "1.8.0"]
                           [compojure "1.6.1"]
                           [cheshire "5.9.0"]
                           [org.xerial/sqlite-jdbc "3.30.1"]]
            :main workshop-app.core
            :global-vars {*warn-on-reflection* true}
            :source-paths ["1" ;; simple hello world printer
                           "2" ;; hello world server with routes
                           "3" ;; hello name server
                           "4" ;; server with person crud filled
                           "5" ;; server with interop task done
                           "6" ;; server with sqlite update and delete filled.
                           "final"]
            :profiles {:test {:dependencies [[org.clojure/test.check "0.10.0"]]}}
            :local-repo ".local-m2")




