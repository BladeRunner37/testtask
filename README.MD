Spring boot console application
Stack: Spring boot/data-jpa/test, Lombok, Jackson, Liquibase, Junit, Mockito, Slf4j/logback, gradle


- run resources/initDB.sql in your postgres console to create application database, WARNING: if you have database named 'testdb' it will be dropped
- define your database url and credentials in resources/application.yml
- use gradle clean build to build application
- run application with 2 arguments
- 1st argument: method name, 'serialize' - to write database table content to JSON file, 'deserialize' - to write content of JSON file to database
- 2nd argument: path to JSON file to serialize/deserialize
- schema and table will be created automatically by liquibase when application starts
- JSON example:

[ {
  "depCode" : "code99",
  "depJob" : "job5",
  "description" : "new5656"
}, {
  "depCode" : "code5",
  "depJob" : "job5",
  "description" : "new2"
}, {
  "depCode" : "code7",
  "depJob" : "job7",
  "description" : "new29999999999"
} ]