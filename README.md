# ScenarioQualityChecker

[![Java CI with Maven](https://github.com/MichalRedm/ScenarioQualityChecker/actions/workflows/maven.yml/badge.svg)](https://github.com/MichalRedm/ScenarioQualityChecker/actions/workflows/maven.yml)
[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://MichalRedm.github.io/ScenarioQualityChecker/javadoc/)
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/23212826-bddb4048-399e-4562-81ee-76f6adc5e77e?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D23212826-bddb4048-399e-4562-81ee-76f6adc5e77e%26entityType%3Dcollection%26workspaceId%3D9cf44873-c2dc-491d-952b-e2bb3006bedf)

## Description of the project

For analysts documenting functional requirements with scenarios, our SQC application provides quantitative information and enables the detection of problems in functional requirements written in the form of scenarios. It is available as a remote API, which allows it to be integrated with existing tools.

Format of scenarios that will be processed by our application:
- The scenario includes a header specifying its title and actors (external and system)
- The scenario consists of steps (each step contains text)
- Steps can contain sub-scenarios (any level of nesting)
- The steps may start with the keywords IF, ELSE, FOR EACH

An example of such a scenario:

```
Title: Book addition
Actors: Librarian
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

## Project structure

![UML diagram](https://raw.githubusercontent.com/MichalRedm/ScenarioQualityChecker/main/src/main/resources/logic.png "UML diagram")

## Usage

The application should be run with Java 17; when it is running, the REST API endpoint will be located at port `8080`. The API accepts GET and POST requests. The body of every POST request should be JSON with field `scenario` containing JSON representation of a scenario. An example of a valid POST request body (the scenario here corresponds to an exemplary scenario provided earlier):
```json
{
    "scenario": {
        "title": "Book addition",
        "actors": [
            "Librarian"
        ],
        "systemActor": "System",
        "steps": [
            {
                "text": "Librarian selects options to add a new book item"
            },
            {
                "text": "A form is displayed."
            },
            {
                "text": "Librarian provides the details of the book."
            },
            {
                "type": "IF",
                "text": "Librarian wishes to add copies of the book",
                "substeps": [
                    {
                        "text": "Librarian chooses to define instances"
                    },
                    {
                        "text": "System presents defined instances"
                    },
                    {
                        "type": "FOR_EACH",
                        "text": " instance:",
                        "substeps": [
                            {
                                "text": "Librarian chooses to add an instance"
                            },
                            {
                                "text": "System prompts to enter the instance details"
                            },
                            {
                                "text": "Librarian enters the instance details and confirms them."
                            },
                            {
                                "text": "System informs about the correct addition of an instance and presents the updated list of instances."
                            }
                        ]
                    }
                ]
            },
            {
                "text": "Librarian confirms book addition."
            },
            {
                "text": "System informs about the correct addition of the book."
            }
        ]
    }
}
```
If everything goes well, server will respond with success message and save the scenario.

The GET request should also be a JSON, with field `actions` that should be the list of actions to be performed on the provided scenario by the application. The supported actions are:
- `countAllSteps` - provides the total number of steps in the scenario (at any level of nesting);
- `countConditionalDecisions` - counts the total number of all conditional decisions within a scenario;
- `getInvalidSteps` - provides a list of steps that do not start with an actor or system actor;
- `toText` - transforms the scenario to a textual format with step numbering;
- `simplify<depth>` - provides a simpler version of a scenario, reduced to a certain level of nesting.

An example o valid GET request body:
```json
{
    "actions": [
        "countAllSteps", 
        "countConditionalDecisions",
        "getInvalidSteps",
        "toText",
        "simplify1"
    ]
}
```

If the provided request is correct, the system should return a response in JSON format; it will have the form of a dictionary, in which keys will be the actions and values will be results produced by the application. Example response for the requests provided earlier:
```json
{
  "countConditionalDecisions": 2,
  "getInvalidSteps": [
    {
      "text": "A form is displayed."
    },
    {
      "type": "FOR_EACH",
      "text": " instance:",
      "substeps": [
        {
          "text": "Librarian chooses to add an instance"
        },
        {
          "text": "System prompts to enter the instance details"
        },
        {
          "text": "Librarian enters the instance details and confirms them."
        },
        {
          "text": "System informs about the correct addition of an instance and presents the updated list of instances."
        }
      ]
    }
  ],
  "simplify1": {
    "title": "Book addition",
    "actors": [
      "Librarian",
      "TestGuy"
    ],
    "systemActor": "System",
    "steps": [
      {
        "text": "Librarian selects options to add a new book item"
      },
      {
        "text": "A form is displayed."
      },
      {
        "text": "Librarian provides the details of the book."
      },
      {
        "type": "IF",
        "text": "Librarian wishes to add copies of the book",
        "substeps": []
      },
      {
        "text": "Librarian confirms book addition."
      },
      {
        "text": "System informs about the correct addition of the book."
      }
    ]
  },
  "countAllSteps": 13,
  "toText": "Title: Book addition\nActors: Librarian, TestGuy \nSystem actor: System\n\nSteps:\n1. Librarian selects options to add a new book item\n2. A form is displayed.\n3. Librarian provides the details of the book.\n4. IF: Librarian wishes to add copies of the book\n4.1. Librarian chooses to define instances\n4.2. System presents defined instances\n4.3. FOR_EACH:  instance:\n4.3.1. Librarian chooses to add an instance\n4.3.2. System prompts to enter the instance details\n4.3.3. Librarian enters the instance details and confirms them.\n4.3.4. System informs about the correct addition of an instance and presents the updated list of instances.\n5. Librarian confirms book addition.\n6. System informs about the correct addition of the book.\n"
}
```

If any request is invalid, the system will produce an error `500 Bad Request` and return an error message via the API.

## Additional information

[Here](https://docs.google.com/spreadsheets/d/11QnRBhp2aYy2u0Iwbr8FGHB5VYHG_Fb6) is an Excel spreadsheet containing the Sprint and Product backlog for this project.

<!--
## Sprint remind:

* We started the sprint on April 29th
* During the first week we started planning and created basic project structure on github
* During the second week we started coding rest API
, created github actions
* During the third week we added github issues, which were responsible for most functionalities and we completed all project backlog tasks

## Observations:

* We should have more equal distribution of work
* We should perform more work earlier
* We should have more live meetings

## Actions to be taken in the next sprint:

* Distribute work at the beginning
* Plan what should be done in each week at the beginning
* Meeting once a week
-->
