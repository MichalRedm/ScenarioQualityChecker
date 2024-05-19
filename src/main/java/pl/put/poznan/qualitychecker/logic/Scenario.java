package pl.put.poznan.qualitychecker.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.google.gson.*;

/** Representation of a single scenario. */
public class Scenario {

    /** Title of the scenario. */
    private String title;
    /** List of names of all the actors. */
    private List<String> actors;
    /** Name of the system actor. */
    private String systemActor;
    /** List of steps within a scenario. */
    private List<ScenarioStepComponent> steps;

    /**
     * Creates a new instance of class Scenario.
     * @param scenario Textual representation of a scenario.
     */
    public Scenario(String scenario) {
        // Replace all tabs with 4 spaces
        scenario = scenario.replaceAll("\t", "    ");

        // Parse the scenario string
        Scanner lineScanner = new Scanner(scenario);

        title = lineScanner.nextLine().substring(7).trim();
        actors = List.of(lineScanner.nextLine().substring(8).split(",(\\s)*"));
        systemActor = lineScanner.nextLine().substring(14).trim();

        steps = new ArrayList<>();

        int spacesPerIndent = -1;
        int expectedIndent = 0;

        Stack<ScenarioStepComposite> stack = new Stack<>();

        while (lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine();
            int indent;
            if (line.isEmpty()) {
                continue;
            } else {
                int leadingSpaces = leadingSpacesCount(line);
                if (spacesPerIndent == -1 && leadingSpaces > 0) {
                    spacesPerIndent = leadingSpaces;
                }
                line = line.substring(leadingSpaces + 1).trim();
                indent = leadingSpaces / spacesPerIndent;
                if (indent < expectedIndent) {
                    for (int i = 0; i < expectedIndent - indent; i++) {
                        stack.pop();
                    }
                } else if (indent > expectedIndent + 1) {
                    throw new RuntimeException("Invalid indentation encountered.");
                }
            }
            // check if the line is a composite step
            if (line.startsWith("IF:") || line.startsWith("ELSE:") || line.startsWith("FOR EACH:")) {
                ScenarioStepCompositeType type;
                if (line.startsWith("IF:")) {
                    type = ScenarioStepCompositeType.IF;
                } else if (line.startsWith("ELSE:")) {
                    type = ScenarioStepCompositeType.ELSE;
                } else {
                    type = ScenarioStepCompositeType.FOR_EACH;
                }
                var step = new ScenarioStepComposite(type, line.substring(line.indexOf(":") + 1).trim());
                expectedIndent = indent + 1;
                if (stack.isEmpty()) {
                    steps.add(step);
                } else {
                    stack.peek().addSubstep(step);
                }
                stack.push(step);
            } else {
                ScenarioStepLeaf step = new ScenarioStepLeaf(line);
                if (stack.isEmpty()) {
                    steps.add(step);
                } else {
                    stack.peek().addSubstep(step);
                }
                expectedIndent = indent;
            }

        }
    }

    /**
     * Helper function for parsing textual representation of a scenario.
     * Counts the number of leading spaces in a string.
     * @param s String to be analyzed.
     * @return Number of leading spaces.
     */
    private static Integer leadingSpacesCount(String s) {
        Integer result = 0;
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                result++;
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Transforms the scenario into its JSON representation.
     * @return JSON string representing the scenario.
     */
    public String toJSON() {
        return new Gson().toJson(this);
    }

    /**
     * @return Title of the scenario.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return List of names of all the actors.
     */
    public List<String> getActors() {
        return actors;
    }

    /**
     * @return Name of the system actor.
     */
    public String getSystemActor() {
        return systemActor;
    }

    /**
     * @return List of steps within a scenario.
     */
    public List<ScenarioStepComponent> getSteps() {
        return steps;
    }

    /**
     * @return List of all steps within scenario (at any level of nesting)
     * in proper order.
     */
    public List<ScenarioStepComponent> getAllSteps() {
        var stack = new Stack<ScenarioStepComponent>();
        List<ScenarioStepComponent> allSteps = new LinkedList<>();
        int stepsSize = steps.size();
        for (int i = 0; i < stepsSize; i++) {
            stack.push(steps.get(stepsSize - i - 1));
        }
        while (!stack.isEmpty()) {
            var step = stack.pop();
            allSteps.add(step);
            if (step instanceof ScenarioStepComposite) {
                var substeps = ((ScenarioStepComposite) step).getSubsteps();
                int substepsSize = substeps.size();
                for (int j = 0; j < substepsSize; j++) {
                    stack.push(substeps.get(substepsSize - j - 1));
                }
            }
        }
        return allSteps;
    }

    /**
     * @return List of all actors for a scenario - including
     * the system actor.
     */
    public List<String> getAllActors() {
        List<String> allActors = new LinkedList<>(actors);
        allActors.add(systemActor);
        return allActors;
    }

    /**
     * Adds a step to the scenario.
     * @param step Step to be added.
     */
    public void addStep(ScenarioStepComponent step) {
        steps.add(step);
    }
}
