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

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import seedu.address.model.MessagesList;

public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);


    private List<Label> messages = new ArrayList<>();
    private ScrollPane container = new ScrollPane();
    private int index = 0;

    private static final String FXML = "ChatBotPanel.fxml";

   //  @FXML VBox messageList;

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
        container.setPrefSize(200, 400);
        //container.setContent((Node) messages);
        username.setText("Jobbi: ");
        welcome.setText("Hi I am Jobbi. How can I help you find your idela internship today?");
        messages.add(new Label("I'm a message"));
       // alignMessages();
        //index ++;
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


}


