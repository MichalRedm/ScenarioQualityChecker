package pl.put.poznan.qualitychecker.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;
public class Scenario {

    private String title;
    private List<String> actors;
    private String systemActor;
    private List<ScenarioStepComponent> steps;

    public Scenario(String scenario) {
        // Parse the scenario string
        Scanner lineScanner = new Scanner(scenario);
        title = lineScanner.nextLine().substring(7).trim();
        actors = List.of(lineScanner.nextLine().substring(8).split(","));
        systemActor = lineScanner.nextLine().substring(14).trim();
        steps = new java.util.ArrayList<>();
        while (lineScanner.hasNextLine()) {
            int nestingLevel = 0;
            String line = lineScanner.nextLine();
            if (line.isEmpty()) {
                continue;
            } else {
                line = line.substring(2);
            }
            if (line.startsWith("IF:")) {
                ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.IF, line.substring(4));
                steps.add(step);
            } else if (line.startsWith("ELSE:")) {
                ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.ELSE, line.substring(6));
                steps.add(step);
            } else if (line.startsWith("FOR EACH:")) {
                ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.FOR_EACH, line.substring(9));
                steps.add(step);
            } else if (line.startsWith("*") || line.startsWith("-") || line.startsWith(" ")) {
                while (line.startsWith("*") || line.startsWith("-") || line.startsWith(" ")) {
                    line = line.substring(2);
                    nestingLevel++;
                }
                if (line.startsWith("IF:")) {
                    ScenarioStepComposite parentStep = (ScenarioStepComposite) steps.get(steps.size() - 1);
                    for (int i = 1; i < nestingLevel; i++) {
                        parentStep = (ScenarioStepComposite) parentStep.getSubsteps().get(parentStep.getSubsteps().size() - 1);
                    }
                    ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.IF, line.substring(4));
                    parentStep.addSubstep(step);
                } else if (line.startsWith("ELSE:")) {
                    ScenarioStepComposite parentStep = (ScenarioStepComposite) steps.get(steps.size() - 1);
                    for (int i = 1; i < nestingLevel; i++) {
                        parentStep = (ScenarioStepComposite) parentStep.getSubsteps().get(parentStep.getSubsteps().size() - 1);
                    }
                    ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.ELSE, line.substring(6));
                    parentStep.addSubstep(step);
                } else if (line.startsWith("FOR EACH:")) {
                    ScenarioStepComposite parentStep = (ScenarioStepComposite) steps.get(steps.size() - 1);
                    for (int i = 1; i < nestingLevel; i++) {
                        parentStep = (ScenarioStepComposite) parentStep.getSubsteps().get(parentStep.getSubsteps().size() - 1);
                    }
                    ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.FOR_EACH, line.substring(9));
                    parentStep.addSubstep(step);
                } else { // leaf step
                    ScenarioStepComposite parentStep = (ScenarioStepComposite) steps.get(steps.size() - 1);
                    for (int i = 1; i < nestingLevel; i++) {
                        parentStep = (ScenarioStepComposite) parentStep.getSubsteps().get(parentStep.getSubsteps().size() - 1);
                    }
                    ScenarioStepLeaf step = new ScenarioStepLeaf(line);
                    parentStep.addSubstep(step);
                }
            } else { // leaf step, primary level
                ScenarioStepLeaf step = new ScenarioStepLeaf(line);
                steps.add(step);
            }
                while (line.startsWith(" ")) {
                    line = line.substring(2);
                    nestingLevel++;
                }
        }
    }

    public String toJSON() {
        Gson gson = new Gson();
        // write JSON to a file
        try (FileWriter writer = new FileWriter("scenario.json")) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.toJson(this);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getActors() {
        return actors;
    }

    public String getSystemActor() {
        return systemActor;
    }

    public List<ScenarioStepComponent> getSteps() {
        return steps;
    }
}
