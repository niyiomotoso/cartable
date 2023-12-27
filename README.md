# Cartable System Stack
 Framework: Kotlin SpringBoot, Package manager: maven.

Database: MySQL.

Containerization: Docker.


# Installation
The application is dockerized and composed such that it can be started with a single command.
Ensure [Docker](https://www.docker.com/) is installed on the machine to successfully run. Start the application with the command below.
MySQL DB, Springboot and Vue.js application each has it's own container declaration.


`docker-compose up`

It takes about 3 minutes for all services to be fully setup depending on the local machine VM resources.

# Known issue
If the application is failing to start for some reason or there is an interruption in the starting process. Run the command below to restart the app installation process
`docker-compose down && docker-compose up`


# Running Tests
The test is run with a gradle command. To run the test `cd` to `parent` directory, then run:

`./mvnw test`


# Design Note
A well-detailed design note is available in Notes.pdf in the parent folder.



