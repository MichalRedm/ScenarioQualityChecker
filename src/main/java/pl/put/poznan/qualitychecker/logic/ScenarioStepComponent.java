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

    /**
     * Accepts a {@link Visitor}.
     * @param visitor Visitor to be accepted.
     * @return Object returned by the visitor.
     */
    public Object accept(Visitor visitor);
}
