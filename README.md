# JobInterview.EH_ATM

## Introduction
This project is composed for a job interview propose. This process is a standalone rest API which stimulate an ATM. The web API provide 2 functions: 
* Check available bank note in the ATM
* Withdraw some money from the ATM
Its associated simple front end can be found here 


## Technologies, libraries, processes and their reasons
* **Spring Boot** Provides web API for the ATM process stimulation
* **Maven** Provides dependency mangement and assist in CD/CI
* **Repository Design Pattern** Decouples the ATMService class from data access layer improving testability
* **Mockito** Provide more testability on Unit tests
* **HSQLDB** Provides a built-in, in-momory database to keep track of the available bank notes on the ATM 
* **Test Driven Development Approach** Has been adopted to develop the core logic for the class ATMService
* **Circle CI** Provide Continuous Integration helping to keep track of all the test. ( see [this link](https://circleci.com/gh/LameLoura/JobInterview.EH_ATM) )


## Usage

#### Prerequisite
These software must have been installed on your machine
 * Java version 8
 * Maven
 
#### Installation
Follow these steps to set up this webservice on your machine
 * Clone the source code into your local machine
 * Go to /AutoTellingMachineService
 * run command **mvn clean install**
 * run command **mvn spring-boot:run**
 
#### Service Consumption
After the service is up and running you can consume the webservice as following : 
 * use GET verb on http://localhost:8080/AutoTellingMachineAPI/atm/position/ to see number of available banks on the ATM
 * use POST verb on http://localhost:8080/AutoTellingMachineAPI/atm/withdraw/{amount} to withdraw money from the ATM
