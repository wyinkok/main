//@@author wyinkok
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a chatbot card in the chat bot message panel.
 */
public class ChatBotCardHandle extends NodeHandle<Node> {
    private static final String MESSAGES_FIELD_ID = "#messages";

    private final Label messagesLabel;

    public ChatBotCardHandle(Node cardNode) {
        super(cardNode);

        this.messagesLabel = getChildNode(MESSAGES_FIELD_ID);
    }
    public String getMessages() {
        return messagesLabel.getText();
    }

}

