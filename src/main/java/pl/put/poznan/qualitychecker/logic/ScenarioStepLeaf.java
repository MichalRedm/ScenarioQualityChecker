package pl.put.poznan.qualitychecker.logic;

public class ScenarioStepLeaf implements ScenarioStepComponent {

    private String text;

    public void setText(String text) {
        this.text = text;
    }
    @Override
    public String getText() {
        return text;
    }
}
