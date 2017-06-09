(load-plugins) ; will load plugins from the classpath

(rabbitmq-plugin/amqp-consumer {
                       :parser-fn rabbitmq-plugin/logstash-parser ; message parsing function, the sample function here is the default
                       :prefetch-count 100 ; this is the default
                       :bindings [{
                         :opts {:durable true :auto-delete false} ; this is the default
                         :queue "queue.default" ; the default is "" which means auto-generated queue name
                         :bind-to {"ex", ["queue.default"]} ; also works with single non-seq binding key
                         :tags ["amqp"] ; will be added to event tags
                       }]
                       :connection-opts {:host "10.20.30.40" :port 5672 :username "guest" :passowrd "guest"} ; default is {}
                       })
