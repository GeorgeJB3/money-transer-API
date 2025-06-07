# Money Transfer API

## API Description

This is a money transfer API developed in Java and uses a PostgreSQL databse to store accounts.
There is an Account class with fields accountID, name and balance.  This is used to create instances for each account holder.
Inside Transfer controller there is a method to show all accounts in the database, add and delete accounts 
and then one to transfer money between accounts.

Inside the test folder there are tests for all controller methods. Making sure they work as intended.
the application is run on local port 8080 and uses maven dependencies.




### **How to run the application**

Clone the repository:
git clone git@github.com:GeorgeJB3/money-transer-API.git

Open the project in your preferred IDE. I would recommend IntelliJ.

Ensure you have PostgreSQL installed then create a database:

createdb moneytransferdb

_AccountRepository has all CRUD operations and will create the table for you._

RUN: mvn clean package
_This builds the jar file_

RUN: bash run_api.sh
_This triggers the application_

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

Running the app via a bash script instead of Docker container - 
I focused on running through a bash script to keep things as simple as possible. This is the first API I have 
built using Java and haven't got much experience creating docker containers. This is a great opportunity to 
get the experience with docker and something I will be adding in next.

#### What I didn't focus on

Authentication - 
As this is a basic API being ran locally I didn't need to add authentication. When I further expand the application
I will need to consider the security of the app.

Docker - 
Implementing Docker is currently a work in progress. I faced many issues, some due to the controller and test controller using
the same properties. This is something I am working on to containerise the application.

Stricter error handling - 
I have kept the error handling fairly minimal just to get the API running and keeping it basic.
This is something I can further enhance in the future, especially when adding new functionality.

