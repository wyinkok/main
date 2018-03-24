package seedu.address.ui;

import static org.fxmisc.easybind.EasyBind.map;

import java.util.Observable;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import seedu.address.model.MessagesList;

public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);


    private List<Label> messages = new ArrayList<>();
    private ScrollPane container = new ScrollPane();
    private int index = 0;

    private static final String FXML = "ChatBotPanel.fxml";

    //@FXML VBox messageList;

    @FXML
    private ListView<ChatBotCard> chatBotListView;

    @FXML private Label username;
    @FXML private Label welcome;




    public ChatBotPanel() {
        super(FXML);
        initChatBot();
        registerAsAnEventHandler(this);
    }


    public void initChatBot() {
        ObservableList<String> messageList = addToList();
        ObservableList<ChatBotCard> mappedList = EasyBind.map(
                messageList, (msg) -> new ChatBotCard(msg));
        chatBotListView.setItems(mappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }

    public ObservableList<String> addToList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("I am Jobbi");
        list.add("Nice to meet you");
        list.add("Yay nice to see u");
        return list;
    }

    /**
     * Scrolls to the {@code InternshipCard} at the {@code index} and selects it.
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InternshipCard}.
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

   /*  public void alignMessages() {

        // Temporarily add the same message first
        messages.add(new Label("I'm a message"));
        if (index % 2 == 0) {

            messages.get(index).setAlignment(Pos.TOP_LEFT);
            System.out.println("1");

        } else {

            messages.get(index).setAlignment(Pos.CENTER_RIGHT);
            System.out.println("2");

        }

        messageList.getChildren().add(messages.get(index));
        index++;

    } */





