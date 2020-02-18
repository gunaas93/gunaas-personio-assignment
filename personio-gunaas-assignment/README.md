# Personio Assignment - Gunaalan S - Changing Hierarchy Model

The project would give a help for company to build their employee-boses relational hierarchy model.

## Getting Started

Technology stack includes [Spring Boot](https://spring.io/projects/spring-boot) + Java + Maven

### Prerequisites
- [Java](https://www.oracle.com/technetworkk/java/javase/downloads/jdk8-downloads-2133151.html) >= 8
- [Mysql](https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/) 8.0


### Usage
1. Start Mysql server locally
2. Update your local application properties to as such:
```
##database##
spring.datasource.url=jdbc:mysql://localhost:3306/personio?useSSL=false&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root

##jpa##
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.jpa.properties.format_sql=true
spring.jpa.properties.dialect.storage_engine=innodb
spring.jpa.properties.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.enable_lazy_load_no_trans=true
spring.jpa.open-in-view=false

###server###
server.port=8082
server.servlet.context-path=/api/v1

###jwt###
jwt.expiration-time=18000000
jwt.secret-key=PBKDF2WithHmacqweFqasd2sghSHA256
```

3. Start application server from IntellIJ.

From now, we should able to access web api via `http://localhost:8082`

## Running the tests
Test cases cover mostly service layers and repository layers covering
* Core logic
* Functionality behaviour
* Scale  
```bash
# run all tests
$mvn clean install
```
## Coding style
Follow default Java code style on IntelliJ IDE

## Available APIs
```
POST /api/v1/hierarchy (create hierarchy)
GET  /api/v1/hierarchy (get hierarchy)
POST /api/v1/users/seed (seed test user for authentication)
POST /api/v1/auth (login)
```
## A deeper look

### Applied design pattern
- Service pattern
- Repository pattern
- SOLID principles
- DTO - Data transfer object
- MVC
- REST APIs

### Algorithm
- Step 1: Use adjacent matrix to represent relationship between employee
- Step 2: Find the root of the tree from the matrix
- Step 3: Build the relations tree from root to leaves

**For example:** the company has following employees relationship
```json
{
	"Pete": "Nick",
	"Barbara": "Nick",
	"Nick": "Sophie",
	"Sophie": "Jonas"
}
```
For each relationship `subordinate -> superior`, we find out its position in matrix, increase by 1 to express an employee is the boss of another one, hence increase 2 if someone is indirect boss of someone. We end up with following matrix

|         | Barbara | Jonas | Sophie | Nick | Pete |
|---------|---------|-------|--------|------|------|
| Barbara | 0       | 2     | 2      | 1    | 0    |
| Jonas   | 0       | 0     | 0      | 0    | 0    |
| Sophie  | 0       | 1     | 0      | 0    | 0    |
| Nick    | 0       | 2     | 1      | 0    | 0    |
| Pete    | 0       | 2     | 2      | 1    | 0    |

**0:** means two employees has no relationship

**1:** means one is boss of another one

**2:** means one is indirectly boss of another one

=> **Root** is the one who is direct boss or indirect boss of everyone

In case of we found > 1 root or zero root, the tree is conflict which lead to error and hence we throw the exception.