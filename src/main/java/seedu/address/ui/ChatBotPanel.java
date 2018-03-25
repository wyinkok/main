package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;

/**
 * Panel containing the message thread between chatbot and user.
 */

public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);
    private static final String FXML = "ChatBotPanel.fxml";

    private Logic logic;
    private ListElementPointer historySnapshot;
    private ObservableList<String> messagelist = FXCollections.observableArrayList();
    private final StringProperty displayed = new SimpleStringProperty();

    private int index = 1;

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
     * Creates the first welcome message from Jobbi
     */
    public ObservableList<String> initMessageList(ObservableList<String> initialMessageList) {
        initialMessageList.add("Hello, I am Jobbi! "
                + "I am here to help you find your ideal internship today, How can I help you?");
        return initialMessageList;
    }

    /**
     * Initiates the chatbot thread of messages with Jobbi's first message
     */
    public void initChatBot() {
        ObservableList<String> initialMessageList = initMessageList(messagelist);
        ObservableList<ChatBotCard> initialMappedList = EasyBind.map(
                initialMessageList, (msg) -> new ChatBotCard(msg, index));
        chatBotListView.setSelectionModel(new DisableSelectionOfListCell<>()); // prevent user from selecting list cell
        chatBotListView.setItems(initialMappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }

    /**
     *  Adds subsequent messages from the user end into the message list - Currently have not implemented Jobbi's reply
     */

    public ObservableList<String> addToMessageList(ObservableList<String> listToUpdate) {
        historySnapshot = logic.getHistorySnapshot();
        listToUpdate.add(historySnapshot.current());
        return listToUpdate;
    }

    /**
     *  Creates subsequent messages from the user end - Currently have not implemented Jobbi's reply
     */

    public void buildChat(ObservableList<String> listToBuild) {
        ObservableList<String> updatedMessageList = addToMessageList(listToBuild);
        ObservableList<ChatBotCard> mappedList = EasyBind.map(
                updatedMessageList, (msg) -> new ChatBotCard(msg, index++));
        chatBotListView.setItems(mappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }


    /**
     * Scrolls to the {@code ChatBotCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            chatBotListView.scrollTo(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildChat(messagelist); // Adds to message thread whenever and whatever user types something in the command box
        Platform.runLater(() -> displayed.setValue((event.message)));
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






