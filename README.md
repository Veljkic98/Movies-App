## About The Project

The application is based on IMDB site. CRUD options for managing movies, Loging and messaging/event systems and creating report are implemented.

There are 5 microservices:
* Auth server - login and register - Maven
* Movie server - resources and CRUD operations - Maven
* Report server - creating reports in CSV, PDF and Excel format - Maven
* Audit server - Subscribe to Kafka and listen for events/messages - Gradle

## Built With

* Maven, Gradle
* Spring boot
* ELK stack
* Kafka
* Docker
* Kubernetes

## Getting started

### Prerequisites
* Java 11
* Docker
* Kubernetes (if you don't use Docker Desktop, which contains Kubernetes)

### Installation
1. Clone the repo
    ```
    git clone https://vcs.levi9.com/v.plecas/onboarding-app.git
    ```

2. Create Jar files

    set position on audit-server and type:
    ```
    gradle assemble
    ```
    set position on auth-server, movie-server and then report-server and for every service type:
    ```
    mvn clean install -DskipTests
    ```

3. Create Docker images

    * Position on audit-server directory and type:
    ```
    docker build -f Dockerfile -t audit-server .
    ```

    * Position on auth-server directory and type:
    ```
    docker build -f Dockerfile -t auth-server .
    ```

    * Position on movie-server directory and type:
    ```
    docker build -f Dockerfile -t movie-server .
    ```

    * Position on report-server directory and type:
    ```
    docker build -f Dockerfile -t report-server .
    ```

4. Expose to kubernetes

* Add secrets by typing:
    ```
    kubectl create secret generic mysql-auth-db --from-literal=db-name=auth --from-literal=db-pw=<YOUR DB PASSWORD> --from-literal=db-root-pw=<YOUR DB PASSWORD>

    kubectl create secret generic mysql-movie-db --from-literal=db-name=res_server --from-literal=db-pw=<YOUR DB PASSWORD> --from-literal=db-root-pw=<YOUR DB PASSWORD>

    kubectl create secret generic mysql-report-db --from-literal=db-name=report_server --from-literal=db-pw=<YOUR DB PASSWORD> --from-literal=db-root-pw=<YOUR DB PASSWORD>

    kubectl create secret generic mysql-audit-db --from-literal=db-name=audit_server --from-literal=db-pw=<YOUR DB PASSWORD> --from-literal=db-root-pw=<YOUR DB PASSWORD>
    ```
    
* Position on kubernetes-setup and type:
    ```
    kubectl apply -f audit

    kubectl apply -f auth

    kubectl apply -f movie

    kubectl apply -f report

    kubectl apply -f kafka

    kubectl apply -f k8s
    ````
5. Enable exposing endpoints on local through port-forwarding
    ```
    kubectl port-forward svc/auth-service 8081:8081
    
    kubectl port-forward svc/movie-service 8082:8082

    kubectl port-forward svc/report-service 8083:8083

    kubectl port-forward svc/audit-service 8084:8084
    ```
