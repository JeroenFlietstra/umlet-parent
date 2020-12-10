package com.baselet.hvatesting;

import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.diagram.DiagramHandler;
import com.baselet.diagram.DrawPanel;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.old.element.Actor;
import com.baselet.gui.command.AddElement;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

public class AddElementTest {

    private DrawPanel panel;

    @Before
    public void setUp() {
        DiagramHandler handler = Mockito.mock(DiagramHandler.class);
        Program.init("", RuntimeType.STANDALONE);
        panel = new DrawPanel(handler, true);
        Mockito.when(handler.getDrawPanel()).thenReturn(panel);
        Mockito.when(handler.getGridSize()).thenReturn(1000); // Prevent arithmetic error (division by zero)
    }

    /**
     * Criteria:
     * - Hamcrest [Variant: Matchers.contains]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only executing the use case,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElement_WhenAddElement_ThenElementAddedToPanel() {
        // Given
        GridElement element = new Actor();
        AddElement addElement = new AddElement(element, 1, 1, false);

        // When
        addElement.execute(panel.getHandler());

        // Then
        List<GridElement> result = panel.getGridElements();
        MatcherAssert.assertThat(result, Matchers.contains(element));
    }

    /**
     * Criteria:
     * - Hamcrest [Variant: Matchers.not]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only executing the use case and undoing the action,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElementAdded_WhenUndo_ThenElementRemovedFromPanel() {
        // Given
        GridElement element = new Actor();
        AddElement addElement = new AddElement(element, 1, 1, false);

        // When
        addElement.execute(panel.getHandler());
        addElement.undo(panel.getHandler());

        // Then
        List<GridElement> result = panel.getGridElements();
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(element)));
    }
}