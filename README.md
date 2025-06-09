# Money Transfer API

## API Description

This is a money transfer API developed in Java uses a PostgreSQL database to store accounts and uses Docker.
There is an Account class with fields accountID, name and balance.  This is used to create instances for each account holder.
Inside Transfer controller there is a method to show all accounts in the database, add and delete accounts 
and then one to transfer money between accounts.

Inside the test folder there are tests for all controller methods. Making sure they work as intended.
the application is run on local port 8080 and uses maven dependencies.

### **How to run the application**

#### Pre requisites

Docker
Git
Java 17 JDK
Maven

Clone the repository:
`git clone git@github.com:GeorgeJB3/money-transer-API.git`

Open the project in your preferred IDE. I would recommend IntelliJ IDEA.

Run the following commands in your terminal:

Build project
`mvn clean package`

Build the docker image for the application
`docker build -t moneytransfer-image .`

Run PostgreSQL container (Ensure port 5432 is available)
`docker run -d --name postgres-docker -e POSTGRES_PASSWORD=password -e POSTGRES_DB=moneytransferdb_docker -p 5432:5432 postgres`

Run the money-tranfer application container
`docker run -d -t --name moneytransfer-app --link postgres-docker:postgresql -p 8080:8080 moneytransfer-image`

Run the bash script to execute the application
`bash run_api.sh`

Once finished stop the containers

PostgreSQL container
`docker stop postgres-docker`

Money-tranfer application container
`docker stop moneytransfer-app`

If you want to delete the contaoners, Run:
`docker rm moneytransfer-app postgres-docker`

When running the application again, providing you haven't removed the containers. 
Run the following to start the containers:
`docker start postgres-docker` & `docker start moneytransfer-app`

Otherwise, follow the above steps to run the containers.


#### Decisions I made

I made the decision to add all the logic into TransferController as it is a relatively small application
and I wanted to keep it all in one place.

The curl commands within the bashscript are hardcoded, so I could get the app working. A future improvement is 
to make the app more user orientated. Allowing the user to choose their own name and balance.

I decided to use PostgreSQL as it will be easy to expand on this project without altering or creating more databases.

For simplicity, I only added 3 fields to the Account class to get the API up and running to a basic level. A future step
would be to add different types of accounts stored in different tables.

#### what I focused on

Clean simple code - 
I wanted the code to be readable and efficient. So anyone can look at the methods and 
classes and be able to see what is happening. I added comments and doc strings to aid in readbility and to make it easier 
to bug fix along the way.

Basic RESTful endpoints (GET, POST, DELETE) - 
Using these 3 endpoints allowed me to meet the project requirements while allowing room for expansion in the future.

Testing the controller methods - 
I have done UT testing to make sure the controller methods work as intended in isolation.
I tested each methods in various cases such as transferring invalid amounts and ensuring there are not any duplicate accounts.


#### What I didn't focus on

Authentication - 
As this is a basic API being ran locally I didn't need to add authentication. When I further expand the application
I will need to consider the security of the app.

Stricter error handling - 
I have kept the error handling fairly minimal just to get the API running and keeping it basic.
This is something I can further enhance in the future, especially when adding new functionality.

