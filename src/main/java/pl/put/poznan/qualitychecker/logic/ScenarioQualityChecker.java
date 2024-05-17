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
                case "countAllSteps" -> result.put(action, countAllSteps());
                case "countConditionalDecisions" -> result.put(action, countConditionalDecisions());
                case "getInvalidSteps" -> result.put(action, getInvalidSteps());
                case "toText" -> result.put(action, toText());
                default -> {
                    if (action.startsWith("simplify")) {
                        int depth = Integer.parseInt(action.substring("simplify".length()));
                        result.put(action, simplify(depth));
                    } else {
                        throw new IllegalArgumentException("Invalid action: " + action);
                    }
                }
            }
        }
        return result;
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

    public Scenario simplify(Integer maxDepth) {
        return null; // TODO
    }
}
