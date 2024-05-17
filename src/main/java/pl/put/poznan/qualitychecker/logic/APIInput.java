package pl.put.poznan.qualitychecker.logic;

import java.util.List;

public class APIInput {
    private Scenario scenario;
    private List<String> actions;

    public APIInput(Scenario scenario, List<String> actions) {
        this.scenario = scenario;
        this.actions = actions;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public List<String> getActions() {
        return actions;
    }
}
