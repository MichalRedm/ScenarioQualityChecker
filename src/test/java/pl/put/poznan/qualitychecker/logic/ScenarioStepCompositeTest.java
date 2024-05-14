package pl.put.poznan.qualitychecker.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ScenarioStepCompositeTest {

    @Mock ScenarioStepLeaf stepLeafMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(stepLeafMock.getTotalStepCount()).thenReturn(1);
    }

    @AfterEach
    public void tearDown() {
        stepLeafMock = null;
    }

    @Test
    public void testBasicTotalStepCount() {
        var stepComposite = new ScenarioStepComposite(ScenarioStepCompositeType.IF, "Lorem ipsum");
        int n = 3;
        for (int i = 0; i < n; i++) {
            stepComposite.addSubstep(stepLeafMock);
        }
        assertEquals(n + 1, stepComposite.getTotalStepCount());
    }

    @Test
    public void testComplexTotalStepCount() {
        var stepComposite = new ScenarioStepComposite(ScenarioStepCompositeType.IF, "Lorem ipsum");
        int m = 3, n = 3, k = 3;
        for (int i = 0; i < m; i++) {
            var substepComposite = new ScenarioStepComposite(ScenarioStepCompositeType.IF, "Lorem ipsum");
            for (int j = 0; j < n; j++) {
                substepComposite.addSubstep(stepLeafMock);
            }
            stepComposite.addSubstep(substepComposite);
        }
        for (int i = 0; i < k; i++) {
            stepComposite.addSubstep(stepLeafMock);
        }
        assertEquals(m * (n + 1) + k + 1, stepComposite.getTotalStepCount());
    }
}