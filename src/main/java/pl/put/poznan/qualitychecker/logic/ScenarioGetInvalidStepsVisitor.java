package pl.put.poznan.qualitychecker.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing logic for finding all
 * steps that do not start with an actor
 * within a given scenario.
 */
public class ScenarioGetInvalidStepsVisitor implements Visitor {

    /** List af all actors within the analyzed scenario. */
    private List<String> actors = new ArrayList<>();

    @Override
    public List<ScenarioStepComponent> visitScenario(Scenario scenario) {
        List<ScenarioStepComponent> invalidSteps = new ArrayList<>();
        actors = scenario.getAllActors();
        scenario.getSteps()
                .forEach(step -> invalidSteps.addAll((List<ScenarioStepComponent>) step.accept(this)));
        return invalidSteps;
    }

    @Override
    public List<ScenarioStepComponent> visitStepComposite(ScenarioStepComposite stepComposite) {
        List<ScenarioStepComponent> invalidSteps = new ArrayList<>();
        if (isTextInvalid(stepComposite.getText())) {
            invalidSteps.add(stepComposite);
        }
        stepComposite.getSubsteps()
                .forEach(step -> invalidSteps.addAll((List<ScenarioStepComponent>) step.accept(this)));
        return invalidSteps;
    }

    @Override
    public List<ScenarioStepComponent> visitStepLeaf(ScenarioStepLeaf stepLeaf) {
        List<ScenarioStepComponent> invalidSteps = new ArrayList<>();
        if (isTextInvalid(stepLeaf.getText())) {
            invalidSteps.add(stepLeaf);
        }
        return invalidSteps;
    }

    private boolean isTextInvalid(String text) {
        boolean isInvalid = true;
        for (String actor : actors) {
            if (text.startsWith(actor)) {
                isInvalid = false;
                break;
            }
        }
        return isInvalid;
    }
}
