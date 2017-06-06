# Riemann test project 

## Invoke Riemann
1. Invoke
    ```
    docker-compose up -d riemann
    ```
2. Understand what happening
   ```
    docker logs riemann
   ```

## Setup input testing strems

*   method 1. Build testing streams source client
    ```
    cd streams_src
    go build
    ./streams_src
    ```
*   method 2. Use open source solution 
    ```
    gem install riemann-tools
    riemann-health --host my.riemann.server
    ```


## Invoke Alerta
   In testing environment, we assume the host machine that running docker is using IP `10.20.30.40`.

   ```
   docker-compose up -d alerta-web
   ```
   Use web browser to see http://10.20.30.40:8081
