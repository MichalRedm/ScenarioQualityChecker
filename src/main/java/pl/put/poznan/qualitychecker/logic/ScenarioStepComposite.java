package pl.put.poznan.qualitychecker.logic;

import java.util.List;

/** Step within a scenario that has substeps. */
public class ScenarioStepComposite implements ScenarioStepComponent {

    /** Type of the composite step (IF, ELSE, FOR EACH). */
    private ScenarioStepCompositeType type;
    /** Text within a step. */
    private String text;
    /** List of substeps of the composite step. */
    private List<ScenarioStepComponent> substeps;

    /**
     * Creates a new instance of class {@link ScenarioStepComposite}.
     * @param type Type of the composite step represented by {@link ScenarioStepCompositeType}.
     * @param text Text within a step.
     */
    public ScenarioStepComposite(ScenarioStepCompositeType type, String text) {
        this.type = type;
        this.text = text;
        this.substeps = new java.util.ArrayList<>();
    }

    /**
     * Adds a step to the list of substeps
     * @param substep Step to be added as a substep.
     */
    public void addSubstep(ScenarioStepComponent substep) {
        substeps.add(substep);
    }

    /**
     * @return Type of the composite step (IF, ELSE, FOR EACH).
     */
    public ScenarioStepCompositeType getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }

    /**
     * @return List of substeps of the composite step.
     */
    public List<ScenarioStepComponent> getSubsteps() {
        return substeps;
    }


}
