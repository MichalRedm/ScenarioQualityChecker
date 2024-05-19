package pl.put.poznan.qualitychecker.logic;

public interface Visitor {
    Object visitStepComposite(ScenarioStepComposite stepComposite);
    Object visitStepLeaf(ScenarioStepLeaf stepLeaf);
}
