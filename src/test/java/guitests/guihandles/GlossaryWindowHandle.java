package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code GlossaryWindow} of the application.
 */
public class GlossaryWindowHandle extends StageHandle {

    public static final String GLOSSARY_WINDOW_TITLE = "Glossary";

    private static final String GLOSSARY_WINDOW_BROWSER_ID = "#browser";

    public GlossaryWindowHandle(Stage glossaryWindowStage) {
        super(glossaryWindowStage);
    }

    /**
     * Returns true if a glossary window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(GLOSSARY_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl()  {
        return WebViewUtil.getLoadedUrl(getChildNode(GLOSSARY_WINDOW_BROWSER_ID));
    }
}
