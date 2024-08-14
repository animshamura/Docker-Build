<div align="justify">
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/docker.png?raw=true"> <br/>
  
# Dockerizing Spring Boot App
**Step 1: Create a spring boot app from https://start.spring.io .**
<br/> <br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-initialize.png?raw=true">
<br/><br/>
**Step 2: Edit the main application file, include restcontoller and map some views.**
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-controller.png?raw=true">
<br/> <br/>
**Step 3: Transform the project directory into a local git repository and add it to the remote repository.**
<br/> <br/>
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
**Step 4: Create a Dockerfile.** <br/>
```
# Use OpenJDK as the base image for building
FROM openjdk:17 AS builder

# Set working directory
WORKDIR /app

# Copy Maven Wrapper
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Copy Maven project files
COPY pom.xml .

# Copy source code
COPY src ./src

# Build Spring Boot application
RUN ./mvnw package -DskipTests

# Use OpenJDK as the base image for running the application
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=builder /app/target/student-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]

```
**Step 5: Build an image.** <br/><br/>
Open a terminal in the directory where the Dockerfile is located and run the command below to build an image. 
```
docker build -t spring-app .
```
**Step 6: Run the image in the container.** <br/> <br/>
Run an instance of the image on port 8080 from the container to port 9090 on the host or external network.
```
docker run -d -p 9090:8080 spring-app
```
**Step 7: Hit http://localhost:9090/ and http://localhost:9090/greet for the following views in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/spring--greeting.png?raw=true">

# Dockerizing Flask App

**Step 1: Create a flask app.** <br/><br/>
Python3 must have to installed in the machine. After that, 'pip' has to be installed. A virtual environment has to be created and further creating a virtual environment for windows. The virtual environment has to be activated with flask being installed.  
```
pip install virtualenv, 
python3 -m venv venv
py -3 -m venv venv
. venv/bin/activate
venv\Scripts\activate
pip install flask
```
Create a app.py file and write below code to get view upon hit requests.<br/> <br/> 
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-index.png?raw=true"> 

**Step 2: Create a file naming 'requirements.txt' for installing all dependencies.** <br/> <br/> 
A project must have a lot of dependencies to run in an instance. For an easier dockerizing process, a text file naming requirement.txt has to be created including all the dependencies for the project.  

**Step 3: Transform the project directory into a local git repository and add it to the remote repository.** <br/> <br/> 
Initialize the project as a local git repository.
```
git init
```
Login to https://github.com and create a repository. Then, add remote repository and push files in the local repository to the remote repository. <br/>
```
git remote add origin https://github.com/animshamura/flask-app.git
git branch -M main
git push -u origin main
```
**Step 4: Create a Dockerfile.**
<br/>

```
# Stage 1: Build
FROM python:3.8-slim-buster AS build

# Set the working directory
WORKDIR /python-docker

# Copy only the requirements file to leverage Docker cache
COPY requirements.txt requirements.txt

# Install the dependencies in a virtual environment
RUN pip install --upgrade pip \
    && pip install -r requirements.txt \
    && mkdir /python-docker/app \
    && mv /python-docker/* /python-docker/app/ \
    && rm -rf /python-docker

# Stage 2: Production
FROM python:3.8-slim-buster

# Set the working directory
WORKDIR /python-docker

# Copy only the necessary files from the build stage
COPY --from=build /python-docker/app /python-docker

# Install only the necessary runtime dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Expose the port the app runs on
EXPOSE 5000

# Command to run the application
CMD [ "python3", "-m", "flask", "run", "--host=0.0.0.0"]

```
**Step 5: Build an image.** <br/> <br/> 
Open a terminal in the directory where the Dockerfile is located and run the command below to build an image. 
```
docker build -t flask-app .
```
**Step 6: Run the image in the container.**  <br/> <br/> 
Run an instance of the image on port 5000 from the container to port 5000 on the host or external network.
```
docker run -d -p 5000:5000 flask-app
```
**Step 7: Hit http://localhost:5000/ and http://localhost:5000/greet for the following views in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-running.png?raw=true">
<br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/flask-greeting.png?raw=true">
# Dockerizing Node App

**Step 1: Create a node app.** <br/> <br/> 
Initialize a project and install express. 
```
npm init -y
npm install express
```
Create a index.js file and write code to view it upon hit requests.  <br/> <br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/node-index.png?raw=true">
 <br/>

**Step 2: Transform the project directory into a local git repository and add it to the remote repository.** <br/> <br/>
Initialize the project as a local git repository.
```
git init
```
Login to https://github.com and create a repository. Then, add remote repository and push files in the local repository to the remote repository. <br/>
```
git remote add origin https://github.com/animshamura/node-app.git
git branch -M main
git push -u origin main
```
**Step 3: Create a Dockerfile.** <br/>

```
# Stage 1: Build
FROM node:14 AS build

# Set the working directory
WORKDIR /usr/src/app

# Copy package.json and package-lock.json (if available)
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application (if needed, e.g., for TypeScript or other build steps)
# RUN npm run build

# Stage 2: Production
FROM node:14-slim

# Set the working directory
WORKDIR /usr/src/app

# Copy only the necessary files from the build stage
COPY --from=build /usr/src/app/node_modules /usr/src/app/node_modules
COPY --from=build /usr/src/app/package*.json /usr/src/app/
COPY --from=build /usr/src/app/dist /usr/src/app/ # Include this line if you have a build step

# Expose the port the app runs on
EXPOSE 3000

# Command to run the application
CMD ["npm", "start"]

```

**Step 4: Build an image.**  <br/> <br/>
Open a terminal in the directory where the Dockerfile is located and run the command below to build an image. 
```
docker build -t node-app .
```
**Step 5: Run the image in the container.**  <br/> <br/>
Run an instance of the image on port 3000 from the container to port 3000 on the host or external network.
```
docker run -d -p 3000:3000 node-app
```
**Step 6: Hit http://localhost:3000/ for the following view in the browser.** <br/><br/>
<img src="https://github.com/animshamura/Dockerization/blob/main/app-screenshot/node-app.png?raw=true">

# Dockerizing Angular App
**Step 1: Create an angular app.** <br/><br/>
First install Angular CLI and then create a new angular application.
```
npm install - g @angular/cli
ng new angular-app
```
**Step 2: Transform the project directory into a local git repository and add it to the remote repository.** <br/> <br/>
Initialize the project as a local git repository.
```
git init
```
Login to https://github.com and create a repository. Then, add remote repository and push files in the local repository to the remote repository. <br/>
```
git remote add origin https://github.com/animshamura/angular-app.git
git branch -M main
git push -u origin main
```
**Step 3: Create a Dockerfile.** <br/> 
```
# Stage 1: Build the application
FROM node:alpine as build

# Set working directory for the build stage
WORKDIR /usr/src/app

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy application code
COPY . .

# Build the application
RUN npm run build

# Stage 2: Create the production image
FROM nginx:alpine

# Copy built files from the build stage
COPY --from=build /usr/src/app/dist/student-fontend /usr/share/nginx/html

# Copy nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port
EXPOSE 80

# Command to run NGINX in the foreground
CMD ["nginx", "-g", "daemon off;"]
```
**Step 4: Build an image.** <br/> <br/> 
Open a terminal in the directory where the Dockerfile is located and run the command below to build an image. 
```
docker build -t angular-app .
```
**Step 5: Run the image in the container.** <br/> <br/> 
Run an instance of the image on port 4200 from the container to port 4200 on the host or external network.
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
    image: postgres:latest 
    restart: always
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
      POSTGRES_DB: test_db
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data  

  pgadmin:
    container_name: pgadmin4_container
    image: dpage/pgadmin4:latest  
    restart: always
    environment:
      PGADMIN_DEFAULT_EMAIL: shamura@gmail.com
      PGADMIN_DEFAULT_PASSWORD: root
    ports:
      - "5050:80"
    depends_on:
      - db  

volumes:
  pgdata:  
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
