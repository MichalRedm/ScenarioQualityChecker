package pl.put.poznan.qualitychecker.logic;

/**
 * A single step within a scenario - either a simple
 * one or a composite step that contains substeps.
 */
public interface ScenarioStepComponent {
    /**
     * @return Text within a step.
     */
    public String getText();

    public Object accept(Visitor visitor);
}
