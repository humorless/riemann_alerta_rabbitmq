    ; streams stay in let block of main riemann 
    (streams
        (where (not (state "expired"))
            prn
            (match :service #"disk"
                (with {:event "FsUtil" :group "OS"}
                    (splitp < (* metric 100)
                        95 (critical "Filesystem utilisation >95% " dedup-alert)
                        90 (major "Filesystem utilisation >90%" dedup-alert)
                        80 (warning "Filesystem utilisation >80%" dedup-alert)
                        (normal "Filesystem utilisation is OK" dedup-alert))))))
