package pl.put.poznan.qualitychecker.logic;

public class ScenarioStepLeaf implements ScenarioStepComponent {

    private String text;

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
