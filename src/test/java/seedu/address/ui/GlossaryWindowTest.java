/* package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.GlossaryWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.GlossaryWindowHandle;
import javafx.stage.Stage;

public class GlossaryWindowTest extends GuiUnitTest {

    private GlossaryWindow glossaryWindow;
    private GlossaryWindowHandle glossaryWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> glossaryWindow = new GlossaryWindow());
        Stage glossaryWindowStage = FxToolkit.setupStage((stage)-> stage.setScene(glossaryWindow.getRoot().getScene()));
        FxToolkit.showStage();
        glossaryWindowHandle = new GlossaryWindowHandle(glossaryWindowStage);
    }

    @Test
    public void display() {
        URL expectedGlossaryPage = GlossaryWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedGlossaryPage, glossaryWindowHandle.getLoadedUrl());
    }
}

*/