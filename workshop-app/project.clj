(defproject inclojure/workshop-app "0.1.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring "1.8.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.9.0"]
                 [org.xerial/sqlite-jdbc "3.30.1"]]
  :aot [workshop-app.clj-to-java-interop]
  :main workshop-app.core
  :global-vars {*warn-on-reflection* true}
  :profiles {:test {:dependencies [[org.clojure/test.check "0.10.0"]]}}
  :local-repo ".local-m2")