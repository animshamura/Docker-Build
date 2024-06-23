# Dockerized Spring Boot App
**Step 1: Create a spring boot app.** <br/><br/>
**Step 2: Edit pom.xml file and add finalname of the artifact.** 
```
<build>
    <plugins>
        <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
     </plugins>
        <finalName>spring-boot-docker</finalName>
</build>
```
**Step 3: Create a Dockerfile.**
```
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/spring-boot-docker.jar"]
```
**Step 4: Build an image.**
```
docker build -t spring-app .
```
**Step 5: Run the imaage in the container.** 
```
docker run -d -p 9090:8080 spring-app
```
**Step 6: Hit http://localhost:9090/ and http://localhost:9090/greet for the following views in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring--greeting.png?raw=true">

# Dockerized Flask App
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-greeting.png?raw=true">

# Dockerized Angular App
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/angular.png?raw=true">

# Dockerized PHPMyAdmin for MySQL Database
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/mysql-pma.png?raw=true">

# Dockerized PG4Admin for PostgreSQL Database
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/postgresql-pg4.png?raw=true">

# Dockerized Mongo Express for MongoDB
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/mongo-express.png?raw=true">
