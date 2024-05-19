package pl.put.poznan.qualitychecker.logic;

public interface Visitor {
    Object visitScenario(Scenario scenario);
    Object visitStepComposite(ScenarioStepComposite stepComposite);
    Object visitStepLeaf(ScenarioStepLeaf stepLeaf);
}
