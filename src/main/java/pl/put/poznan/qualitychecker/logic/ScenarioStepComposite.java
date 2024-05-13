package pl.put.poznan.qualitychecker.logic;

import java.util.List;

public class ScenarioStepComposite implements ScenarioStepComponent {

    private ScenarioStepCompositeType type;
    private String text;
    private List<ScenarioStepComponent> substeps;

    public ScenarioStepComposite(ScenarioStepCompositeType type, String text) {
        this.type = type;
        this.text = text;
        this.substeps = new java.util.ArrayList<>();
    }

    public void addSubstep(ScenarioStepComponent substep) {
        substeps.add(substep);
    }
    public ScenarioStepCompositeType getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }

    public List<ScenarioStepComponent> getSubsteps() {
        return substeps;
    }
}
