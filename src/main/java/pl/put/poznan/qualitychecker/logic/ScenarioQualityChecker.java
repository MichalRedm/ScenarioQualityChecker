package pl.put.poznan.qualitychecker.logic;

import java.util.List;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class ScenarioQualityChecker {

    private Scenario scenario;

    public ScenarioQualityChecker(String[] args) {
        // TODO
    }

    /**
     * @return The total number of steps within a scenario,
     * including steps that are in all nested scenarios.
     */
    public Integer countAllSteps() {
        return scenario.getAllSteps().size();
    }

    public Integer countConditionalDecisions() {
        return 0; // TODO
    }

    public List<ScenarioStepComponent> getInvalidSteps() {
        return null; // TODO
    }

    public String toText() {
        return null; // TODO
    }

    public String toText(Integer maxDepth) {
        return null; // TODO
    }
}
