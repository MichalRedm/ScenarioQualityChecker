package pl.put.poznan.qualitychecker.logic;

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

    private final static ScenarioToTextVisitor scenarioToTextVisitor = new ScenarioToTextVisitor();

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
        return (Integer) scenario.accept(new ScenarioCountAllStepsVisitor());
    }

    /**
     * Computes the total number of all conditional decisions in
     * the scenario (composite steps, starting with a keyword).
     * @return Number of all conditional decisions in the scenario.
     */
    public Integer countConditionalDecisions() {
        return (Integer) scenario.accept(new ScenarioCountConditionalDecisionsVisitor());
    }

    /**
     * Finds all steps within a scenario at any level of nesting that
     * do not start with an actor.
     * @return A list of all steps found by the method.
     */
    public List<ScenarioStepComponent> getInvalidSteps() {
        return (List<ScenarioStepComponent>) scenario.accept(new ScenarioGetInvalidStepsVisitor());
    }

    /**
     * Transforms the scenario into its textual representation.
     * @return String representing the scenario.
     */
    public String toText() {
        return (String) scenario.accept(scenarioToTextVisitor);
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
