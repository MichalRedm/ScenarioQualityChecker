package pl.put.poznan.qualitychecker.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class ScenarioQualityChecker {

    private final Scenario scenario;

    public ScenarioQualityChecker(Scenario scenario) {
        this.scenario = scenario;
    }

    public Map<String, Object> executeActions(List<String> actions) {
        Map<String, Object> result = new HashMap<>();
        for (var action : actions) {
            switch (action) {
                case "countAllSteps" -> result.put("countAllSteps", countAllSteps());
                case "countConditionalDecisions" -> result.put("countConditionalDecisions", countConditionalDecisions());
                case "getInvalidSteps" -> result.put("getInvalidSteps", getInvalidSteps());
                case "toText" -> result.put("toText", toText());
                default -> throw new IllegalArgumentException("Invalid action: " + action);
            }
        }
        return result;
    }

    public Integer countAllSteps() {
        return 0; // TODO
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
