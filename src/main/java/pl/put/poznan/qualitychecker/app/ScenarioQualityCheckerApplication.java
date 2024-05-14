package pl.put.poznan.qualitychecker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.qualitychecker.logic.Scenario;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.qualitychecker.rest"})
public class ScenarioQualityCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenarioQualityCheckerApplication.class, args);
        Scenario scenario = new Scenario("""
Title: Book addition
Actors: Librarian,TestGuy
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
* System informs about the correct addition of the book."""
                );
        System.out.println(scenario.toJSON());
    }
}
