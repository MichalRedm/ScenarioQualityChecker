package pl.put.poznan.qualitychecker.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioQualityCheckerTest {
    private final ScenarioQualityChecker scenarioQualityChecker = new ScenarioQualityChecker(new Scenario("""
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
            """));

    @Test
    void countAllSteps() {
        assertEquals(13, scenarioQualityChecker.countAllSteps());
    }

    @Test
    void countConditionalDecisions() {
        assertEquals(2, scenarioQualityChecker.countConditionalDecisions());
    }

    @Test
    void getInvalidSteps() {
        assertFalse(scenarioQualityChecker.getInvalidSteps().isEmpty());
        assertEquals("A form is displayed.", scenarioQualityChecker.getInvalidSteps().get(0).getText());
        assertEquals("instance:", scenarioQualityChecker.getInvalidSteps().get(1).getText());
        assertEquals(2, scenarioQualityChecker.getInvalidSteps().size());
    }

    @Test
    void toText() {
        assertEquals("Title: Book addition\nActors: Librarian \nSystem actor: System\n\nSteps:\n1. Librarian selects options to add a new book item\n2. A form is displayed.\n3. Librarian provides the details of the book.\n4. IF: Librarian wishes to add copies of the book\n4.1. Librarian chooses to define instances\n4.2. System presents defined instances\n4.3. FOR_EACH: instance:\n4.3.1. Librarian chooses to add an instance\n4.3.2. System prompts to enter the instance details\n4.3.3. Librarian enters the instance details and confirms them.\n4.3.4. System informs about the correct addition of an instance and presents the updated list of instances.\n5. Librarian confirms book addition.\n6. System informs about the correct addition of the book.\n",
                scenarioQualityChecker.toText());
    }

    @Test
    void simplify() {
        Scenario simplify0 = scenarioQualityChecker.simplify(0);
        Scenario simplify1 = scenarioQualityChecker.simplify(1);
        Scenario simplify2 = scenarioQualityChecker.simplify(2);
        assertEquals("Book addition", simplify0.getTitle());
        assertEquals("Book addition", simplify1.getTitle());
        assertTrue(simplify0.getSteps().isEmpty());
        assertFalse(simplify1.getSteps().isEmpty());
        assertEquals("Librarian chooses to define instances", ((ScenarioStepComposite) simplify2.getSteps().get(3)).getSubsteps().get(0).getText());
    }
}