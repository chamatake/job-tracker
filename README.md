### job-tracker

## General
This project consists of the following things running in their own Docker containers:
    * MySQL database:                   container_name = jobtracker-db
    * Spring Boot Application:          container_name = jobtracker-app

## Useful URLs
(note: when running locally, baseUrl=localhost:8088)
    * API documentation:                http://{baseUrl}/swagger-ui/index.html
    * Spring actuator healthcheck:      http://{baseUrl}/actuator/health

## Prerequisites
    1. .env file must contain valid database credentials. **** DO NOT check .env into source control!!! **** 
    2. Docker Desktop (or equivalent Docker tool) installed
    3. MySQL installed locally for local dev work

## RUNNING THE PROJECT
if code changes have been made in the application or configs, run the following command manually in a terminal:
`./gradlew bootBuildImage --imageName=job-tracker:latest`

run `docker-compose up` in a terminal.
    *   database healthcheck settings (interval of checking, timeout duration, and number of retries) are 
        contained in compose.yaml. Feel free to override these settings to fit your local dev environment.
        My local machine is old and slow, hence the absurdly large values. A modern machine can probably
        function fine with interval=3s, timeout=5s, retries=20.


## TROUBLESHOOTING
    // TODO populate this section as needed
    *   it's a good idea to remove old docker volumes if you change basic database schema things (ex. in the initial
        script to create the databases). Do this by running `docker volume ls` to list all volumes. Then to remove
        a volume, run `docker volume rm {name-of-volume-no-curlies}`
