//@@author wyinkok
package seedu.address.ui;

import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMessage;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ChatBotCardHandle;
import guitests.guihandles.ChatBotListPanelHandle;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ChatBotListPanelTest extends GuiUnitTest {

    private ChatBotListPanelHandle chatBotListPanelHandle;
    private static final String EXPECTED_WELCOME_MESSAGE = "JOBBI:   " + "Hello there, I am Jobbi! "
            + "I am here to help you find your ideal internship today. Type 'start' to begin your search.";

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        ChatBotPanel chatBotListPanel = new ChatBotPanel(logic);
        uiPartRule.setUiPart(chatBotListPanel);

        chatBotListPanelHandle = new ChatBotListPanelHandle(getChildNode(chatBotListPanel.getRoot(),
                ChatBotListPanelHandle.CHAT_BOT_LIST_VIEW_ID));
    }
    @Test
    public void display_welcomeMessage() {
        ChatBotCardHandle actualCard = chatBotListPanelHandle.getHandleToWelcomeMessage();
        assertCardDisplaysMessage(EXPECTED_WELCOME_MESSAGE, actualCard);
    }
}

