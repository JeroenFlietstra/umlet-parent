package com.baselet.diagram;

import com.baselet.control.basics.geom.Rectangle;
import com.baselet.control.constants.Constants;
import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.old.element.Actor;
import com.baselet.element.old.element.Class;
import com.baselet.element.old.element.Database;
import com.baselet.gui.command.AddElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SelectorOldTest {

    private DiagramHandler handler;
    private SelectorOld selector;

    @Before
    public void setUp() {
        handler = Mockito.mock(DiagramHandler.class);
        Program.init("", RuntimeType.STANDALONE);
        Mockito.when(handler.getDrawPanel()).thenReturn(new DrawPanel(handler, true));
        Mockito.when(handler.getGridSize()).thenReturn(Constants.DEFAULTGRIDSIZE); // Prevent arithmetic error (division by zero)
        selector = handler.getDrawPanel().getSelector();
    }

    @Test
    public void GivenElementsOnPanel_WhenSelectAll_ThenAllElementsSelected() {
        // Given
        GridElement element1 = new Actor();
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        AddElement addElement2 = new AddElement(element2, 0, 0, false);
        GridElement element3 = new Database();
        AddElement addElement3 = new AddElement(element3, 0, 0, false);

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        addElement3.execute(handler);
        handler.getDrawPanel().getSelector().selectAll();

        // Then
        List<GridElement> expected = new ArrayList<>();
        expected.add(element1);
        expected.add(element2);
        expected.add(element3);
        List<GridElement> result = selector.getSelectedElements();
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void GivenSelectedElementsOnPanel_WhenDeselectAll_ThenAllElementsDeselected() {
        // Given
        GridElement element1 = new Actor();
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        AddElement addElement2 = new AddElement(element2, 0, 0, false);
        GridElement element3 = new Database();
        AddElement addElement3 = new AddElement(element3, 0, 0, false);

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        addElement3.execute(handler);
        selector.selectAll();
        selector.deselectAll();

        // Then
        List<GridElement> result = selector.getSelectedElements();
        assertTrue(result.isEmpty());
    }

    @Test
    public void GivenElementsOnPanel_WhenMultiselectRange_ThenAllElementsInRangeSelected() {
        // Given
        Rectangle range = new Rectangle(500, 500, 500, 500);
        GridElement element1 = new Actor();
        AddElement addElement1 = new AddElement(element1, range.getX(), range.getY(), false);
        GridElement element2 = new Class();
        AddElement addElement2 = new AddElement(element2, range.getX(), range.getY(), false);
        GridElement element3 = new Database();
        AddElement addElement3 = new AddElement(element3, 0, 0, false);

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        addElement3.execute(handler);
        selector.deselectAll(); // Newly added elements are selected by default.
        selector.multiSelect(range);

        // Then
        assertTrue(selector.isSelected(element1));
        assertTrue(selector.isSelected(element2));
        assertFalse(selector.isSelected(element3));
    }

    @Test
    public void GivenElementsOnPanel_WhenGroupedTogetherAndOneElementSelected_ThenAllElementsInGroupSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setPanelAttributes("group=1");
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        element2.setPanelAttributes("group=1");
        AddElement addElement2 = new AddElement(element2, 0, 0, false);
        GridElement element3 = new Database();
        AddElement addElement3 = new AddElement(element3, 0, 0, false);
        ArrayList<GridElement> group = new ArrayList<>();
        group.add(element1);
        group.add(element2);

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        addElement3.execute(handler);
        selector.deselectAll(); // Newly added elements are selected by default.
        selector.select(element1);

        // Then
        List<GridElement> result = selector.getSelectedElements();
        assertEquals(element1.getGroup(), element2.getGroup());
        assertEquals(group.size(), result.size());
        assertTrue(result.containsAll(group));
        assertTrue(selector.isSelected(element1));
        assertTrue(selector.isSelected(element2));
        assertFalse(selector.isSelected(element3));
    }

    @Test
    public void GivenGroupOnPanel_WhenUngroupedAndOneElementSelected_ThenOnlyThatElementSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setPanelAttributes("group=1");
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        element2.setPanelAttributes("group=1");
        AddElement addElement2 = new AddElement(element2, 0, 0, false);

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        element1.setPanelAttributes(element1.getPanelAttributes().replace("group=1", ""));
        element2.setPanelAttributes(element2.getPanelAttributes().replace("group=1", ""));
        selector.deselectAll(); // Newly added elements are selected by default.
        selector.select(element1);

        // Then
        assertTrue(selector.isSelected(element1));
        assertFalse(selector.isSelected(element2));
    }
}