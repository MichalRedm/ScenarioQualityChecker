package pl.put.poznan.qualitychecker.logic;

public interface ScenarioStepComponent {
    public String getText();

    /**
     * @return The total number of steps within this step.
     * If the step is a leaf step, then this number is just 1.
     * However, if the step is composite, this number is 1 plus
     * the number of all nested steps (on any level of nesting).
     */
    public Integer getTotalStepCount();
}
