package pl.put.poznan.qualitychecker.logic;

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
        System.out.println("the title is: " + title);
        System.out.println("the actors are: " + actors);
        System.out.println("the system actor is: " + systemActor);
        while (lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine();
            if (line.isEmpty()) {
                continue;
            } else {
                line = line.substring(2);
            }
            System.out.println(line);
            // check if the line is a composite step
            if (line.startsWith("IF:")) {
                ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.IF, line.substring(4));
                steps.add(step);
            } else if (line.startsWith("ELSE:")) {
                ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.ELSE, line.substring(6));
                steps.add(step);
            } else if (line.startsWith("FOR EACH:")) {
                ScenarioStepComposite step = new ScenarioStepComposite(ScenarioStepCompositeType.FOR_EACH, line.substring(9));
                steps.add(step);
            } else {
                ScenarioStepLeaf step = new ScenarioStepLeaf();
                step.setText(line);
                steps.add(step);
            }

        }
        System.out.println("the steps are: " + steps);
    }

    public String toJSON() {
        Gson gson = new Gson();
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
