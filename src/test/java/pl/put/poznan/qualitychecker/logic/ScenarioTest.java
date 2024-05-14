package pl.put.poznan.qualitychecker.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

    @Test
    public void testGetAllSteps() {
        var scenario = new Scenario("Title: Lorem ipsum\n" +
                "Actors: A, B\n" +
                "System actor: A\n" +
                "\n" +
                "* 1\n" +
                "* 2\n" +
                "* 3\n" +
                "* IF: 4\n" +
                "  - 5\n" +
                "  - 6\n" +
                "  - FOR EACH: 7\n" +
                "    * 8\n" +
                "    * 9\n" +
                "* 10");
        var steps = scenario.getAllSteps();
        for (int i = 0; i < steps.size(); i++) {
            assertEquals(String.valueOf(i + 1), steps.get(i).getText());
        }
    }
}