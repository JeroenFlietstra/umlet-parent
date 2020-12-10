package com.baselet.hvatesting;

import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.diagram.DiagramHandler;
import com.baselet.diagram.DrawPanel;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.old.element.Actor;
import com.baselet.element.old.element.Class;
import com.baselet.gui.command.Search;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SearchTest {

    private DrawPanel panel;

    @Before
    public void setUp() {
        DiagramHandler handler = Mockito.mock(DiagramHandler.class);
        Program.init("", RuntimeType.STANDALONE);
        panel = new DrawPanel(handler, true);
        Mockito.when(handler.getDrawPanel()).thenReturn(panel);
    }

    /**
     * Criteria:
     * - Hamcrest [Variant: Matchers.equalTo]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only a successful search and selecting the results,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElementsOnPanel_WhenSuccessfulSearch_ThenResultSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        GridElement element2 = new Class();
        element2.setProperty("title", "This is a class!");
        List<GridElement> elements = new ArrayList<>();
        elements.add(element1);
        panel.setGridElements(elements);
        Search search = new Search("actor");

        // When
        search.execute(panel.getHandler());

        // Then
        List<GridElement> expected = elements.subList(0, 1);
        List<GridElement> result = panel.getSelector().getSelectedElements();
        MatcherAssert.assertThat(expected, Matchers.equalTo(result));
    }

    /**
     * Criteria:
     * - Hamcrest [Variant: Matchers.empty]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only a failed search,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElementsOnPanel_WhenFailedSearch_ThenNothingSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        GridElement element2 = new Class();
        element2.setProperty("title", "This is a class!");
        List<GridElement> elements = new ArrayList<>();
        elements.add(element1);
        elements.add(element2);
        panel.setGridElements(elements);
        Search search = new Search("database");

        // When
        search.execute(panel.getHandler());

        // Then
        List<GridElement> result = panel.getSelector().getSelectedElements();
        MatcherAssert.assertThat(result, Matchers.<GridElement>empty());
    }

    /**
     * Criteria:
     * - Hamcrest [Variant: Matchers.hasSize]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only a successful search and selecting the results
     *      and undoing the action, is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElementsSelectedAfterSearch_WhenUndo_ThenNothingSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        GridElement element2 = new Actor();
        element2.setProperty("title", "This is another actor!");
        List<GridElement> elements = new ArrayList<>();
        elements.add(element1);
        panel.setGridElements(elements);
        Search search = new Search("actor");

        // When
        search.execute(panel.getHandler());
        search.undo(panel.getHandler());

        // Then
        List<GridElement> result = panel.getSelector().getSelectedElements();
        MatcherAssert.assertThat(result, Matchers.<GridElement>hasSize(0));
    }
}