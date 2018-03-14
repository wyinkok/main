package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a glossary page
 */
public class GlossaryWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/GlossaryGuide.html";

    private static final Logger logger = LogsCenter.getLogger(GlossaryWindow.class);
    private static final String FXML = "GlossaryWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new GlossaryWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public GlossaryWindow(Stage root) {
        super(FXML, root);

        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
    }

    /**
     * Creates a new GlossaryWindow.
     */
    public GlossaryWindow() {
        this(new Stage());
    }

    /**
     * Shows the Glossary window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing Glossary page about the application.");
        getRoot().show();
    }
}
