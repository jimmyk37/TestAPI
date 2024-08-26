# Ecom API Testing Project

## Overview

This project is a comprehensive API testing framework for the Ecom API. It uses Java with TestNG, RestAssured for API testing, Log4j for logging, and ExtentReports for generating detailed test reports. The framework is designed with a modular structure, including Base classes, Request Handler classes, and custom logging for failed test cases.

## Project Structure

```plaintext
.
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── base
│   │   │   │   └── BaseTest.java
│   │   │   │   └── RequestHandler.java
│   │   │   │   └── ApiLoggingUtil.java
│   │   └── resources
│   │       └── log4j.properties
│   └── test
│       └── java
│           ├── ├── product
│           │   └── BrandListTest.java
│           │   └── ProductListTest.java
│           │   └── SearchProductTest.java
│           ├── ├── user
│           │   └── CreateAccountTest.java
│           │   └── DeleteAccountTest.java
│           │   └── GetUserDetailByEMailTest.java
│           │   └── VerifyLoginTest.java
├── pom.xml
├── All.xml
├── Product.xml
├── User.xml
└── README.md

**Key Components**
BaseTest.java: The base class for all test classes. It sets up the test environment, initializes ExtentReports, and configures logging with Log4j.
RequestHandler.java: A utility class for handling API requests (GET, POST, PUT, DELETE). It also integrates SoftAssert for assertions.
ApiLogger.java: Custom logging class using Log4j for capturing detailed logs, especially for failed test cases.


**Prerequisites**
Java 8 or higher
Maven
TestNG
RestAssured
Log4j
ExtentReports

**Logging**
Log4j is used for logging test execution details. Logs are stored in logs/api-test.log.
ApiLoggingUtil.java captures detailed information for failed test cases, including request and response details.
**Reporting**
ExtentReports is used to generate rich HTML reports.
Reports are stored in the test-output/ExtentReport/ directory.
**Customizing the Framework**
**Adding New Tests**
Create a new test class under src/test/java/testcases/.
Extend the BaseTest class.
Use the RequestHandler class to send API requests and perform assertions.
**Modifying Logging**
Update the ApiLogger class for custom logging requirements.
Modify log4j.properties for different logging levels or output formats.
**Custom Assertions**
The RequestHandler class includes a SoftAssert for custom assertion handling. This can be modified as needed to suit your test cases.
