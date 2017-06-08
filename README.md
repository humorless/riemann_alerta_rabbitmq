# Riemann test project 

## just testing Rieman with Alerta

*   Disable riemann with RabbitMQ
    In the file `riemann_config/riemann.confg`
    comment the line `(include "/etc/riemann/conf.d")`

### Invoke Riemann

1. Invoke
    ```
    docker-compose up -d riemann
    ```
2. Understand what happening
   ```
    docker logs riemann
   ```


### Invoke Alerta

   In testing environment, we assume the host machine that running docker is using IP `10.20.30.40`.

   ```
   docker-compose up -d alerta-web
   ```
   Use web browser to see http://10.20.30.40:8081


### Setup input testing strems

*   Use riemann-tools to collect cpu, memory, disk, load and send metric to Riemann 
    ```
    gem install riemann-tools
    riemann-health --host my.riemann.server
    ```

## Testing Riemann with RabbitMQ

*   Enable riemann with RabbitMQ
    In the file `riemann_config/riemann.confg`
    uncomment the line `; (include "/etc/riemann/conf.d")`

### The rabbitMQ exchange needs to be setup by publisher

1.  Invoke mq first
   ```
    docker-compose up -d mq
   ```
2.  Invoke mq publisher
   ```
    cd mqclient
    lein run
   ```
3.  Invoke riemann
   ```
    docker-compose up -d riemann
   ```
