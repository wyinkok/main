package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class UnsavedPersonBuilder {


    public static final String MESSAGE_DUPLICATE_TAG = "This internship has been removed from Saved Collection";
    public final String savedTagName = "saved";

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public Person removeTag(Person personToCopy) throws CommandException {
        final UniqueTagList personTags = new UniqueTagList(personToCopy.getTags());
            personTags.delete(new Tag(savedTagName));

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        personTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Person(
                personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                correctTagReferences);
    }
}
