package pl.put.poznan.qualitychecker.logic;

import java.util.List;

public class ScenarioStepComposite implements ScenarioStepComponent {

    private ScenarioStepCompositeType type;
    private String text;
    private List<ScenarioStepComponent> substeps;

    public ScenarioStepComposite() {
        // TODO
    }

    @Override
    public String getText() {
        return text;
    }

    public List<ScenarioStepComponent> getSubsteps() {
        return substeps;
    }
}
