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

    private ObservableList<String> list;

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
     * Creates the first welcome message from Jobbi
     */
    public ObservableList<String> initMessageList(ObservableList<String> initialMessageList) {
        initialMessageList.add("Hello there, I am Jobbi! "
                + "I am here to help you find your ideal internship today. Type 'start' to begin your search.");
        return initialMessageList;
    }

    /**
     * Initiates the chatbot thread of messages with Jobbi's first message
     */
    public void initChatBot() {
        ObservableList<String> initialMessageThread = initMessageList(messagelist);
        ObservableList<ChatBotCard> initialMappedList = EasyBind.map(
                initialMessageThread, (msg) -> new ChatBotCard(msg, 0));
        chatBotListView.setSelectionModel(new DisableSelectionOfListCell<>()); // prevent user from selecting list cell
        chatBotListView.setItems(initialMappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }

    /**
     *  Adds subsequent messages from the user end into the message list - Currently have not implemented Jobbi's reply
     */

    public ObservableList<String> addToMessageThread(ObservableList<String> listToUpdate) {
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

    public ObservableList<String> hasConversationStarted(ObservableList<String> currentMessageList, String message) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            currentMessageList.add(message);
            if (historySnapshot.current().equals("new")){
                currentMessageList.clear();
                initChatBot();
            }

        }
        return currentMessageList;
    }


    /**
     *  Creates subsequent messages from the user end - Currently have not implemented Jobbi's reply
     */

    public void buildChat(ObservableList<String> listToBuild) {
        ObservableList<String> updatedMessageList = addToMessageThread(listToBuild);
        if (updatedMessageList.size() == (1)) {
            ObservableList<ChatBotCard> mappedList = EasyBind.map(
                    updatedMessageList, (msg) -> new ChatBotCard(msg, 0));
            chatBotListView.setItems(mappedList);
            chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
        } else {
            AtomicInteger index = new AtomicInteger();
            ObservableList<ChatBotCard> mappedList = EasyBind.map(
                    updatedMessageList, (msg) -> new ChatBotCard(msg, index.getAndIncrement()));
            chatBotListView.setItems(mappedList);
            chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
        }
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildChat(messagelist); // Adds to message thread whenever and whatever user types something in the command box
        hasConversationStarted(messagelist, event.message);
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
