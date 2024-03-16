# Project Name

Short description or introduction of the project.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Configuration](#configuration)
- [Usage](#usage)
- [Testing](#testing)


## Introduction

Assignment test untuk PT Xsis Mitra Utama

## Features

RESTFull CRUD Movies reactive programming (webflux)
with request validation, unit test and error handling

## Technologies Used

- Java
- Spring Boot
- Spring WebFlux
- Spring Data R2DBC
- PostgreSQL

## Getting Started

### Prerequisites

jdk 17, maven, postgresql

### Installation
- use maven command to install required dependency
```
mvn clean install 
```
import database.sql to postgresql

### Configuration

change application.properties database to your own in src/main/resources
> - spring.r2dbc.url=r2dbc:postgresql://localhost:7432/xsis
> - spring.r2dbc.username=ridwan
> - spring.r2dbc.password=secret123

## Usage

- to run application :
```
mvn spring-boot:run
```
- import postman collection movies.postman_collection.json

## Testing
- testing is included before application running
- to run test only :
```
mvn test 
```
