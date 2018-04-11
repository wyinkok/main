//@@author wyinkok
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.getTypicalInternships;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysInternship;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMessage;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysUserMessage;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ChatBotCardHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.InternshipCardHandle;
import guitests.guihandles.ChatBotListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.internship.Internship;

public class ChatBotListPanelTest extends GuiUnitTest {

    private ChatBotListPanelHandle chatBotListPanelHandle;

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
        String expectedWelcomeMessage = "JOBBI:   " + "Hello there, I am Jobbi! "
                + "I am here to help you find your ideal internship today. Type 'start' to begin your search.";
        ChatBotCardHandle actualCard = chatBotListPanelHandle.getHandleToWelcomeMessage();
        assertCardDisplaysMessage(expectedWelcomeMessage, actualCard);
        }


}


