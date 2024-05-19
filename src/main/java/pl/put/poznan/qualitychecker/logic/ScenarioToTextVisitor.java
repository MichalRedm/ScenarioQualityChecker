package pl.put.poznan.qualitychecker.logic;

public class ScenarioToTextVisitor implements Visitor {

    private Integer i;
    private Scenario scenario;

    public String visitScenario(Scenario scenario) {
        this.scenario = scenario;
        StringBuilder text = new StringBuilder();
        text.append("Title: ").append(scenario.getTitle()).append("\n");
        text.append("Actors: ");
        for (var actor : scenario.getActors()) {
            text.append(actor).append(", ");
            // delete last comma
            if (actor.equals(scenario.getActors().get(scenario.getActors().size() - 1))) {
                text.deleteCharAt(text.length() - 2);
                text.append("\n");
            }
        }
        text.append("System actor: ").append(scenario.getSystemActor()).append("\n");
        text.append("\nSteps:\n");
        var steps = scenario.getSteps();
        for (i = 0; i < steps.size(); i++) {
            text.append(steps.get(i).accept(this));
        }

        return text.toString();
    }

    /**
     * Helper function for the method toText().
     * @param parentStep
     * @param prefix
     * @return
     */
    private String toTextComponent(ScenarioStepComposite parentStep, String prefix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parentStep.getSubsteps().size(); i++) {
            result.append(prefix).append(".");
            if (parentStep.getSubsteps().get(i) instanceof ScenarioStepComposite) {
                result.append(i+1).append(". ").append(((ScenarioStepComposite) parentStep.getSubsteps().get(i)).getType()).append(": ").append(parentStep.getSubsteps().get(i).getText()).append("\n");
                result.append(toTextComponent((ScenarioStepComposite) parentStep.getSubsteps().get(i), prefix + "." + (i + 1)));
            } else {
                result.append(i+1).append(". ").append(parentStep.getSubsteps().get(i).getText()).append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String visitStepComposite(ScenarioStepComposite stepComposite) {
        return (i + 1) + ". " + stepComposite.getType() + ": " + stepComposite.getText() + "\n" +
                toTextComponent(stepComposite, String.valueOf(i + 1));
    }

    @Override
    public String visitStepLeaf(ScenarioStepLeaf stepLeaf) {
        return (i + 1) + ". " + stepLeaf.getText() + "\n";
    }
}
