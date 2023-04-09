# Welcome to our coding test!

Your solution to this coding test will be evaluated based on its:
 * Adherence to best coding practices
 * Correctness
 * Efficiency

Take your time to fully understand the problem and formulate a plan before starting to code, and don't hesitate to ask any questions if you have doubts.

# Objective

Since we are a money transfer company this test will revolve around a (very) simplified transaction model. Our aim is to implement the methods listed in `com.smallworld.service.TransactionDataFetcher`, a component that allows us to get some insight into the transactions our system has.

A battery of test transactions is stored in `transactions.json` that is going to be used as a datasource for all our data mapping needs.

Each entry in `transactions.json` consists of:
 * mtn: unique identifier of the transaction
 * amount
 * senderFullName, senderAge: sender information
 * beneficiaryFullName, beneficiaryAge: beneficiary information
 * issueId, issueSolved, issueMessage: issue information. Transactions can:
   * Contain no issues: in this case, issueId = null.
   * Contain a list of issues: in this case, the transaction information will be repeated in different entries in `transactions.json` changing the issue related information.

Each method to be implemented includes a brief description of what's expected of it.

The parameters and return types of each method can be modified to fit the model that contains the transaction information

Have fun!

Solution:

1 - implemented all methods according to requirement.

2 - TransactionDAO contains code which get data from json and fill the objects. 
    getData()---> return all object (including duplicate transaction). 
    getUniqueTransaction() ---> return only unique transactions in list. 

3 - check Main method where all methods called. 

4 - impelmented TransactionDataFetcherTest test class where all unit test cases (positive and negative) defined for all methods , used ummy data which is available in Testutil class.
