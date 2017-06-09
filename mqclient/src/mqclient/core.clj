(ns mqclient.core
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.exchange  :as lhe]
            [langohr.basic     :as lb]
            [clojure.data.json :as json]))

(defn message-handler
  [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  (println (format "[consumer] Received a message: %s, delivery tag: %d, content type: %s, type: %s"
                   (String. payload "UTF-8") delivery-tag content-type type)))

(def message
  {:endpoint "test-endpoint"
   :metric "test-metric"
   :timestamp 1488884708
   :step 60
   :value 10
   :counterType "GAUGE"
   :tags "idc=lg"})

(def payload
  (json/write-str message))

(defn -main
  [& args]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)
        qname "queue.default"
        routingkey "queue.default"
        exchange "ex"]
    (println (format "[main] Connected. Channel id: %d" (.getChannelNumber ch)))
    (lq/declare ch qname {:durable true :exclusive false :auto-delete false})
    ;
    ; when using default exchange, which name is "".
    ; we do not need to declare exchange or bind exchange with queue.
    ;
    (lhe/declare ch exchange "direct")
    (lq/bind ch qname exchange)
    (lb/publish ch exchange routingkey payload {:content-type "text/plain" :type "greetings.hi"})
    (Thread/sleep 2000)
    (println "[main] Disconnecting...")
    (rmq/close ch)
    (rmq/close conn)))
