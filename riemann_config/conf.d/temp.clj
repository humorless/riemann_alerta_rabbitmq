; Expire old events from the index every 5 seconds.
(periodically-expire 5)

(let [index (index)]
  ; Inbound events will be passed to these streams:
  (streams
    (default :ttl 60
      ; Index all events immediately.
      index

      ; show all the events received.
      ; #(info "received event" %)

      ; Log expired events.
      ; (expired
      ;    (fn [event] (info "expired" event))))))

      ; select certain service
      (where (service "net.if.in.bits/iface=eth_all")
        ; split streams by host and alarm on per host condition
        (by :host
          (fixed-time-window 5
            (smap folds/median
              (where (< metric 80.0)
                #(info "alert event!" %)))))

        ; split steams by platform and alarm on aggregated metrics.
        (where (= (:platform event) "c01.i01")
          (fixed-time-window  5
            (smap folds/sum
              (where (> metric 100.0)
                #(info "alert platform event!!!" %)))))))))
