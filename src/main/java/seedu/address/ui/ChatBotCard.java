//@@author wyinkok
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * An UI component that displays information of a chat message.
 */
public class ChatBotCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ChatBotCard.class);
    private static final String FXML = "ChatBotCard.fxml";

    @FXML
    private HBox messagePane;
    @FXML
    private Label username;
    @FXML
    private Label messages;


    public ChatBotCard(String msg, int index) {
        super(FXML);
        setMessage(msg, index);
        registerAsAnEventHandler(this);
    }

    /**
     * Displays messages alternating between Jobbi and User
     * @param msg
     * @param index
     */
    public void setMessage(String msg, int index) {
        if (index % 2 == 0) {
            username.setText("Jobbi: ");
            messages.setText(msg);
        } else {
            username.setText("User: ");
            messages.setText(msg);
        }
    }
}
