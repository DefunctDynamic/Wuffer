(defproject wuffer "0.0.1"
  :description "this makes some noise; it really does"
  :url "http://github.com/woxelworks/wuffer"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :repl-options {:host "0.0.0.0"
                 :port 1337}
  :main ^:skip-aot wuffer.main
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [overtone "0.10.3"]
                 [shadertone "0.2.5"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.clojure/tools.cli "0.3.5"]])
