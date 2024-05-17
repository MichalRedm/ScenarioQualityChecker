# ScenarioQualityChecker

Repository for Software Engineering project at Poznań University of Technology.

[![Java CI with Maven](https://github.com/MichalRedm/ScenarioQualityChecker/actions/workflows/maven.yml/badge.svg)](https://github.com/MichalRedm/ScenarioQualityChecker/actions/workflows/maven.yml)

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/23212826-bddb4048-399e-4562-81ee-76f6adc5e77e?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D23212826-bddb4048-399e-4562-81ee-76f6adc5e77e%26entityType%3Dcollection%26workspaceId%3D9cf44873-c2dc-491d-952b-e2bb3006bedf)

## Description of the project

For analysts documenting functional requirements with scenarios, our SQC application will provide quantitative information and enable the detection of problems in functional requirements written in the form of scenarios. The application will be available via GUI and also as a remote API, thanks to which it can be integrated with existing tools.

Format of scenarios that will be processed by our application:
- The scenario includes a header specifying its title and actors (external and system)
- The scenario consists of steps (each step contains text)
- Steps can contain sub-scenarios (any level of nesting)
- The steps may start with the keywords IF, ELSE, FOR EACH

An example of such a scenario:

```
Title: Book addition
Actors:  Librarian
System actor: System

* Librarian selects options to add a new book item
* A form is displayed.
* Librarian provides the details of the book.
* IF: Librarian wishes to add copies of the book
  - Librarian chooses to define instances
  - System presents defined instances
  - FOR EACH: instance:
    * Librarian chooses to add an instance
    * System prompts to enter the instance details
    * Librarian enters the instance details and confirms them.
    * System informs about the correct addition of an instance and presents the updated list of instances.
* Librarian confirms book addition.
* System informs about the correct addition of the book.
```
