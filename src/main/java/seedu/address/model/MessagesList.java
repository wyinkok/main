package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class MessagesList {

    private final ObservableList<String> internalMessageList = FXCollections.observableArrayList();

    /**
     * Constructs empty MessagesList.
     */
    public MessagesList() {}

    /**
     * Adds a Tag to the list.
     *
     */

    public void add(String toAdd)  {
        requireNonNull(toAdd);
        internalMessageList.add(toAdd);
    }
}
