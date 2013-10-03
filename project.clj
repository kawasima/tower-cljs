(defproject net.unit8/tower-cljs "0.1.0"
  :description "FIXME: write this!"
  :url "http://github.com/kawasima/tower-cljs"
  :dependencies [ [org.clojure/clojure "1.5.1"]
                  [org.clojure/clojurescript "0.0-1896"
                    :exclusions [org.apache.ant/ant]]
                  [com.cemerick/clojurescript.test "0.0.4"]]
  :plugins [ [lein-cljsbuild "0.3.3"] ]
  :cljsbuild
  { :builds
    {
      :dev
      { :source-paths ["src"]
        :jar true
        :compiler
        { :output-to "resources/js/main.js"
          :optimizations :whitespace
          :pretty-print true }}
      :prod
      { :source-paths ["src"]
        :compiler
        { :output-to "resources/js/main.min.js"
          :optimizations :advanced
          :externs [ "externs/jquery.js" ]
          :pretty-print true }}
      :test
      { :source-paths ["src" "test"]
        :compiler
        { :output-to "target/cljs/testable.js"
          :optimizations :simple
          :pretty-print true }}}
    :test-commands
    { "unit" [ "runners/phantomjs.js" "target/cljs/testable.js"]}})
