package pl.put.poznan.qualitychecker.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class containing the main logic of the application. It
 * has implementations of all operations that can be executed
 * on a scenario.
 */
public class ScenarioQualityChecker {

    /** Scenario on which ScenarioQualityChecker will operate. */
    private final Scenario scenario;

    /**
     * Creates a new instance of class {@link ScenarioQualityChecker}.
     * @param scenario Scenario on which ScenarioQualityChecker will operate.
     */
    public ScenarioQualityChecker(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Executes specified actions on a scenario.
     * @param actions List of actions to be executed. Names of the
     *                actions should correspond to names of methods
     *                of this class (one exception is action
     *                'simplify[depth]`, where depth is the parameter
     *                of method simplify().
     * @return Dictionary mapping names of the actions to their results.
     */
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
     * Computes the total number of steps within a scenario,
     * including steps that are in all nested scenarios.
     * @return The number of all steps in the scenario.
     */
    public Integer countAllSteps() {
        return scenario.getAllSteps().size();
    }

    /**
     * Computes the total number of all conditional decisions in
     * the scenario (composite steps, starting with a keyword).
     * @return Number of all conditional decisions in the scenario.
     */
    public Integer countConditionalDecisions() {
        return scenario.getAllSteps()
                .stream()
                .filter(step -> step instanceof ScenarioStepComposite)
                .map(e -> 1)
                .reduce(0, Integer::sum);
    }

    /**
     * Finds all steps within a scenario at any level of nesting that
     * do not start with an actor.
     * @return A list of all steps found by the method.
     */
    public List<ScenarioStepComponent> getInvalidSteps() {

        List<ScenarioStepComponent> invalidSteps = new ArrayList<>();

        var actors = scenario.getAllActors();
        actors.add("System");
        for (var step : scenario.getAllSteps()) {
            boolean contain = false;
            for (String actor : actors) {
                if (step.getText().startsWith(actor)) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                invalidSteps.add(step);
            }
        }

        return invalidSteps;
    }

    /**
     * Transforms the scenario into its textual representation.
     * @return String representing the scenario.
     */
    public String toText() {
        StringBuilder text = new StringBuilder();
        text.append("Title: ").append(scenario.getTitle()).append("\n");
        text.append("Actors: ");
        for (var actor : scenario.getActors()) {
            text.append(actor).append(", ");
            // delete last comma
            if (actor.equals(scenario.getActors().get(scenario.getActors().size() - 1))) {
                text.deleteCharAt(text.length() - 2);
                text.append("\n");
            }
        }
        text.append("System actor: ").append(scenario.getSystemActor()).append("\n");
        text.append("\nSteps:\n");
        for (int i = 0; i < scenario.getSteps().size(); i++) {
            if (scenario.getSteps().get(i) instanceof ScenarioStepComposite) {
                text.append(i+1).append(". ").append(((ScenarioStepComposite) scenario.getSteps().get(i)).getType()).append(": ").append(scenario.getSteps().get(i).getText()).append("\n");
                text.append(toTextComponent((ScenarioStepComposite) scenario.getSteps().get(i), String.valueOf(i+1)));
            } else {
                text.append(i+1).append(". ").append(scenario.getSteps().get(i).getText()).append("\n");
            }
        }

        return text.toString();
    }

    /**
     * Helper function for the method toText().
     * @param parentStep
     * @param prefix
     * @return
     */
    private String toTextComponent(ScenarioStepComposite parentStep, String prefix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parentStep.getSubsteps().size(); i++) {
            if (parentStep.getSubsteps().get(i) instanceof ScenarioStepComposite) {
                result.append(prefix).append(".").append(i+1).append(". ").append(((ScenarioStepComposite) parentStep.getSubsteps().get(i)).getType()).append(": ").append(parentStep.getSubsteps().get(i).getText()).append("\n");
                result.append(toTextComponent((ScenarioStepComposite) parentStep.getSubsteps().get(i), prefix + "." + (i + 1)));
            } else {
                result.append(prefix).append(".").append(i+1).append(". ").append(parentStep.getSubsteps().get(i).getText()).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Simplifies the scenario by restricting it to a certain
     * maximum level of nesting.
     * @param maxDepth Maximum nesting depth to which the scenario
     *                 should be restricted.
     * @return Simplified version of the scenario.
     */
    public Scenario simplify(Integer maxDepth) {
        String actors = String.join(", ", scenario.getActors());
        Scenario simplifiedScenario = new Scenario(String.format("""
                Title: %s
                Actors: %s
                System actor: %s""", scenario.getTitle(), actors, scenario.getSystemActor()));
        int currentDepth = 1;
        if (maxDepth >= 1) {
            for (var step : scenario.getSteps()) {
                if (step instanceof ScenarioStepComposite) {
                    ScenarioStepComposite newStep = new ScenarioStepComposite(((ScenarioStepComposite) step).getType(), step.getText());
                    simplifiedScenario.addStep(newStep);
                    simplifyCompositeHelper(newStep, (ScenarioStepComposite) step, simplifiedScenario, currentDepth + 1, maxDepth);
                } else {
                    simplifiedScenario.addStep(step);
                }
            }
        }
        return simplifiedScenario;
    }

    /**
     * Helper function for method simplify().
     * @param parentStep
     * @param originalParentStep
     * @param simplifiedScenario
     * @param currentDepth
     * @param maxDepth
     */
    private void simplifyCompositeHelper(ScenarioStepComposite parentStep, ScenarioStepComposite originalParentStep, Scenario simplifiedScenario, int currentDepth, int maxDepth) {
        if (currentDepth <= maxDepth) {
            for (var step : originalParentStep.getSubsteps()) {
                if (step instanceof ScenarioStepComposite) {
                    ScenarioStepComposite newStep = new ScenarioStepComposite(((ScenarioStepComposite) step).getType(), step.getText());
                    parentStep.addSubstep(newStep);
                    simplifyCompositeHelper(newStep, (ScenarioStepComposite) step, simplifiedScenario, currentDepth + 1, maxDepth);
                } else {
                    parentStep.addSubstep(step);
                }
            }
        }
    }
}
