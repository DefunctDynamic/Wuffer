(ns wuffer.nrepl
  (:require [clojure.tools.nrepl.server :as nrepl]
            [clojure.tools.logging :as logger]))

(def ^:private server (atom {}))

(defn start
  [{:keys [nrepl-host nrepl-port]}]
  (logger/info {:message "starting nrepl server"
                :host    nrepl-host
                :port    nrepl-port})
  (reset! server (nrepl/start-server :bind nrepl-host :port nrepl-port)))

(defn stop
  []
  (logger/info {:message "stopping nrepl server"})
  (nrepl/stop-server @server))
