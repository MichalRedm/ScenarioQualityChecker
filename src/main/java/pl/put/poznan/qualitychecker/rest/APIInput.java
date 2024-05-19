package pl.put.poznan.qualitychecker.rest;

import pl.put.poznan.qualitychecker.logic.Scenario;

import java.util.List;

/**
 * Class that represents the structure of input
 * provided to the API.
 */
public class APIInput {
    /** Scenario that should be analyzed by the application. */
    private Scenario scenario;
    /** List of actions that should be performed on the scenario. */
    private List<String> actions;

    public APIInput(Scenario scenario, List<String> actions) {
        this.scenario = scenario;
        this.actions = actions;
    }

    /**
     * @return Scenario that should be analyzed by the application.
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * @return List of actions that should be performed on the scenario.
     */
    public List<String> getActions() {
        return actions;
    }
}
