package pl.put.poznan.qualitychecker.logic;

public class ScenarioCountConditionalDecisionsVisitor implements Visitor {

    @Override
    public Object visitScenario(Scenario scenario) {
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
                .reduce(0, Integer::sum) + 1;
    }

    @Override
    public Integer visitStepLeaf(ScenarioStepLeaf stepLeaf) {
        return 0;
    }
}
