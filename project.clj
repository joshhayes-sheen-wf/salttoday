(defproject salttoday "1.0.0"

  :description "Scrapes Sootoday and displays statistics about users and comments."
  :url "http://www.salttoday.ca"

  :min-lein-version "2.0.0"

  :main ^:skip-aot salttoday.core

  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :test-paths ["test/clj"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"

  :repositories [["jitpack.io" "https://jitpack.io"]]

  :dependencies [[clj-time "0.14.4"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [com.github.humboldtdev/logback-logdna-bulk "1.0"]
                 [com.google.guava/guava "21.0"]
                 [compojure "1.6.1"]
                 [cprop "0.1.11"]
                 [enlive "1.1.6"]
                 [funcool/struct "1.2.0"]
                 [http-kit "2.3.0"]
                 [cljs-http "0.1.46"]
                 [io.honeycomb.libhoney/libhoney-java "1.0.2"]
                 [luminus-immutant "0.2.4"]
                 [luminus-nrepl "0.1.4"]
                 [luminus/ring-ttl-session "0.3.2"]
                 [markdown-clj "1.0.2"]
                 [metosin/muuntaja "0.5.0"]
                 [metosin/ring-http-response "0.9.0"]
                 [mount "0.1.12"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238" :scope "provided"]
                 [org.clojure/tools.cli "0.3.7"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.webjars.bower/tether "1.4.3"]
                 [org.webjars/font-awesome "5.0.13"]
                 [org.webjars/webjars-locator "0.34"]
                 [overtone/at-at "1.2.0"]
                 [reagent "0.8.0" :exclusions [com.google.guava/guava]]
                 [ring-webjars "0.2.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [secretary "1.2.3"]
                 [selmer "1.11.7"]]

  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-immutant "2.1.0"]
            [lein-cljfmt "0.6.4"]]

  :repl-options {:timeout 120000}

  :clean-targets ^{:protect false}

  [:target-path [:cljsbuild :builds :app :compiler :output-dir] [:cljsbuild :builds :app :compiler :output-to]]

  :figwheel
  {:http-server-root "public"
   :nrepl-port 7002
   :css-dirs ["resources/public/css"]
   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :profiles
  {:uberjar {:omit-source true
             :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
             :cljsbuild
             {:builds
              {:min
               {:source-paths ["src/cljc" "src/cljs" "env/prod/cljs"]
                :compiler
                {:output-dir "target/cljsbuild/public/js"
                 :output-to "target/cljsbuild/public/js/app.js"
                 :source-map "target/cljsbuild/public/js/app.js.map"
                 :optimizations :advanced
                 :pretty-print false
                 :closure-warnings
                 {:externs-validation :off :non-standard-jsdoc :off}
                 :externs ["react/externs/react.js"]}}}}
             :aot :all
             :uberjar-name "salttoday.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev]

   :project/dev  {:dependencies [[binaryage/devtools "0.9.10"]
                                 [com.cemerick/piggieback "0.2.2"]
                                 [expound "0.6.0"]
                                 [figwheel-sidecar "0.5.16" :exclusions [com.google.guava/guava]]
                                 [pjstadig/humane-test-output "0.8.3"]
                                 [prone "1.6.0"]
                                 [ring/ring-devel "1.6.3"]
                                 [ring/ring-mock "0.3.2"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.19.0"]
                                 [lein-figwheel "0.5.16"]
                                 [org.clojure/clojurescript "1.10.238"]]
                  :cljsbuild
                  {:builds
                   {:app
                    {:source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
                     :figwheel {:on-jsload "salttoday.core/mount-components"}
                     :compiler
                     {:main "salttoday.app"
                      :asset-path "/js/out"
                      :output-to "target/cljsbuild/public/js/app.js"
                      :output-dir "target/cljsbuild/public/js/out"
                      :source-map true
                      :optimizations :none
                      :pretty-print true}}}}

                  :source-paths ["env/dev/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}})
