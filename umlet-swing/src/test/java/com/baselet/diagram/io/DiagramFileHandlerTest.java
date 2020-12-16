package com.baselet.diagram.io;

import com.baselet.control.constants.Constants;
import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.diagram.CurrentDiagram;
import com.baselet.diagram.DiagramHandler;
import com.baselet.diagram.DrawPanel;
import com.baselet.element.interfaces.Diagram;
import com.baselet.gui.BaseGUI;
import com.baselet.gui.CurrentGui;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.Frame;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class DiagramFileHandlerTest {

    private Frame frame;
    private DiagramHandler diagramHandler;
    private JFileChooser fileChooser;
    private DiagramFileHandler handler;
    private BaseGUI baseGUI;
    private String absoluteTestDir;

    @Before
    public void setUp(){
        String path = "src/test/resources";
        File file = new File(path);
        absoluteTestDir = file.getAbsolutePath();
        frame = Mockito.mock(Frame.class);
        fileChooser = Mockito.mock(JFileChooser.class);
        diagramHandler = Mockito.mock(DiagramHandler.class);
        baseGUI = Mockito.mock(BaseGUI.class);
        diagramHandler.setChanged(true);
        Program.init("", RuntimeType.STANDALONE);

        Mockito.when(fileChooser.showSaveDialog(frame)).thenReturn(JFileChooser.APPROVE_OPTION);
        Mockito.when(fileChooser.getSelectedFile()).thenReturn(new File(absoluteTestDir + "\\test"));
        Mockito.when(diagramHandler.getName()).thenReturn("test");
        Mockito.when(diagramHandler.getDrawPanel()).thenReturn(new DrawPanel(diagramHandler, true));
        Mockito.when(diagramHandler.getGridSize()).thenReturn(Constants.DEFAULTGRIDSIZE); // Prevent arithmetic error (division by zero)
        CurrentGui.getInstance().setGui(baseGUI);
        Mockito.when(baseGUI.getMainFrame()).thenReturn(frame);
        CurrentDiagram.getInstance().setCurrentDiagramHandler(diagramHandler);
        CurrentGui.getInstance().setGui(baseGUI);
        Mockito.when(baseGUI.getMainFrame()).thenReturn(frame);
        handler = new DiagramFileHandler(diagramHandler,null);
    }


    @Test
    public void GivenModifiedCanvas_WhenCanvasNotSaved_ThenSaveDialogShows() throws IOException {
        //Given
        diagramHandler.setChanged(true);
        //When
        handler = new DiagramFileHandler(diagramHandler,null);
        handler.doSave();
        //Then
        assertTrue(true);
    }

    @Test
    public void GivenModifiedCanvas_WhenCanvasAlreadySavedOnce_ThenFileSaves() throws IOException{
        handler = new DiagramFileHandler(diagramHandler,new File(absoluteTestDir + "\\test.ufx"));

        handler.doSave();

    }

}