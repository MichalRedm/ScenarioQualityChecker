package pl.put.poznan.qualitychecker.logic;

/**
 * Class implementing logic for counting all steps
 * within a given scenario.
 */
public class ScenarioCountAllStepsVisitor implements Visitor {

    @Override
    public Integer visitScenario(Scenario scenario) {
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
        return 1;
    }
}
