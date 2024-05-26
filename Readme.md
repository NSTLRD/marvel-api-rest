# Marvel API RESTful Consumer

This project is a RESTful API consumer for the Marvel API, fetching data from the Marvel API and exposing it through its own API.

## Usage of the JAR File

To use the JAR file, follow these steps to install it in your local Maven repository:

1. Locate the `marvel-api-consumer-0.0.1-SNAPSHOT.jar` in the `target` directory of the `marvel-api-consumer` project.
2. Execute the following command in the root directory of the project to install the JAR file:

    ```shell
    mvn install:install-file -Dfile=C:\\Users\\yourUser\\Desktop\\work-space-springboot\\marvel-api-consumer\\target\\marvel-api-consumer-0.0.1-SNAPSHOT.jar -DgroupId=com.marvel -DartifactId=marvel-api-consumer -Dversion=1.0.0 -Dpackaging=jar
    ```

## Building the Project

Follow these steps to build the project:

1. Run `docker-compose up` in the root directory of the project to start a fake email server for testing the email service. Ensure the email server is running on port 1080 to test the user registration flow with email token verification:

    ```shell
    docker-compose up
    ```

2. Execute the command `mvn clean install` in the root directory of the project to build the project. The JAR file will be generated in the `target` directory.
3. Insert the following SQL query into your H2 database to set up initial roles:

    ```sql
    INSERT INTO Role (id, name, created, modified) VALUES (1, 'ROLE_USER', NOW(), NOW());
    ```

## H2 Database Console

Access the H2 console with the following credentials:

- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User Name**: `root`
- **Password**: `root`

## Maven Dependencies Explanation

Here is an explanation of the dependencies used in the `pom.xml` file:

- **Spring Boot Starter Parent (`spring-boot-starter-parent`)**: A starter for building Spring Boot applications, providing default configurations and dependency management.

- **Spring Boot Starter Actuator (`spring-boot-starter-actuator`)**: Adds production-ready features such as monitoring and metrics.

- **Spring Boot Starter Data JPA (`spring-boot-starter-data-jpa`)**: Simplifies database access with JPA (Java Persistence API).

- **Spring Boot Starter Security (`spring-boot-starter-security`)**: Provides security features including authentication and authorization.

- **Spring Boot Starter Web (`spring-boot-starter-web`)**: A starter for building web applications, including RESTful services using Spring MVC.

- **Springdoc OpenAPI (`springdoc-openapi-starter-webmvc-ui`)**: Simplifies the generation and maintenance of API documentation.

- **Spring Boot Starter Validation (`spring-boot-starter-validation`)**: Provides validation functionalities.

- **Spring Boot Starter Mail (`spring-boot-starter-mail`)**: Adds email support.

- **Spring Boot Starter Thymeleaf (`spring-boot-starter-thymeleaf`)**: A starter for building MVC web applications using Thymeleaf templating engine.

- **Java JWT (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)**: Libraries for creating and parsing JSON Web Tokens (JWTs).

- **Spring Boot DevTools (`spring-boot-devtools`)**: Provides additional development-time features.

- **H2 Database (`h2`)**: An in-memory database for testing purposes.

- **Project Lombok (`lombok`)**: Reduces boilerplate code through annotations.

- **Spring Boot Starter Test (`spring-boot-starter-test`)**: A starter for testing Spring Boot applications with libraries like JUnit and Mockito.

- **Spring Security Test (`spring-security-test`)**: Provides security testing utilities.


## Author

- **Starling Diaz**
   - [Website](http://starlingdiaz.com)
   - [GitHub](https://github.com/NSTLRD)
   - [Email](mailto:starlingdiaz70@gmail.com)

For more information, feel free to contact me through my website or email.