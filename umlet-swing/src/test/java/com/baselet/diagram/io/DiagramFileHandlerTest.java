package com.baselet.diagram.io;

import com.baselet.control.enums.Program;
import com.baselet.control.enums.RuntimeType;
import com.baselet.diagram.DiagramHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class DiagramFileHandlerTest {

    private DiagramHandler diagramHandler;
    private DiagramFileHandler fileHandler;
    private File file;

    @Before
    public void setUp() {
        Program.init("", RuntimeType.STANDALONE);
        String fileName = "test";
        file = new File("src/test/resources/" + fileName + "." + Program.getInstance().getExtension());
        diagramHandler = Mockito.mock(DiagramHandler.class);
        fileHandler = new DiagramFileHandler(diagramHandler, file);
    }

    @Test
    public void GivenExistingDiagramChanged_WhenSaveDiagram_ThenDiagramSavedOnDrive() throws IOException {
        // Given
        new FileOutputStream(file); // File exists.
        diagramHandler.setChanged(true);
        long currentTime = System.currentTimeMillis();

        // When
        fileHandler.doSave();

        // Then
        assertTrue(file.lastModified() > currentTime);
    }
}