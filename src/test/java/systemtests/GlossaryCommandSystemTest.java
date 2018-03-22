package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.GlossaryWindowHandle;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.GlossaryCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for the glossary window, which contains interaction with other UI components.
 */
public class GlossaryCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openGlossaryWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openGlossaryWindowUsingAccelerator();
        assertGlossaryWindowOpen();

        getResultDisplay().click();
        getMainMenu().openGlossaryWindowUsingAccelerator();
        assertGlossaryWindowOpen();

        getInternshipPersonListPanel().click();
        getMainMenu().openGlossaryWindowUsingAccelerator();
        assertGlossaryWindowOpen();

        getBrowserPanel().click();
        getMainMenu().openGlossaryWindowUsingAccelerator();
        assertGlossaryWindowNotOpen();

        //use menu button
        getMainMenu().openGlossaryWindowUsingMenu();
        assertGlossaryWindowOpen();

        //use command box
        executeCommand(GlossaryCommand.COMMAND_WORD);
        assertGlossaryWindowOpen();

        // open help window and give it focus
        executeCommand(GlossaryCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // assert that while the glossary window is open the UI updates correctly for a command execution
        executeCommand(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(GlossaryCommand.SHOWING_GLOSSARY_MESSAGE, getResultDisplay().getText());
        assertNotEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertListMatching(getInternshipPersonListPanel(), getModel().getFilteredPersonList());

        // assert that the status bar too is updated correctly while the glossary window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Asserts that the glossary window is open, and closes it after checking.
     */
    private void assertGlossaryWindowOpen() {
        assertTrue(ERROR_MESSAGE, GlossaryWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new GlossaryWindowHandle(guiRobot.getStage(GlossaryWindowHandle.GLOSSARY_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the glossary window isn't open.
     */
    private void assertGlossaryWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, GlossaryWindowHandle.isWindowPresent());
    }
}

