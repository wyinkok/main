package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.internship.Internship;

/**
 * An UI component that displays information of a {@code Internship}.
 */
public class ChatBotCard extends UiPart<Region> {

    private static final String FXML = "ChatBotCard.fxml";

    public final String msg;

    @FXML
    private HBox messagePane;
    @FXML
    private Label username;
    @FXML
    private Label message;

    public ChatBotCard(String msg) {
        super(FXML);
        this.msg = msg;
        username.setText("Jobbi: ");
        message.setText("Hi I am Jobbi");
    }

   /*  @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InternshipCard)) {
            return false;
        }

        // state check
        String card = (String) other;
        return username.getText().equals(card.username.getText())
                && msg.equals(card.msg);
    } */
}
