
# Token and Role Base Authentication Using Spring Boot 3 + JWT + Postgres


This repository serves as a comprehensive tutorial demonstrating the implementation of Tokens and Role management using 
Spring Boot 3, JWT, and Postgres. Unlike a simple example, this tutorial provides in-depth insights and hands-on 
guidance for a robust implementation.

![](img/jwt.png)

### 1 - Create a new project with spring initalizr
![](img/spring-initializr-1.png)
I left the Dependencies section blank as we will be adding the dependencies externally.
![](img/spring-initializr-2.png)

### 2 - Add Dependecies to pom file

We will add those three dependencies at the first time.

![](img/first-3-dependecies.png)
<br>
_spring-boot-starter-data-jpa_  -> it will help us create entities
<br>
_spring-boot-starter-web_       -> it will help us create endpoints
<br>
_postgresql_                    -> it will help us build connection between spring app and postgresql

### 3 - Rename application.properties file to .yml
![](img/application-properties.png)
![](img/application-yml.png)
<br>Add properties <br>
![](img/application-yml-1.png)


### 4 - With docker compose run a new postgres container 
Docker Compose is a tool for defining and running multi-container Docker applications. 
Here's a basic guide on using Docker Compose:

- Ensure that Docker Compose is installed on your system. You can download it from the official Docker Compose
GitHub repository: https://github.com/docker/compose/releases
- Create a docker-compose.yml file in your project directory.

![](img/docker-compose-1.png)

![](img/docker-compose-2.png)

- Open a terminal, navigate to the directory containing your docker-compose.yml file, and run:

![](img/docker-compose-command-detach-mode.png) 

### 5 - Connect to the db inside Intellij Idea
I'm using IntelliJ IDEA Ultimate Edition. If you are using the Community Edition, you may not have the Database 
connection feature inside IntelliJ IDEA.

![](img/db-inside-idea.png)
![](img/db-connection-idea.png)

### 6 - Writing Code (Packages abd Role)

 - Creating packages , package structuring<br>
![](img/creating-packages.png)
 - Creating entity - Role<br>
![](img/entity-role.png)
 - Creating interface RoleRepository<br>
![](img/role-repository.png)
 - Creating service RoleService<br>
![](img/role-service.png)
 - Creating controller RoleController<br>
![](img/role-controller.png)
Before launch first time the application, if we drill inside db on Postgres<br>
![](img/drill-postgres-before-launching.png)
<br>After launch the app<br>
![](img/drill-postgres-after-launching-app-1.png)

### 7 - Test With Postman

 - #### Create new role as Admin<br>
![](img/postman-post-admin-role.png)
![](img/postman-response-admin-role.png)
After sending Post request with Postman, we have a record inside role table.
![](img/sql-query-role-table.png)
<br><br><br>
 - #### Create new role as User
![](img/postman-post-user-role.png)
![](img/postman-response-user-role.png)
SQL Query
![](img/sql-query-role-table-1.png)

### 8 - Writing code (User Entity, Controller, Repo and Service)
In similar we have to create a new entity for the User that I will call AppUser.<br>
Let's use JPA annotations to establish MANY-to-MANY relationships between AppUser and Role entities.
![](img/entity-appuser.png)
And Repository, Service and Controller.
![](img/user-repository.png)
![](img/user-service.png)
<br><br>Now when we relaunch teh app , we have 3 tables in our db
![](img/drill-postgres-after-launching-app-2.png)
<br>When we send a query , now we have the previous records erased.
![](img/sql-query-role-table-2.png)
<br><br>Records are erased because every time when we relaunch the app , we will have a new creation, it's mentioned in 
application.yml file
![](img/application-yml-create.png)

### 9 - Init method for DB data
In UserService class we create a new public method
![](img/init-db-1.png)
![](img/role-repository-injection-in-user-service.png)

method initRolesAndUsers(){}

![](img/init-roles-and-users-method-1.png)

Call this method inside Controller, it's important to add PostConstruct annotation

![](img/calling-init-roles-and-users.png)

Now when we query tables , we have 
![](img/sql-query-role-table-3.png)
![](img/sql-query-user-table-1.png)

Pay attention that password is not encoded after yet , will do it later<br>
And user_role is empty because we have to change init method
![](img/sql-query-user_role-table-1.png)

<strong> Setting roles </strong>

![](img/init-roles-and-users-method-2.png)

Now user_role has records because we have setted roles in init method
![](img/sql-query-user_role-table-2.png)

### 10 - Creating Endoints forAdmin and forUser in UserController
![](img/user-controller-foradmin-foruser-endpoints.png)

![](img/forAdmin-endpoint-1.png)

![](img/forUser-endpoint-1.png)

### 11 - Adding JWT and Spring security dependencies in pom.xml
![](img/pom-xml-jwt-security.png)

Now we have default security working on the server side.
Whe we launch the app and try to hit endpoints , Spring security ask for login.

![](img/spring-security-default-login.png)
![](img/generated-password.png)

### 12 - Global CORS (Cross Origin Ressource Sharing) configuration
[CORS with Spring](https://www.baeldung.com/spring-cors)

We create a new package _configuration_ and new class inside 
![](img/cors-configuration.png)

### 13 - Creating JwtAthenticationEntryPoint class
In the context of Spring Security and JWT (JSON Web Token) authentication, the term "authentication entry point" 
refers to the component responsible for handling authentication failures and initiating the authentication process. 
When a user attempts to access a secured resource without proper authentication or with an invalid JWT token, the 
authentication entry point comes into play.

In a Spring Security configuration with JWT authentication, you typically configure an authentication entry point using 
the AuthenticationEntryPoint interface. The commence method of this interface is invoked when authentication fails, and 
it's responsible for initiating the authentication process or handling the failure appropriately.
![](img/jwt-authentication-entry-point.png)

### 14 - Creating JwtUtil class

[  Russian text](JwtUtil-rus.md)

The payload is also called Claims.<br>
![](img/jwt-example.png)

This class provides methods for generating, validating, and extracting information from JSON Web Tokens (JWTs) used for authentication in a Spring Boot application.We can found on the net this class also called _JwtTokenProvider_

We create new package _util_ and class _JwtUtil_ annotated with _component_ annotation 
![](img/jwt-util/util-jwt-util-4.png)
The SECRET_KEY will contain a secret word used to encrypt the payload.<br>
TOKEN_VALIDITY - 60 sec * 60 min * 5 hours * 1000 ms - Token validity period.<br>
In production, we need to securely store the secret key.


We generate token 

![](img/jwt-util/generate-token-1.png)

We extract the expiration date from the token.

![](img/jwt-util/get-expiration-date-from-token.png)


 Has the token expired or not?

![](img/jwt-util/is-token-expired-1.png)

Token validation check

![](img/jwt-util/is-valid-token.png)

We write the method getUserNameFromToken()

![](img/jwt-util/get-username-from-token-1.png)

We implement the method getClaimsFromToken(), for which we will need another auxiliary method getAllClaimsFromToken()

![](img/jwt-util/get-all-claims-from-token.png)

We call _getAllClaimsFromToken()_ inside _getClaimsFromToken()_

![](img/jwt-util/get-claims-from-token.png)

We return to the private method _private Date getExpirationDateFromToken(final String token)_

![](img/jwt-util/get-expiration-date-from-token.png)

![](img/jwt-util/get-expiration-date-from-token-2.png)

We return to the public method _public String getUsernameFromToken(final String token)_

![](img/jwt-util/get-username-from-token-2.png)

Let's test how the method works, make a call in the main method

![](img/jwt-util/main-test-get-username-from-token-1.png)

We are getting an error

![](img/jwt-util/main-test-get-username-from-token-log-trace-1.png)

We add a dependency

![](img/jwt-util/pom-xml.png)

We get another error, but now it's related to an invalid token

![](img/jwt-util/main-test-get-username-from-token-log-trace-2.png)

We generate a JWT token on the website, but still, we receive an error.<br>
The error is related to the fact that the generated token on the website cannot be read on the local server.

![](img/jwt-util/main-test-get-username-from-token-log-trace-3.png)

We will need the ObjectMapper from com.fasterxml.jackson .<br>
ObjectMapper - an instance of this class allows mapping a string to JSON or vice versa, reading a value from JSON based on a key.

![](img/jwt-util/get-username-from-token-3.png)

We  do a small refactoring for the method generateToken()

![](img/jwt-util/generate-token-2.png)

We run another test using the main method of Spring Boot. We generate a new token using our local server

![](img/jwt-util/main-generate-token-1.png)

![](img/jwt-util/main-generate-token-2.png)
eyJhbGciOiJIUzUxMiJ9.
eyJzdWIiOiJ7XG4gIFwidXNlck5hbWVcIjogXCJBbGV4XCIsXG4gIFwidXNlclBhc3N3b3JkXCI6IFwicGFzc3dvcmRcIlxufSIsImlhdCI6MTcwNjM3NTg1NywiZXhwIjoxNzA2MzkzODU3fQ.
YmtVcycOywxafymiL2raLcVxFn_mt6U1YgEyKm1sa4gQqeQDpd3WvcKj5tHvJBf-qbmyKek2GCJujxHVqp4dwA

We insert the generated token into the string and restart the application to test the method
`public String getUsernameFromToken(final String token)`

![](img/jwt-util/main-test-get-username-from-token-log-trace-4.png)

![](img/jwt-util/main-test-get-username-from-token-log-trace-5.png)



So, what information will we put in the payload to generate a JWT token? We will include user information, so we need to refactor the method _public String generateToken(final String payload)_ 

![](img/jwt-util/generate-token-3.png)
![](img/jwt-util/generate-token-4.png)


### 15 - Creating JwtService
In _service_ package we create a new class JwtService that will implement _UserDetailsService_ interface
![](img/jwt-service-class-1.png)

### 16 - Creating and implementing JwtRequestFilter

[  Russian text](JwtRequestFilter-rus.md)

JwtRequestFilter is a custom filter responsible for extracting the JWT token from the request and
authenticating the user.

In the configuration package, create a new class, JwtRequestFilter .<br>
Annotate it with _@Component_ and extend it like below.
![](img/jwt-request-filter/do-filter-internal-1.png)

If the Authorization header is not empty and starts with Bearer, then we proceed to obtain the token.

![](img/jwt-request-filter/do-filter-internal-2.png)

If the token is not valid, we also pass it along to the filterChain

Next is setting the user in the Spring Security Context (authorization via token)

![](img/jwt-request-filter/do-filter-internal-3.png)

Now that the filtering stage is complete, everything is good; we have authorized the user in the context and send them further to WebSecurityConfigurer that we will write in the next

### 17 - Creating WebSecurityConfigurer class

[  Russian text](WebSecurityConfigurer-rus.md)

In package _config_ we add a new config class WebSecurityConfigurer class

![](img/web-security-configurer/web-security-configurer.png)

We write the method securityFilterChain()

![](img/web-security-configurer/web-security-configurer-2.png)

In Spring Boot 2, we extended the class _WebSecurityConfigurerAdapter_ and overridden the method _configure()_

![](img/web-security-configurer/web-security-configurer-3.png)

We add another bean to the class

![](img/web-security-configurer/bean-authentication-manager.png)


 1 - `@Bean`: This annotation indicates that the method produces a bean to be managed by the Spring container. In this case, it's a bean of type AuthenticationManager.<br>
 2 - This method takes an `AuthenticationConfiguration` as a parameter and returns an `AuthenticationManager`. The `AuthenticationConfiguration` parameter is injected by the Spring framework.<br>
 3 - `authenticationConfiguration.getAuthenticationManager()`: The method uses the injected `AuthenticationConfiguration` to retrieve an `AuthenticationManager` instance. This method call is responsible for obtaining or configuring the `AuthenticationManager` that will be used in the application.

 In the context of Spring Security, the `AuthenticationManager` is a core interface responsible for authenticating an `Authentication` request. It delegates the authentication process to the configured `AuthenticationProvider(s)`. This bean configuration is crucial for handling authentication within a Spring Security-enabled application.

We run the application and get an error(This run was done after  JwtService was completely implemented , not t this stage )

![](img/web-security-configurer/web-security-configurer-4.png)

We create another bean `bCryptPasswordEncoder()`

![](img/web-security-configurer/bean-authentication-manager-2.png)


### 18 - Creating JwtRequest and JwtResponse entity

In the `entity` package, let's create two entities classes JwtRequest and JwtResponse

![](img/jwt-request.png)
![](img/jwt-response.png)

### 19 - JwtController and implementing JwtService
Let's create a new controller for authentication of user

![](img/JwtController.png)

Back to JwtService class to implement `public JwtResponse createJwtToken(JwtRequest jwtRequest)`

![](img/jwt-service-class-2.png)


### 20 - Password Encryption
Before we finish , we ha to encrypt the password because the password is not encrypted.
If we make a query to db we will have this

![](img/sql-query-user-table-2.png)

We have a bean which ise declared in `WebSecurityConfigurer` class
![](img/bcrypt-pass-encoder-1.png)

We will use it in `UserService` class and we will create a new method 
`public String getEncodedPassword(String password)`

![](img/bcrypt-pass-encoder-2.png)

We have to call this method from `adminAppUser.setUserPassword( here  )`
and `appUser.setUserPassword( here  )`

![](img/bcrypt-pass-encoder-3.png)
![](img/bcrypt-pass-encoder-4.png)

Now we have our password in encrypted format

![](img/sql-query-user-table-3.png)

### 21 - Postman testing JWT request and response

When we run the application we have this error
![](img/test-postman/error-cycle-beans.png)
To resolve this , after some search I add @Lazy annotation to JwtService constructor
![](img/test-postman/lazy-annotation-jwt-service.png)

Now the app is launched and let's test with postman

[![Postman-JWT](img/test-postman/test-postman-jwt.png)](https://www.youtube.com/watch?v=E6iHyX4jsVI "Postman-JWT")

Let's test our spring security , we have created before two end points `forUser` and `forAdmin`.
We will try to have access to those end points without authentication

![](img/test-postman/for-user-and-for-admin-end-point.png)

In the video we can see that spring security does not allow access to resources.

[![Postman-JWT-2 ](img/test-postman/test-postman-jwt-2.png)](https://www.youtube.com/watch?v=aKA8BJsWCc4 "Postman-JWT-2")

Let's trywith user authentication and see how we can have access to user  and admin resources.
<br>In This short video we see how we have access to forUser and forAdmin resources with user JWT authentication. <br>It's incorrect , the forAdmin resource must be unauthorized for the user.
[![Postman-JWT-3 ](img/test-postman/test-postman-jwt-3.png)](https://www.youtube.com/watch?v=k48Vv4WlEeo "Postman-JWT-3")

We have to add some corrections to the code, we want when we login with `user` , the `forAdmin`
endpoint will be inaccessible for the user and when we login with `admin` , the `forUser` endpoint also inaccessible for the `admin`

In `UserController` we add 2 annotations `@PreAuthorize("hasRole('Admin')")` and `@PreAuthorize("hasRole('User')")`

![](img/test-postman/preauthorize-annotations.png)

Let's test.
First we will login (send request) as user, and test access forUser and forAdmin resource
Second we will test (send request) as admin, and test access forUser and forAdmin resource

[![Postman-JWT-4 ](img/test-postman/test-postman-jwt-4.png)](https://youtu.be/XBtjNt72XuM "Postman-JWT-4")

### 21 - Finishing the project

While checking the application's functionality, I noticed errors. To address this, I will add SL4J for logging and 
proceed to identify the issues.
![](img/finishing-app/sl4j-dependency.png)
Let's add two loggers to Controller and to the Filter
![](img/finishing-app/sl4j-jwt-request-filter-1.png)
![](img/finishing-app/sl4j-jwt-controller-1.png)

[![Postman-JWT-5 ](img/finishing-app/sl4j-youtube-thumb.png)](https://youtu.be/ZIS6FRbFIbw "Postman-JWT-5")

As in the video we see, we have first log in the JwtRequestFilter and after int the JwtController.<br>
If we take the first pic in the tutorial , we can say that we are here
![](img/finishing-app/jwt-2.png)

Let's continue testing the app.
We will add logger to JwtUtil

![](img/finishing-app/sl4j-jwt-util-1.png)

Now after sending request at this level - watch next video

![](img/finishing-app/jwt-3.png)
We have this logs and errors

![](img/finishing-app/log-1.png)

![](img/finishing-app/log-2.png)

[![Postman-JWT-6 ](img/finishing-app/sl4j-youtube-thumb-2.png)](https://youtu.be/0fHY3dpVJuM "Postman-JWT-6")

Method `getUsernameFromToken()` is called two times and two times an error is catched.
Let's correct the error
We don't need ObjectMapper from com.fasterxml.jackson and we remove this part from method.
![](img/finishing-app/getusername-remove-objectmapper.png)

Only think we left back is removing the "Hard Coding" in `UserService`

![](img/finishing-app/user-service-1.png)


If we want to create a new user , we have to create a new Rest Endpoint.
We will only keep one admin because creating admin by user is not a great idea.

We have one Rest Endpoint in UserController `/registerNewUser`.
And if new user comes and register himself as admin is not a good idea.
So we are not going allow the user to register as admin.
We are going keep our one default record as admin and rest of records will be by default having the user roles;

![](img/finishing-app/user-controller-1.png)

Editing `registerNewUser()` method

![](img/finishing-app/register-new-user-method.png)

Let's Test the app. After running the app , we have one record in db.

![](img/finishing-app/db-records-1.png)

Let's try to register new user with Postman
![](img/finishing-app/error-postman.png)

There is an error on `UserController` 

![](img/finishing-app/registr-new-user-method-2.png)

Now all it;s ok 

![](img/finishing-app/postman-register-new-user.png)

Inside db we have the newly registered user

![](img/finishing-app/db-records-2.png)

One user can have more than one role, in this case you have change here.

![](img/finishing-app/has-any-role.png)

Thank you for reading.
















