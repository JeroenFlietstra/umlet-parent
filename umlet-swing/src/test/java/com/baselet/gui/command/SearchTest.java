package com.baselet.gui.command;

import com.baselet.control.constants.Constants;
import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.diagram.DiagramHandler;
import com.baselet.diagram.DrawPanel;
import com.baselet.element.interfaces.GridElement;
import com.baselet.element.old.element.Actor;
import com.baselet.element.old.element.Class;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchTest {

    private DiagramHandler handler;

    @Before
    public void setUp() {
        handler = Mockito.mock(DiagramHandler.class);
        Program.init("", RuntimeType.STANDALONE);
        Mockito.when(handler.getDrawPanel()).thenReturn(new DrawPanel(handler, true));
        Mockito.when(handler.getGridSize()).thenReturn(Constants.DEFAULTGRIDSIZE); // Prevent arithmetic error (division by zero)
    }

    @Test
    public void GivenElementOnPanel_WhenSuccessfulSearch_ThenResultSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        Search search = new Search("actor");

        // When
        addElement1.execute(handler);
        search.execute(handler);

        // Then
        List<GridElement> expected = new ArrayList<>();
        expected.add(element1);
        List<GridElement> result = handler.getDrawPanel().getSelector().getSelectedElements();
        assertEquals(expected, result);
    }

    @Test
    public void GivenElementsOnPanel_WhenSuccessfulSearch_ThenResultSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        element2.setProperty("title", "This is a class!");
        AddElement addElement2 = new AddElement(element2, 0, 0, false);
        Search search = new Search("actor");

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        search.execute(handler);

        // Then
        List<GridElement> expected = new ArrayList<>();
        expected.add(element1);
        List<GridElement> result = handler.getDrawPanel().getSelector().getSelectedElements();
        assertEquals(expected, result);
    }

    @Test
    public void GivenElementsOnPanel_WhenFailedSearch_ThenNothingSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        element2.setProperty("title", "This is a class!");
        AddElement addElement2 = new AddElement(element2, 0, 0, false);
        Search search = new Search("database");

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        search.execute(handler);

        // Then
        List<GridElement> result = handler.getDrawPanel().getSelector().getSelectedElements();
        assertTrue(result.isEmpty());
    }

    @Test
    public void GivenElementsSelectedAfterSearch_WhenUndo_ThenNothingSelected() {
        // Given
        GridElement element1 = new Actor();
        element1.setProperty("title", "This is an actor!");
        AddElement addElement1 = new AddElement(element1, 0, 0, false);
        GridElement element2 = new Class();
        element2.setProperty("title", "This is a class!");
        AddElement addElement2 = new AddElement(element2, 0, 0, false);
        Search search = new Search("actor");

        // When
        addElement1.execute(handler);
        addElement2.execute(handler);
        search.execute(handler);
        search.undo(handler);

        // Then
        List<GridElement> result = handler.getDrawPanel().getSelector().getSelectedElements();
        assertTrue(result.isEmpty());
    }
}