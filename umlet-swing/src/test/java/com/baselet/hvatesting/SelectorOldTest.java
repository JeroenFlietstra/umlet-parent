package com.baselet.hvatesting;

import com.baselet.control.basics.geom.Rectangle;
import com.baselet.diagram.DrawPanel;
import com.baselet.diagram.SelectorOld;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.old.element.Actor;
import com.baselet.element.old.element.Class;
import com.baselet.element.old.element.Database;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SelectorOldTest {

    private DrawPanel panel;
    private SelectorOld selector;

    @Before
    public void setUp() {
        panel = Mockito.mock(DrawPanel.class);
        selector = new SelectorOld(panel);
    }

    /**
     * Criteria:
     * - Assert.assertXXXX [Variant: assertEquals]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only selecting all elements on the panel,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElementsOnPanel_WhenSelectAll_ThenAllElementsSelected() {
        // Given
        List<GridElement> elements = new ArrayList<>();
        elements.add(new Actor());
        elements.add(new Class());
        elements.add(new Database());
        Mockito.when(panel.getGridElements()).thenReturn(elements);

        // When
        selector.selectAll();

        // Then
        List<GridElement> result = selector.getSelectedElements();
        assertEquals(elements, result);
    }

    /**
     * Criteria:
     * - Assert.assertXXXX [Variant: assertNotEquals]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only deselecting all elements on the panel,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenSelectedElementsOnPanel_WhenDeselectAll_ThenAllElementsDeselected() {
        // Given
        List<GridElement> elements = new ArrayList<>();
        elements.add(new Actor());
        elements.add(new Class());
        elements.add(new Database());
        Mockito.when(panel.getGridElements()).thenReturn(elements);
        selector.selectAll();

        // When
        selector.deselectAll();

        // Then
        List<GridElement> result = selector.getSelectedElements();
        assertNotEquals(elements, result);
    }

    /**
     * Criteria:
     * - Assert.assertXXXX [Variant: assertArrayEquals]
     * - CORRECT: The data has the correct format, the ordering is correct and the ranges are respected. The data is
     *      relevant and the test has the correct timings.
     * - RIGHT-BICEP: The results are correct and as expected, no boundary conditions, cross-check or forced errors
     *      are necessary. The performance is good.
     * - FIRST: The unit test is fast, has single responsibility for only selecting all elements in a range on the panel,
     *      is repeatable and is self-verifying.
     * - AAA: Arrange/Act/Assert is done according to the Given/When/Then format.
     */
    @Test
    public void GivenElementsOnPanel_WhenMultiselectRange_ThenAllElementsInRangeSelected() {
        // Given
        Rectangle range = new Rectangle();
        List<GridElement> elements = new ArrayList<>();
        GridElement element1 = new Actor();
        element1.setRectangle(range);
        GridElement element2 = new Class();
        element2.setRectangle(range);
        GridElement element3 = new Database();
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        Mockito.when(panel.getGridElements()).thenReturn(elements);

        // When
        selector.multiSelect(range);

        // Then
        List<GridElement> expected = elements.subList(0, 2);
        List<GridElement> result = selector.getSelectedElements();
        assertArrayEquals(expected.toArray(), result.toArray()); // Done explicitly for the sake of using a different assertion.
    }
}