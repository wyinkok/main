//@@author wyinkok
package seedu.address.ui;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;


/**
 * Panel containing the message thread between chatbot and user.
 */
public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);
    private static final String FXML = "ChatBotPanel.fxml";

    private Logic logic;
    private ListElementPointer historySnapshot;
    private ObservableList<String> messagelist = FXCollections.observableArrayList();

    @FXML
    private ListView<ChatBotCard> chatBotListView;
    @FXML
    private Label username;
    @FXML
    private Label welcome;

    /**
     *  Creates the chatbot thread of messages
     */
    public ChatBotPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        initChatBot();
        registerAsAnEventHandler(this);
    }

    /**
     * Initiates the chatbot thread of messages with Jobbi's first message
     */
    public void initChatBot() {
        ObservableList<String> initialMessage = createInitialMessage(messagelist);
        ObservableList<ChatBotCard> initialMappedList = EasyBind.map(
                initialMessage, (msg) -> new ChatBotCard(msg, 0));

        // prevents user from selecting list cell
        chatBotListView.setSelectionModel(new DisableSelectionOfListCell<>());

        chatBotListView.setItems(initialMappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotListViewCell());
    }

    /**
     * Creates the first welcome message from Jobbi
     */
    public ObservableList<String> createInitialMessage(ObservableList<String> initialMessage) {
        initialMessage.add("Hello there, I am Jobbi! "
                + "I am here to help you find your ideal internship today. Type 'start' to begin your search.");
        return initialMessage;
    }

    /**
     *  Creates subsequent message thread between user and Jobbi
     */

    public void buildChat(ObservableList<String> listToBuild) {
        ObservableList<String> updatedMessages = addUserResponse(listToBuild);
        // if the user has not started conversation with jobbi using the `start` command
        if (updatedMessages.size() == (1)) {
            ObservableList<ChatBotCard> mappedList = EasyBind.map(
                    updatedMessages, (msg) -> new ChatBotCard(msg, 0));
            chatBotListView.setItems(mappedList);
            chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());

            // if the conversation has started between user and jobbi
        } else {
            AtomicInteger index = new AtomicInteger();
            ObservableList<ChatBotCard> mappedList = EasyBind.map(
                    updatedMessages, (msg) -> new ChatBotCard(msg, index.getAndIncrement()));
            chatBotListView.setItems(mappedList);
            chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
            chatBotListView.scrollTo(chatBotListView.getItems().size()-1);
        }
    }

    /**
     *  Adds subsequent messages from the user end into the message list
     */

    public ObservableList<String> addUserResponse(ObservableList<String> listToUpdate) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            listToUpdate.add(historySnapshot.current());
            if (historySnapshot.current().equals("new")) {
                listToUpdate.clear();
                initChatBot();
            }
        }
        return listToUpdate;
    }

    /**
     * Checks if the user has initiated conversation with Jobbi and adds Jobbi's response if he/she has.
     * @param currentMessageList
     * @param message
     * @return
     */
    public ObservableList<String> addJobbiResponse(ObservableList<String> currentMessageList, String message) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            currentMessageList.add(message);
            if (historySnapshot.current().equals("new")) {
                currentMessageList.clear();
                initChatBot();
            }
        }
        return currentMessageList;
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildChat(messagelist); // Adds to message thread whenever and whatever user types something in the command box
        addJobbiResponse(messagelist, event.message);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ChatBotCard}.
     */
    class ChatBotListViewCell extends ListCell<ChatBotCard> {
        @Override
        protected void updateItem(ChatBotCard message, boolean empty) {
            super.updateItem(message, empty);
            if (empty || message == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(message.getRoot());

            }
        }
    }
}
