package com.baselet.gui.command;

import com.baselet.control.constants.Constants;
import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.diagram.DiagramHandler;
import com.baselet.diagram.DrawPanel;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.old.element.Actor;
import com.baselet.element.old.element.Class;
import com.baselet.element.old.element.Database;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddElementTest {

    private DiagramHandler handler;

    @Before
    public void setUp() {
        handler = Mockito.mock(DiagramHandler.class);
        Program.init("", RuntimeType.STANDALONE);
        Mockito.when(handler.getDrawPanel()).thenReturn(new DrawPanel(handler, true));
        Mockito.when(handler.getGridSize()).thenReturn(Constants.DEFAULTGRIDSIZE); // Prevent arithmetic error (division by zero)
    }

    @Test
    public void GivenElement_WhenAddElement_ThenElementAddedToPanel() {
        // Given
        GridElement element = new Actor();
        AddElement addElement = new AddElement(element, 1, 1, false);

        // When
        addElement.execute(handler);

        // Then
        List<GridElement> result = handler.getDrawPanel().getGridElements();
        assertTrue(result.contains(element));
    }

    @Test
    public void GivenMultipleElements_WhenAddMultipleElement_ThenMultipleElementsAddedToPanel() {
        // Given
        GridElement element1 = new Actor();
        AddElement addElement1 = new AddElement(element1, 1, 1, false);
        GridElement element2 = new Class();
        AddElement addElement2 = new AddElement(element2, 1, 1, false);
        GridElement element3 = new Database();
        AddElement addElement3 = new AddElement(element3, 1, 1, false);

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        addElement3.execute(handler);

        // Then
        List<GridElement> expected = new ArrayList<>();
        expected.add(element1);
        expected.add(element2);
        expected.add(element3);
        List<GridElement> result = handler.getDrawPanel().getGridElements();
        assertTrue(result.containsAll(expected));
    }

    @Test
    public void GivenElementAdded_WhenUndo_ThenElementRemovedFromPanel() {
        // Given
        GridElement element = new Actor();
        AddElement addElement = new AddElement(element, 1, 1, false);

        // When
        addElement.execute(handler);
        addElement.undo(handler);

        // Then
        List<GridElement> result = handler.getDrawPanel().getGridElements();
        assertFalse(result.contains(element));
    }
}
