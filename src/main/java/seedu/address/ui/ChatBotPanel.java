//@@author wyinkok
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;


/**
 * Panel containing the message thread between the chatbot and user.
 */
public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);
    private static final String FXML = "ChatBotPanel.fxml";

    private Logic logic;
    private ListElementPointer historySnapshot;
    private ObservableList<String> messageList = FXCollections.observableArrayList();

    @FXML
    private ListView<ChatBotCard> chatBotListView;

    /**
     *  Creates the chatbot thread of messages
     */
    public ChatBotPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        initiateChatBot();
        registerAsAnEventHandler(this);
    }

    /**
     * Initiates the chatbot thread of messages with Jobbi's welcome message
     */
    public void initiateChatBot() {
        ObservableList<String> initialMessage = createInitialMessage(messageList);
        ObservableList<ChatBotCard> initialMappedList = EasyBind.map(
                initialMessage, (msg) -> new ChatBotCard(msg));

        // prevents user from selecting list cell
        chatBotListView.setSelectionModel(new DisableSelectionOfListCell<>());

        chatBotListView.setItems(initialMappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotListViewCell());
    }

    /**
     * Creates the first welcome message from Jobbi
     */
    public ObservableList<String> createInitialMessage(ObservableList<String> initialMessage) {
        initialMessage.add("JOBBI:   " + "Hello there, I am Jobbi! "
                + "I am here to help you find your ideal internship today. Type 'start' to begin your search.");
        return initialMessage;
    }

    /**
     *  Expands on the message thread between user and Jobbi
     */

    public void buildConversationWithUserResponse(ObservableList<String> listToBuild) {
        ObservableList<String> updatedMessages = handleUserResponse(listToBuild);
        ObservableList<ChatBotCard> mappedList = EasyBind.map(
                updatedMessages, (msg) -> new ChatBotCard(msg));
        chatBotListView.setItems(mappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
        chatBotListView.scrollTo(chatBotListView.getItems().size());
    }


    /**
     *  Checks if the user has initiated conversation with Jobbi and adds User's response if he/she has.
     */

    public ObservableList<String> handleUserResponse(ObservableList<String> listToUpdateWithUserResponse) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            listToUpdateWithUserResponse.add("USER:   " + historySnapshot.current());
            if (historySnapshot.current().equals("new")) {
                listToUpdateWithUserResponse.clear();
                initiateChatBot();
            }
        }
        return listToUpdateWithUserResponse;
    }

    /**
     * Checks if the user has initiated conversation with Jobbi and adds Jobbi's response if he/she has.
     * @param listToUpdateWithJobbiResponse
     * @param message
     * @return
     */
    public ObservableList<String> handleJobbiResponse(ObservableList<String> listToUpdateWithJobbiResponse,
                                                      String message) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            listToUpdateWithJobbiResponse.add("JOBBI:  " + message);
            if (historySnapshot.current().equals("new")) {
                listToUpdateWithJobbiResponse.clear();
                initiateChatBot();
            }
        }
        return listToUpdateWithJobbiResponse;
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildConversationWithUserResponse(messageList);
        handleJobbiResponse(messageList, event.message);
    }

    //@@author
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ChatBotCard}.
     */
    class ChatBotListViewCell extends ListCell<ChatBotCard> {
        @Override
        protected void updateItem(ChatBotCard message, boolean isEmpty) {
            super.updateItem(message, isEmpty);
            if (isEmpty || message == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(message.getRoot());
            }
        }
    }
}
