# AccountService

Implementation:
     Transactional Nature: 
       * The function handling the fund transfer is
       * Annotated with @Transactional annotation
       * so that debit from source, credit to destination account 
       * and the entry in transaction table are executed sequentially
       * and commited only when complete transaction is successful
     Concurrency: 
       * To handle concurrency added a version field with @Version annotation
       * so as to implement an OptimisticLocking on the accouts. If concurrent requests are 
       * there then only the first one would be successful, others would return 409 CONFLICT
     
Testing APIs
Have added the postman collection as well to the repo.

How to Run:
Clone the project and run the below command to run this application in a docker container

docker-compose up -d 

Application Details:
Maven: 3.8.5
SpringBoot: 2.6.6
Kotlin
OpenJDK11
