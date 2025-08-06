I have implemented automated testing of the website of American Eagle Outfitters, Inc. 
Is a famous American clothing and accessories retailer.

---

## 📑 Contents

- [AE UI and API Automation Project](#ae-ui-and-api-automation-project)
- [Technologies used](#technologies-used)
- [Description of tests](#description-of-tests)
- [Limitations](#limitations)
- [How launch](#how-launch)
  - [Local](#local)
  - [Due GitHub Actions](#due-github-actions)
- [Allure reports](#allure-reports)
  - [Local](#local)  
  - [Due Git-hub](#due-git-hub)
- [Specificities](#specificities) 
- [Contacts](#contacts)

---
---

## AE UI and API Automation Project

An automated project for UI and API testing of the American Eagle online store (https://www.ae.com/us/en).

---
---

## Technologies used:

<div style="display: flex; flex-wrap: wrap; gap: 10px; align-items: center;"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="Java" /> <img src="https://seeklogo.com/images/G/gradle-logo-D9B7525BD6-seeklogo.com.png" height="40" alt="Gradle logo" /> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/selenium/selenium-original.svg" height="40" alt="Selenium" /> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/github/github-original.svg" height="40" alt="GitHub" /> <img src="https://img.shields.io/badge/JUnit5-red?style=for-the-badge&logo=JUnit5&logoColor=white" alt="JUnit5" height="28" /> <img src="https://img.shields.io/badge/Rest%20Assured-339933?style=for-the-badge&logo=rest-assured&logoColor=white" alt="Rest Assured" height="28" /> <img src="https://img.shields.io/badge/Allure-FF69B4?style=for-the-badge&logo=allure&logoColor=white" alt="Allure" height="28" /> </div>


| Category               | Technology     | Description                                                        |
|------------------------|----------------|--------------------------------------------------------------------|
| Programming Language   | Java 18        | The main language used to write tests                              |
| Build System           | Gradle 8.10    | Tool for project build and dependency management                   |
| Unit/Integration Tests | JUnit 5        | Framework for writing and running tests                            |
| UI Testing             | Selenium       | UI automation for web applications                                 |
| API Testing            | RestAssured    | Testing REST APIs                                                  |
| Reporting              | Allure Reports | Generates readable and visual test execution reports               |
| CI/CD                  | GitHub Actions | Automated test execution and deployment pipelines                  |
| Code Simplification    | Lombok, Owner  | Generates boilerplate code (getters, setters, constructors, etc.)  |
| Design Pattern         | Builder        | Pattern for constructing complex objects step by step              |
| Design Pattern         | PageFactory    | Pattern for initializing page elements in UI testing               |


---
---

## Description of tests:

The tests are divided into API and UI testing parts. 

---
---

## Limitations:
There are limitations for both parts.

First of all, impossible to test actions related to the registration of a new user or actions related to a registered user 
due to the fact that the tested application uses Akamai bot which protects the site from automatic actions on it.

Seconds, I didn't know real endpoints for api part. I used CURLs data from browser.

---
---

## How launch:

### Local:

1. Use JDK 18 и Gradle 8.10.
2. Check or fill data for guest.properties and auth.properties. There are needed :

guest.properties:
  mode=GUEST
  baseUrl=https://www.ae.com
  guest.header.authorization=Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==

 auth.properties (not using due Akamai bot) has data, which to fill:

  mode=AUTH
  auth.username=
  auth.password=
  baseUrl=https://www.ae.com
  guest.header.authorization=Basic MjBlNDI2OTAtODkzYS00ODAzLTg5ZTctODliZmI0ZWJmMmZlOjVmNDk5NDVhLTdjMTUtNDczNi05NDgxLWU4OGVkYjQwMGNkNg==

```bash
# UI tests
./gradlew uiTest -DitemsCount=3

# API tests
./gradlew apiTest -Dmode=GUEST
```

### Due GitHub Actions:

"AE Project" contains GitHub Workflow (`.github/workflows/gradle.yml`):

- launch API and UI tests;
- publishes the Allure report in `gh-pages` brunch;
- manage repository secrets in project;
- ui tests uses GUI mode of browser options with fool window size.

Run workflow:

Go to https://github.com/DevAutoTest/AE_Project
And click "Action" and choose "AE project(API and UI)"
![img_2.png](images/img_2.png)

Click "Run workflow" to launch deploy.

---
---

## Allure reports:

### Local:

Execute code in terminal:

```bash

cd ./build
allure serve

```

### Due Git-hub:

All UI+API test results are displayed in Allure Report.
Go to https://github.com/DevAutoTest/AE_Project

On the "Code" page click github-pages in Deployments area:
![img_3.png](images/img_3.png)

Choose last deploy version:

![img_4.png](images/img_4.png)

---
---

## Specificities:

- Library org.aeonbits.owner is using to read from property files, system property and env property.
  By default, using property file, than system property.

- -DitemsCount - parameter need for UI part, default = 2 otherwise this is count of items (exmpl: jeans, tops, shorts etc.)
   witch in random mode will be added to bag. Usually not more than 6 items.

- -Dmode=GUEST - parameter need for API part witch may run withowt Akamai bot checking.

- Each ui test is isolated: the browser is restarted before each method (`@beforeEach').
- Each test is performed sequentially, parallel launch  is disabled.

---
---

## Contacts:

 author: Elena Danilova  
 e-mail: Elena_Danilova7788@outlook.com

---
