package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final InternshipListPanelHandle internshipListPanel;
    private final ChatBotListPanelHandle chatBotListPanel;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        internshipListPanel = new InternshipListPanelHandle(getChildNode(InternshipListPanelHandle
                .INTERNSHIP_LIST_VIEW_ID));
        chatBotListPanel = new ChatBotListPanelHandle(getChildNode(ChatBotListPanelHandle
                .CHAT_BOT_LIST_VIEW_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public InternshipListPanelHandle getInternshipListPanel() {
        return internshipListPanel;
    }

    //@@author wyinkok
    public ChatBotListPanelHandle getChatBotListPanel() {
        return chatBotListPanel;
    }

    //@@author
    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
