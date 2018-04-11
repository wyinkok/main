package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.internship.Internship;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an internship list
 */
public interface ReadOnlyJobbiBot {

    /**
     * Returns an unmodifiable view of the internships list.
     * This list will not contain any duplicate internships.
     */
    ObservableList<Internship> getInternshipList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
