(ns wuffer.main
  (:use [overtone.live])
  (:require [clojure.tools.cli :as cli]
            [clojure.tools.logging :as logger]
            [shadertone.tone :as shader]
            [signal.handler :as signal]
            [wuffer.core :as wuffer]
            [wuffer.nrepl :as nrepl])
  (:gen-class))

(defn init!
  [{:keys [shader]
    :as   options}]
  (let [shader-file (format "opt/shaders/%s.glsl" shader)]
    (logger/info {:message "initing shadertone..."
                  :shader-file shader-file})
    (shader/start shader-file
                  :title "Wuffer"
                  :width 1680
                  :height 1050)
    (logger/info {:message "give it a second..."})
    (Thread/sleep 1000)
    (nrepl/start options)))

(defn kill!
  []
  (logger/info {:message "aaaand we're done. killing shadertone"})
  (shader/stop)
  (nrepl/stop)
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
                                            "Shader's Name"
                                            :default "sine_dance"]
                                           ["-nh"
                                            "--nrepl-host NREPL_HOST"
                                            "nREPL's Host"
                                            :default "0.0.0.0"]
                                           ["-np"
                                            "--nrepl-port NREPL_PORT"
                                            "nREPL's PORT"
                                            :default 1337
                                            :parse-fn #(Integer/parseInt %)
                                            :validate [#(< 0 % 0x10000)
                                                       "must be a number between 0 and 65536"]]])]
    (init! options)
    (muse!)
    (signal/with-handler :term (kill!))))
