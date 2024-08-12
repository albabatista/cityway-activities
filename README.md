# cityway-activities

This is **the activities microservice**, which is part of **CityWay (Full Stack Project)**.

This microservice has a 3-tier architecture (business, integration and presentation) and use the following technologies:

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data Mongo DB
- Spring Cloud: Eureka Client
- Spring AOP (used for logging)
- Spring HATEOAS
- Mapstruct
- Lombok
- AWS S3 (to upload images)
- Swagger (to document the api): [](http://localhost:8080/swagger-ui/index.html)http://localhost:8080/swagger-ui/index.html

As well as, it has been tested by using JUnit and Mockito with the aid of Jacoco Plugin to
visualize the coverage: ```mvn verify```

**CityWay Architecture Diagram**:
![CityWay Architecture](https://github.com/user-attachments/assets/e36394d7-eee4-4cd8-a6b1-5b6c7dcca552)
