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
**Step 1: Create a docker-compose.yaml file.**
```
version: '3.9'
services:
  mysql_db:
    image: mysql:8.3
    container_name: my-mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: test
      MYSQL_USER: anim
      MYSQL_PASSWORD: anim
    volumes:
      - mysql-data:/var/lib/mysql
      - ./init-db/init.sql:/docker-entrypoint-initdb.d/init.sql:ro
    ports:
      - "3307:3306"
    # Removed deprecated command
  phpmyadmin:
    image: phpmyadmin/phpmyadmin:latest
    container_name: my-phpmyadmin
    restart: always
    environment:
      PMA_HOST: mysql_db
      PMA_PORT: 3306
      MYSQL_ROOT_PASSWORD: anim
    ports:
      - "8080:80"
    depends_on:
      - mysql_db
volumes:
  mysql-data:
```
**Step 2: Turn up the docker-compose file .**
```
docker-compose up
```
**Step 3: Hit http://localhost:8080/ for accesing PHPMyAdmin dashboard.** <br/> <br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/mysql-pma.png?raw=true">

# Dockering PG4Admin and PostgreSQL
**Step 1: Create a docker-compose.yaml file.**
```
version: "3.8"
services:
  db:
    container_name: postgres_container
    image: postgres
    restart: always
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
      POSTGRES_DB: test_db
    ports:
      - "5432:5432"
  pgadmin:
    container_name: pgadmin4_container
    image: dpage/pgadmin4
    restart: always
    environment:
      PGADMIN_DEFAULT_EMAIL: shamura@gmail.com
      PGADMIN_DEFAULT_PASSWORD: root
    ports:
      - "5050:80"
```
**Step 2: Turn up the docker-compose file .**
```
docker-compose up
```
**Step 3: Hit http://localhost:5050/ for accesing PG4Admin dashboard.** <br/> <br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/postgresql-pg4.png?raw=true">

# Dockerizing Mongo Express and MongoDB
**Step 1: Create a docker-compose.yaml file.**
```
version: "3.9"
services:
  #mongo This is the actual database server that stores and manages the data.
  mongo:
    image: mongo:latest
    container_name: mongo_db_old
    environment:
      - MONGO_INITDB_DATABASE=testdb
      - MONGO_INITDB_ROOT_USERNAME=root
      - MONGO_INITDB_ROOT_PASSWORD=root
    networks:
      - mongo-network
    restart: unless-stopped
    ports:
      - "27017:27017"
    volumes:
      - ./my-data/db:/data/db
      - ./database/archive:/database/archive

  #mongo-express This is a web-based administrative user interface for MongoDB
  mongo-express:
    image: mongo-express
    container_name: mexpress_old
    environment:
      - ME_CONFIG_MONGODB_ADMINUSERNAME=shamura
      - ME_CONFIG_MONGODB_ADMINPASSWORD=shamura
      - ME_CONFIG_MONGODB_URL=mongodb://root:root@mongo:27017/?authSource=admin
      - ME_CONFIG_BASICAUTH_USERNAME=anim
      - ME_CONFIG_BASICAUTH_PASSWORD=anim
    networks:
      - mongo-network
    links:
      - mongo
    restart: unless-stopped
    ports:
      - "8081:8081"

networks:
  mongo-network:
    driver: bridge

```
**Step 2: Turn up the docker-compose file .**
```
docker-compose up
```
**Step 3: Hit http://localhost:8081/ for accesing Mongo Express dashboard.** <br/> <br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/mongo-express.png?raw=true">
