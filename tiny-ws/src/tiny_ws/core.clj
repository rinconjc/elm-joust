(ns tiny-ws.core
  (:gen-class)
  (:require [clojure.core :refer [send]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources]]
            [immutant.web :as web]
            [immutant.web.async :as async]
            [immutant.web.middleware :refer [wrap-websocket]])
  (:import java.util.Date))

(def clients (agent []))

(defn register [clients new-channel]
  (if (>= (count clients) 2)
    (async/close new-channel)
    (conj clients new-channel)))

(defn unregister [clients ch]
  (remove #(identical? ch %) clients))

(defn the-other [ch]
  (if (identical? ch (first @clients))
    (second @clients)
    (first @clients)))

(def callbacks
  {:on-open (fn [ch]
              (send clients register ch)
              (println "joining new client!"))

   :on-close (fn [ch {:keys [code reason]}]
               (println "client left! reason:" code reason)
               (send-off clients unregister ch))

   :on-message (fn [ch msg]
                 (some-> (the-other ch) (async/send! msg)))})

;; (defn app [request]
;;   (async/as-channel request callbacks))

(defroutes app
  (GET "/ping" [] (str "replied at " (Date.)))
  (resources "/")
  ;; (ANY "/channel" [] (fn [req]
  ;;                      (println "request received!" req)
  ;;                      (async/as-channel req callbacks)))
  )

(defn -main
  [& args]
  (web/run (wrap-websocket app callbacks) :host "0.0.0.0"))
