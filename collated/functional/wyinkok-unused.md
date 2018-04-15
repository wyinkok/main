# wyinkok-unused
###### \java\seedu\address\commons\events\ui\ShowGlossaryRequestEvent.java
``` java
//package seedu.address.commons.events.ui;

//import seedu.address.commons.events.BaseEvent;


/**
 * An event requesting to view the Glossary page.

public class ShowGlossaryRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
 */
```
###### \java\seedu\address\logic\commands\GlossaryCommand.java
``` java
//package seedu.address.logic.commands;

//import seedu.address.commons.core.EventsCenter;
//import seedu.address.commons.events.ui.ShowGlossaryRequestEvent;

/**
 * Displays a full glossary of potential jobs, industries and other related information a user might consider.

public class GlossaryCommand extends Command {

    public static final String COMMAND_WORD = "glossary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows user a list of potential jobs, industries, skills and other related information to consider.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_GLOSSARY_MESSAGE = "Opened glossary window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowGlossaryRequestEvent());
        return new CommandResult(SHOWING_GLOSSARY_MESSAGE);
    }
}
 */
```
###### \java\seedu\address\ui\GlossaryWindow.java
``` java
/*
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
*/

/**
 * Controller for a glossary page

public class GlossaryWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/GlossaryGuide.html";

    private static final Logger logger = LogsCenter.getLogger(GlossaryWindow.class);
    private static final String FXML = "GlossaryWindow.fxml";

    @FXML
    private WebView browser;
 */

    /**
     * Creates a new GlossaryWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.

    public GlossaryWindow(Stage root) {
        super(FXML, root);
        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
    }
     */

    /**
     * Creates a new GlossaryWindow.

    public GlossaryWindow() {
        this(new Stage());
    }
     */

    /**
     * Shows the Glossary window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.ad
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>

    public void show() {
        logger.fine("Showing Glossary page about the application.");
        getRoot().show();
    }
}
     */
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the glossary window

    @FXML
    public void handleGlossary() {
        GlossaryWindow glossaryWindow = new GlossaryWindow();
        glossaryWindow.show();
    }
    */

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /*
    @Subscribe
    private void handleShowGlossaryEvent(ShowGlossaryRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleGlossary();
    }
    */

}
```
