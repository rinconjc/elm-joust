(defproject tiny-ws "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.immutant/web "2.1.9"]
                 [org.clojure/core.async "0.3.443"]
                 [compojure "1.6.0"]]
  :main ^:skip-aot tiny-ws.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
