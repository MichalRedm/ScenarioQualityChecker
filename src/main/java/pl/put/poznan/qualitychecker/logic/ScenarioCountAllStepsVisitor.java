package pl.put.poznan.qualitychecker.logic;

public class ScenarioCountAllStepsVisitor implements Visitor {

    public Integer countAllSteps(Scenario scenario) {
        return scenario.getSteps()
                .stream()
                .map(step -> (Integer) step.accept(this))
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer visitStepComposite(ScenarioStepComposite stepComposite) {
        return stepComposite.getSubsteps()
                .stream()
                .map(step -> (Integer) step.accept(this))
                .reduce(0, Integer::sum);
    }

    @Override
    public Integer visitStepLeaf(ScenarioStepLeaf stepLeaf) {
        return 1;
    }
}
