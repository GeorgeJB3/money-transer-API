#!/bin/bash

echo "Hello! Welcome to Georges money transfer API"

#Leaving these variables in incase we need to run without docker
JAR_FILE=target/money-transfer-0.0.1-SNAPSHOT.jar
WAIT_TIME=10

PORT=8080

sleep 1

echo -e "\n"

echo "Starting the application..."

sleep 2

# Left these commands in incase we need to run without docker

#java -Xms256m -Xmx1024m -jar $JAR_FILE >/dev/null 2>&1 &

#
#echo -e "\n"
#
#echo "Please wait $WAIT_TIME seconds for the application to start"
#
#sleep $WAIT_TIME

echo -e "\n"

echo "Adding Account 1 - Gandalf..."

echo -e "\n"

curl --location "localhost:$PORT/account" \
--header "Content-Type: application/json" \
--data '{
    "accountID":1,
    "name":"Gandalf",
    "balance":5000.0
}'

echo -e "\n"

sleep 2

echo "Adding Account 2 - Sauron..."

echo -e "\n"

curl --location "localhost:$PORT/account" \
--header "Content-Type: application/json" \
--data '{
    "accountID":2,
    "name":"Sauron",
    "balance":7500.0
}'

echo -e "\n"

sleep 2

echo "Adding Account 3 - Elrond..."

echo -e "\n"

curl --location "localhost:$PORT/account" \
--header "Content-Type: application/json" \
--data '{
    "accountID":3,
    "name":"Elrond",
    "balance":7000.0
}'

echo -e "\n"

sleep 2

echo "Adding Account 4 - Treebeard..."

echo -e "\n"

curl --location "localhost:$PORT/account" \
--header "Content-Type: application/json" \
--data '{
    "accountID":4,
    "name":"Treebeard",
    "balance":10000.0
}'

echo -e "\n"

sleep 2

echo "These 4 accounts have now been added:"

echo -e "\n"

curl http://localhost:$PORT/all-accounts

echo -e "\n"

sleep 8

echo "Treebeard has requested for his account to be deleted"

echo -e "\n"

sleep 3

echo "Running delete command..."

echo -e "\n"

curl -X DELETE http://localhost:8080/accounts/4

echo -e "\n"

sleep 3

echo "Active accounts:"

echo -e "\n"

curl http://localhost:$PORT/all-accounts

echo -e "\n"

sleep 8

echo "Transfer 3000 from Gandalfs account to Saurons"

echo -e "\n"

curl -X POST http://localhost:$PORT/transfer \
-H "Content-Type: application/json" \
-d '{"fromAccountId": 1, "toAccountId": 2, "amount": 3000.0}'

echo -e "\n"

sleep 2

echo -e "Transfer complete. \n Accounts: "

echo -e "\n"

curl http://localhost:$PORT/all-accounts

echo -e "\n"

sleep 10

echo "Removing all accounts..."

echo -e "\n"
curl -X DELETE http://localhost:8080/accounts/1
echo -e "\n"
curl -X DELETE http://localhost:8080/accounts/2
echo -e "\n"
curl -X DELETE http://localhost:8080/accounts/3
echo -e "\n"

sleep 13

echo "All accounts deleted."

sleep 3

echo -e "\n"

echo "Goodbye."