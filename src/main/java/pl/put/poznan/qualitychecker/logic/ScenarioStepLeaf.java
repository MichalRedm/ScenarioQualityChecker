package pl.put.poznan.qualitychecker.logic;

public class ScenarioStepLeaf implements ScenarioStepComponent {

    private String text;

    @Override
    public String getText() {
        return text;
    }
}
