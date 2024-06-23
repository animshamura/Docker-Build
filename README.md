# Dockerizing a Spring Boot App
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

# Dockerizing a Flask App

**Step 1: Create a flask app.** <br/><br/>
**Step 2: Create a file naming requirements.txt for installing all dependencies.** <br/><br/>
**Step 2: Create a Dockerfile.**
```
FROM python:3.8-slim-buster

WORKDIR /python-docker

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

COPY . .

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]
FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/spring-boot-docker.jar"]
```
**Step 4: Build an image.**
```
docker build -t flask-app .
```
**Step 5: Run the imaage in the container.** 
```
docker run -d -p 5000:5000 flask-app
```
**Step 6: Hit http://localhost:5000/ and http://localhost:5000/greet for the following views in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-greeting.png?raw=true">

# Dockerizing a Angular App
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/angular.png?raw=true">

# Dockerizing  PHPMyAdmin and MySQL
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/mysql-pma.png?raw=true">

# Dockering PG4Admin and PostgreSQL
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/postgresql-pg4.png?raw=true">

# Dockerizing Mongo Express and MongoDB
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/mongo-express.png?raw=true">
