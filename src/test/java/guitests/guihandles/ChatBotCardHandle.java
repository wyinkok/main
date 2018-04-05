//@@author wyinkok
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a chatbot card in the chat bot panel.
 */

public class ChatBotCardHandle extends NodeHandle<Node> {
    private static final String USERNAME_FIELD_ID = "#username";
    private static final String MESSAGES_FIELD_ID = "#messages";

    private final Label usernameLabel;
    private final Label messagesLabel;

    public ChatBotCardHandle(Node cardNode) {
        super(cardNode);

        this.usernameLabel = getChildNode(USERNAME_FIELD_ID);
        this.messagesLabel = getChildNode(MESSAGES_FIELD_ID);
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    public String getMessages() {
        return messagesLabel.getText();
    }

}

