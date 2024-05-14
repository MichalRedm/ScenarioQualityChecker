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

    public Integer countAllSteps() {
        return 0; // TODO
    }

    /**
     * @return Number of all conditional decisions in the scenario.
     */
    public Integer countConditionalDecisions() {
        return scenario.getAllSteps()
                .stream()
                .filter(step -> step instanceof ScenarioStepComposite)
                .map(e -> 1)
                .reduce(0, Integer::sum);
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
