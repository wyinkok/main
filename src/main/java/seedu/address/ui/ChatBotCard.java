package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;

/**
 * An UI component that displays information of a {@code Internship}.
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

    private final StringProperty displayed = new SimpleStringProperty();


    public ChatBotCard(String msg, int index) {
        super(FXML);
        setMessage(msg, index);
        registerAsAnEventHandler(this);
    }


    public void setMessage(String msg, int index) {
        if (index % 2 == 0) {
            username.setText("User: ");
            messages.setText(msg); // Display user input into the command box (DONE!)
        } else {
            username.setText("Jobbi: ");
            messages.setText(msg); // Display results of each command + prompts
        }
    }



        @Subscribe
        private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            Platform.runLater(() -> displayed.setValue((event.message)));
        }

}
