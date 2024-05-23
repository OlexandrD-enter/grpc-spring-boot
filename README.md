# gRPC in Spring Boot Project


## This project demonstrates how to integrate gRPC into a Spring Boot application. It includes three modules:

- ### client-service: The client module that makes gRPC calls.
- ### grpc-service: The server module that handles gRPC requests.
- ### proto: The module containing the Protobuf schema.


## Table of Contents

- [Dependencies](#dependencies)
- [Quick Start](#start)
- [Example of usage](#example)

## Dependencies
- [Spring Boot Starter Parent]() - Version 3.2.5
  - Description: Parent project containing the default configuration for building Spring Boot applications, providing dependency management, plugin configurations, and other settings for simplifying the setup and management of Spring Boot projects.
- [grpc-netty-shaded]() - Version 1.41.0
  - Description: A shaded version of the gRPC Netty transport, providing the network layer for gRPC, including support for HTTP/2.
- [grpc-protobuf]() - Version 1.41.0
  - Description: Protobuf code generation support for gRPC, allowing the automatic generation of client and server classes from .proto files.
- [grpc-stub]() - Version 1.41.0
  - Description: Provides stub classes for gRPC, simplifying the implementation of gRPC clients and servers.
- [annotations-api]() - Version 6.0.53
  - Description: Annotations API from Apache Tomcat, necessary for Java 9+.
- [grpc-spring-boot-starter]() - Version 3.1.0.RELEASE
  - Description: Spring Boot starter for gRPC, simplifying the integration of gRPC servers and clients in Spring Boot applications.
- [proto]() - Version 0.0.1-SNAPSHOT
  - Description: Custom module containing the Protobuf schema for the project.
- [spring-boot-starter-test]()
  - Description: Starter for testing Spring Boot applications with libraries such as JUnit, Hamcrest, and Mockito.
- [grpc-testing]() - Version 1.64.0
  - Description: Provides utilities for testing gRPC clients and servers.
- [spring-boot-starter-web]()
  - Description: Starter for building web applications using Spring MVC.
- [grpc-client-spring-boot-starter]() - Version 3.1.0.RELEASE
  - Description: Spring Boot starter for gRPC clients, simplifying the setup and configuration of gRPC clients in Spring Boot applications.


## Quick Start
<a name="start"></a>
1. Clone this repository to your local machine:

    ```bash
    git clone link
    cd grpc
    ```  
2. Build the project
    ```bash         
    mvn clean install
    ``` 
3. Start the gRPC Server. Navigate to the grpc-service module and run:
    ```bash
    mvn spring-boot:run
    ```
4. Start the Client. Navigate to the client-service module and run: 
    ```bash     
    mvn spring-boot:run
    ```         
   
## Example Usage
<a name="example"></a>    
To test the gRPC communication using these endpoints, you can use tools like Postman or cURL to send HTTP requests to the client-service module. Here are some example cURL commands:
1. Create Category:
    ```bash
    curl -X POST http://localhost:8091/categories -H "Content-Type: application/json" -d '{"id": "5L", "name": "New Category"}'
    ```
2. Get Category:                                                                                                            
    ```bash                                                                                                                    
    curl -X GET http://localhost:8091/categories/1
    ```                                                                                                                        
                                                                                                                               
3. Update Category:                                                                                                            
    ```bash                                                                                                                    
    curl -X PUT http://localhost:8091/categories/1 -H "Content-Type: application/json" -d '{"id": "5L", "name": "New Updated Category"}' 
    ```                                                                                                                        
                                                                                                                               
4. Delete Category:                                                                                                            
    ```bash                                                                                                                    
    curl -X DELETE http://localhost:8091/categories/1
    ```                                                                                                                        

5. Get all:                                                                                                            
    ```bash                                                                                                                    
    curl -X GET http://localhost:8091/categories
    ```                                                                                                                        
                                                                                                                                                                                                                                                                 