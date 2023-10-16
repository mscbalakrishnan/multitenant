# multitenant
Sample code to connect multiple schemas based on tenant login

1. execute the sql - schema.sql ( available under resources folder) 
2. Change the DB setting based on your DB (tenant_1.properties, tenant_2.properties)
3. Run the code : mvn spring-boot:run
4. Call the API to login with tenant1
   http://localhost:8081/login - POST
   {
      "username" : "user",
      "password" : "user"
   }
5. Take the Authrization token from #4 API
6. call the API - http://localhost:8081/employee - POST
   Authorization Headers should be your token taken from #5
7. Check the employee table datas from tenant 1 schema.
8. Do the same from #4 to #6. Use the below JSON to authenticate tenant2 - 
   {
      "username" : "user",
      "password" : "user"
   }
9. Check the employee table datas from tenant 2 schema
