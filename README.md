# Employee-Management-System(WIP)

This is simple Employee Management System web application developed using **Java Servlets, JSP JDBC and My SQL**. 

This work was done as a part of capstone project for a Java EE training.

## Purpose
In the current system, **Admin** adds new **regulations/legistations(R/Ls)** and assigns them to the department manually.
Department Head sends these regulations to the individual users through mail to get their consent. **Users** send their **comments** through the courier service after reading regulations. Department head
has to collect the user inputs and pass them on to Admin. Since a lot of manual workflow is involved, it is time consuming for Admin to close each regulation.


So, a new system is required to automate this regulation creation and closure process.

## Functionalities

- Two types of access levels : **admin** and **user**(employees)
- **Login** and session tracking
- Admin can:
    - Add, view, update and delete user accounts
    - Add and view departments
    - create R/Ls for any department and **view status** of all R/Ls
- Users/Employees can:
    - View R/L assigned to their departments.
    - Create **status reports** for assigned R/Ls 
    - View and update comments on past status reports
- passwords are encrypted using *MD5* hashing.

## Dependencies
* JDK 1.8 or above
* MySQL server
* Maven 

## Build
1. Clone the project
    ```bash
    ~$ git clone https://github.com/ashunikam4/Employee-Management-System.git
    ```
2. In `src/main/resources/mysqlconn.properties`, set the url, username and password for MySQL connection (refer [JDBC docs](https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html#db_connection_url))
3. Run
    ```bash
    ~$ cd Employee-Management-System
    ~$ mvn tomcat7:run
    ```
4. Visit http://localhost:8080/webapp/

## Logging
By default, logs are sent to both console and log file (`Employee-Management-System/emslogging.log`). To change the logging location, update `src/main/resources/log4j.properties` file.

## View Samples 
To get an idea of how the webapp looks, see [here](view-samples)