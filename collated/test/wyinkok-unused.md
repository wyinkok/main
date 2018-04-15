# wyinkok-unused
###### \java\guitests\guihandles\GlossaryWindowHandle.java
``` java
/*
package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;
*/

/**
 * A handle to the {@code GlossaryWindow} of the application.

public class GlossaryWindowHandle extends StageHandle {

    public static final String GLOSSARY_WINDOW_TITLE = "Glossary";

    private static final String GLOSSARY_WINDOW_BROWSER_ID = "#browser";

    public GlossaryWindowHandle(Stage glossaryWindowStage) {
        super(glossaryWindowStage);
    }
 */
    /**
     * Returns true if a glossary window is currently present in the application.

    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(GLOSSARY_WINDOW_TITLE);
    }
     */

    /**
     * Returns the {@code URL} of the currently loaded page.

    public URL getLoadedUrl()  {
        return WebViewUtil.getLoadedUrl(getChildNode(GLOSSARY_WINDOW_BROWSER_ID));
    }
}
 */
```
###### \java\seedu\address\logic\commands\GlossaryCommandTest.java
``` java
/*
package seedu.address.logic.commands;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.GlossaryCommand.SHOWING_GLOSSARY_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowGlossaryRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;


public class GlossaryCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_glossary_success() {
        CommandResult result = new GlossaryCommand().execute();
        assertEquals(SHOWING_GLOSSARY_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowGlossaryRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
 */
```
