package pl.put.poznan.qualitychecker.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.google.gson.*;
public class Scenario {

    private String title;
    private List<String> actors;
    private String systemActor;
    private List<ScenarioStepComponent> steps;

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

//    public static Scenario fromString(String scenario) {
//
//    }

    public String toJSON() {
        Gson gson = new Gson();
        // write JSON to a file
//        try (FileWriter writer = new FileWriter("scenario.json")) {
//            gson.toJson(this, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
