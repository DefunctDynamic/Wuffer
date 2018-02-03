(ns wuffer.main
  (:use [overtone.live])
  (:require [clojure.tools.cli :as cli]
            [clojure.tools.logging :as logger]
            [shadertone.tone :as shader]
            [wuffer.core :as wuffer])
  (:gen-class))

(defn init!
  [{:keys [shader]}]
  (let [shader-file (format "opt/shaders/%s.glsl" shader)]
    (logger/info {:message "initing shadertone..."
                  :shader-file shader-file})
    (shader/start shader-file)
    (logger/info {:message "give it a second..."})
    (Thread/sleep 1000)))

(defn kill!
  []
  (logger/info {:message "aaaand we're done. killing shadertone"})
  (shader/stop)
  (System/exit 0))

(defn muse!
  []
  (logger/info {:message "museing up some noise"})

  (wuffer/wsine)
  (wuffer/wmixer)

  (logger/info {:message "gig is over; wait around..."})

  (Thread/sleep (* 10 1000)))

(defn -main
  [& arguments]
  (let [{:keys [options]} (cli/parse-opts arguments
                                          [["-s"
                                            "--shader SHADER"
                                            "visualization shader's name"
                                            :default "sine_dance"]])]
    (init! options)
    (muse!)
    (-> (Runtime/getRuntime)
        (.addShutdownHook (Thread. kill!)))))
