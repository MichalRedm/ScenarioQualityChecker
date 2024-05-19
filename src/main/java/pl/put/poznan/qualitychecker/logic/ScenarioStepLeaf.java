package pl.put.poznan.qualitychecker.logic;

/** Simple step within a scenario that has no substeps. */
public class ScenarioStepLeaf implements ScenarioStepComponent {

    /** Text within a step. */
    private String text;

    /**
     * Creates a new instance of class {@link ScenarioStepLeaf}.
     * @param text Text within a step.
     */
    public ScenarioStepLeaf(String text) {
        this.text = text;
    }
    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
