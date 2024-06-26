<div align="justified">
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/docker.png?raw=true"> <br/>
  
# Dockerizing Spring Boot App
**Step 1: Create a spring boot app from https://start.spring.io .** <br/> <br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-initialize.png?raw=true">
<br/><br/>
**Step 2: Edit the main application file, include restcontoller and map some views.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-controller.png?raw=true">
<br/> <br/>
**Step 3: Transform the project directory into a local git repository and add it to the remote repository.** <br/> <br/>
Initialize the project as a local git repository.
```
git init
```
Login to https://github.com and create a repository. Then, add remote repository and push files in the local repository to the remote repository. <br/>
```
git remote add origin https://github.com/animshamura/spring-boot-app.git
git branch -M main
git push -u origin main
```
**Step 3: Create a Dockerfile.** <br/>
As from the initialization, maven:3.8.3-openjdk-17 has been selected as the base image having requirements matched up. All files in the 'src' folder will be copied to the image's '/home/app/src' directory and so the pom.xml to the 
'/home/app' directory. The 'mvn clean package' command clears the target directory. It builds the project and packages in the resulting JAR file into the target directory without running unit tests during the build.
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
**Step 5: Run the image in the container.** 
```
docker run -d -p 9090:8080 spring-app
```
**Step 6: Hit http://localhost:9090/ and http://localhost:9090/greet for the following views in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring--greeting.png?raw=true">

# Dockerizing Flask App

**Step 1: Create a flask app.** <br/><br/>
**Step 2: Create a file naming requirements.txt for installing all dependencies.** <br/><br/>
**Step 3: Create a Dockerfile.**
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
**Step 5: Run the image in the container.** 
```
docker run -d -p 5000:5000 flask-app
```
**Step 6: Hit http://localhost:5000/ and http://localhost:5000/greet for the following views in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-greeting.png?raw=true">
# Dockerizing Node App

**Step 1: Create a node app.** <br/><br/>
**Step 2: Create a Dockerfile.**
```
FROM node:14

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install express

COPY . .

EXPOSE 3000

CMD ["npm", "start"]
```
**Step 3: Build an image.**
```
docker build -t node-app .
```
**Step 4: Run the image in the container.** 
```
docker run -d -p 3000:3000 node-app
```
**Step 5: Hit http://localhost:3000/ for the following view in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/node-app.png?raw=true">

# Dockerizing Angular App
**Step 1: Create an angular app.** <br/><br/>
**Step 2: Create a Dockerfile.**
```
FROM node:alpine

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install -g @angular/cli

RUN npm install

EXPOSE 4200

CMD ["ng", "serve"]
```
**Step 4: Build an image.**
```
docker build -t angular-app .
```
**Step 5: Run the image in the container.** 
```
docker run -d -p 4200:4200 angular-app
```
**Step 6: Hit http://localhost:4200/ for the following view in the browser.** <br/><br/>
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

# Dockering PGAdmin and PostgreSQL
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
**Step 3: Hit http://localhost:5050/ for accesing PGAdmin dashboard.** <br/> <br/>
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
<div/> 
