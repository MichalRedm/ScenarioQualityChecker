package pl.put.poznan.qualitychecker.logic;

/**
 * Common interface for all classes in the project
 * implementing the visitor pattern.
 */
public interface Visitor {
    /** Visit an instance of class {@link Scenario}. */
    Object visitScenario(Scenario scenario);
    /** Visit an instance of class {@link ScenarioStepComposite}. */
    Object visitStepComposite(ScenarioStepComposite stepComposite);
    /** Visit an instance of class {@link ScenarioStepLeaf}. */
    Object visitStepLeaf(ScenarioStepLeaf stepLeaf);
}
