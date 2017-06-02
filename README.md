# Riemann test project 

1. invoke riemann docker
    ```
    docker-compose -f riemann.yml up -d riemann
    ```

2. build go client and test
    ```
    go build
    ./riemann
    ```
