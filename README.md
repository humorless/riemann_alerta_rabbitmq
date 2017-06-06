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


## Invoke Alerta
   In testing environment, we assume the host machine that running docker is using IP `10.20.30.40`.

   ```
   docker-compose up -d alerta-web
   ```
   Use web browser to see http://10.20.30.40:8081


## Setup input testing strems

*   Use riemann-tools to collect cpu, memory, disk, load and send metric to Riemann 
    ```
    gem install riemann-tools
    riemann-health --host my.riemann.server
    ```
