# Spring Boot JDBC + MySQL CRUD Caching Example

This is a simple Spring Boot application that demonstrates using Spring Boot with Spring JDBC and Spring Cache to perform CRUD operations on MySQL database and store them in memory cache.

## Requirements

1. Java 17
2. Maven 3
3. MySQL 8.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/sachinlakshitha/spring-boot-jdbc-mysql-crud-cache.git
```

**2. Change MySQL username and password as per your MySQL installation**

+ open `src/main/resources/application.properties` file.

+ change `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation

**3. Build and run the app using maven**

```bash
mvn package
java -jar target/spring-boot-jdbc-mysql-crud-cache-1.0.0-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.
