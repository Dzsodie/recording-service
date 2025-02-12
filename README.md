
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Java 17](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7+-6DB33F?style=for-the-badge&logo=springboot)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Repository-6DB33F?style=for-the-badge&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Boot Validation](https://img.shields.io/badge/Spring%20Boot-Validation-6DB33F?style=for-the-badge&logo=spring)
![Mockito](https://img.shields.io/badge/Mockito-Testing-green?style=for-the-badge&logo=java)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5)
![Logging](https://img.shields.io/badge/Logging-SLF4J%20%2F%20Logback-blue?style=for-the-badge&logo=java)

## Recording Service
## Introduction
This project is a backend service in Java with a websocket to fetch and serve recording data from a mock database.
## Purpose
This project is a demo for the Zetoinc interview process, it's main purpose is to showcase the ability to develop a fullstack application.
## Features
- 
## Project structure
    ```shell
    recording-service/
    ├── src/main/java/com/zetoinc/recording/
    │   ├── config/
    │   ├── controller/
    │   ├── model/
    │   ├── repository/
    │   ├── service/
    │   ├── websocket/
    ├── src/test/java/com/zetoinc/recording/

    ```
## Installation
1. Pre-requisites: Download IntelliJ, Postman, Java17, MySQL workbench.
2. Install and start mysql server.
    - For MacOS
   ```shell
       brew install mysql
       brew services start mysql
   ```  
    - For Windows
      Run the downloaded MySQL Installer for Windows from the [MySQL Download Page](https://dev.mysql.com/downloads/installer/)
      In command line use the following command after successful installation and configuration.
   ```shell
     net start mysql
    ```
3. Clone the repository to your local.
    ```shell
    git clone  https://github.com/Dzsodie/recording-service.git
    ```
## Starting the application
Start the service with the following command from the root folder of the cloned application.
   ```shell
   mvn spring-boot:run
   ```
## Testing the Websocket
1. Install wscat.
   ```shell
   npm install -g wscat
   ```
2. Use this wscat command to connect to websocket.
   ```shell
   wscat -c ws://localhost:8080/ws/recordings
   ```
## Logging and monitoring
1. SLF4J is used for logging.
2. Application logs for debugging can be found at `logs/recording-service.log`.
3. Log aggregation is not yet implemented, but Datadog offers an easy-to-implement solution with user-friendly monitoring interface.
## Testing
1. Mockito and JUnit5 is used for the unit testing.
2. Test coverage needs to be improved. Coverage report can be reached with this command.
    ```shell
    mvn jacoco:report
    ```
3. Tests can be run with the following command.
    ```shell
    mvn test
    ```
## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.