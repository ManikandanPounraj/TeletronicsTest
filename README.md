# TeletronicsTest

Here I have Used the MongoDB as a BackEnd DB Which I can Store the UserName and the Related Projects for the Particular UserName.

#Steps Before Launching the Application
 
1.Please have a local or Server MongoDb setup
 
2.Run the Below Scripts in the Terminal of MongoDb Client. So that the Data will be           getting inserted and it is available for Testing.

3.MongoDb Scripts:

---------Script Start-----------

use teletronics

db.createUser(
  {
    user: "test",
    pwd: "test123",
    roles: [ { role: "readWrite", db: "teletronics" }]
  }
)

db.projects.insert(
   { 
     "userName" : "Manikandan",
     "projectDetails" : [
	{
	"projectId": "123",
	"projectTitle": "Test1",
	"projectURL" : "http://test1.com",
	"projectDescription" : "Test1 Project",
	"totalCommits" : "5"
	},{
	"projectId": "456",
	"projectTitle": "Test2",
	"projectURL" : "http://test2.com",
	"projectDescription" : "Test2 Project",
	"totalCommits" : "10"
	}]
   }
)

---------Script End-----------

4.Please replace the hostname(localhost) of MongoDB in mongo URI in application-dev.yaml(For Example if your host name is 10.192.34.56 then replace host with your IP or if mongo is in local host no changes needed

mongodb:
      uri: mongodb://test:test123@localhost:27017/teletronics
      
5.Have Written Mockitto TestCases for the Controllers.

6.I have built the Swagger URL for this Microservice. 

7.After Successful Build Please Open the SwaggerURL like below(HostName : depends on localhost or IP Please Change it)

    http://localhost:8080/swagger-ui.html

Here You can test those 2 APIs 