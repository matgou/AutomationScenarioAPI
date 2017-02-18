AutomationScenarios
===================

[![Build Status](https://travis-ci.org/matgou/AutomationScenarioAPI.svg?branch=master)](https://travis-ci.org/matgou/AutomationScenarioAPI)

## Description
Java API module to execute a test scenario described in XML command file

XML Example : src/test/resources/scenario/emptyScenario.xml

## Usage
To build just run :
```
mvn clean install package assembly:assembly -Dplatform.dependencies=true
```

And to run :
```
java -jar target/batch_run.jar -f emptyScenario.xml -n firefox 
```
