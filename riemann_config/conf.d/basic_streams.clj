    ; streams stay in let block of main riemann 
    (streams
        (where (not (state "expired"))
            prn
            (match :service "heartbeat"
                (fn [event]
                    (let [elapsed (- (now) (:metric event))
                        ttl (:ttl event)]
                        ((with {:event "Heartbeat" :group "Riemann" :metric elapsed}
                            (splitp < elapsed
                                90 (major "Heartbeat stale by more than 90 seconds" dedup-alert)
                                60 (minor "Heartbeat stale by more than 60 seconds" dedup-alert)
                                30 (warning "Heartbeat stale by more than 30 seconds" dedup-alert)
                                (normal "Hearbeat received within " ttl " seconds and not stale" dedup-alert))) event))))

            (match :service "load"
                (with {:event "SystemLoad" :group "OS"}
                    (splitp < metric
                        0.7 (major "15-min load average is very high" dedup-alert)
                        0.4 (warning "15-min load average is high" dedup-alert)
                        (normal "15-min load average is OK" dedup-alert))))

            (match :service "cpu"
                (with {:event "CpuUtil" :group "OS"}
                    (splitp < (* metric 100)
                        99 (critical "CPU utilisation >99%" dedup-alert)
                        95 (major "CPU utilisation >95%" dedup-alert)
                        90 (minor "CPU utilisation >90%" dedup-alert)
                        80 (warning "CPU utilisation >80%" dedup-alert)
                        (normal "CPU utilisation is OK" dedup-alert))))

            (match :service "memory"
                (with {:event "MemUtil" :group "OS"}
                    (splitp < (* metric 100)
                        90 (major "Memory utilisation >90%" dedup-alert)
                        75 (warning "Memory utilisation >75%" dedup-alert)
                        (normal "Memory utilisation is OK" dedup-alert))))

            (match :service #"inode"
                (with {:event "FsUtil" :group "OS"}
                    (splitp < (* metric 100)
                        95 (critical "Filesystem utilisation >95% " dedup-alert)
                        90 (major "Filesystem utilisation >90%" dedup-alert)
                        80 (warning "Filesystem utilisation >80%" dedup-alert)
                        (normal "Filesystem utilisation is OK" dedup-alert))))))
