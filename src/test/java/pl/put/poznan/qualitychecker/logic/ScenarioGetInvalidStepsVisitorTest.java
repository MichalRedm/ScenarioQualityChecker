package pl.put.poznan.qualitychecker.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioGetInvalidStepsVisitorTest {
    @Test
    public void visitStepLeaf() {
        ScenarioStepLeaf stepLeaf = Mockito.mock(ScenarioStepLeaf.class);
        Mockito.when(stepLeaf.getText())
                .thenReturn("Librarian selects options to add a new book item")
                .thenReturn("A form is displayed.")
                .thenReturn("Librarian provides the details of the book.");
        List<ScenarioStepComponent> invalidSteps = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (isTextInvalid(stepLeaf.getText())) {
                invalidSteps.add(stepLeaf);
            }
        }
        assertEquals(1, invalidSteps.size());
    }

    private boolean isTextInvalid(String text) {
        boolean isInvalid = true;
        ArrayList<String> actors =
                new ArrayList<>(Arrays.asList("Librarian", "System"));
        for (String actor : actors) {
            if (text.startsWith(actor)) {
                isInvalid = false;
                break;
            }
        }
        return isInvalid;
    }
}