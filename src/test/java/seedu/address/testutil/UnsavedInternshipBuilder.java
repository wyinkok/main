//@@author wyinkok
package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
/**
 * A utility class to help with building unsaved internship objects.
 */
public class UnsavedInternshipBuilder {


    private static final String MESSAGE_DUPLICATE_REMOVAL = "This internship has been removed from Saved Collection";
    private static final String SAVED_TAG_NAME = "saved";

    /**
     * Initializes the UnsavedInternshipBuilder with the data of {@code internshipToRemoveSavedTag}.
     *
     * @param internshipToRemoveSavedTag
     */
    public Internship removeTag(Internship internshipToRemoveSavedTag) throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internshipToRemoveSavedTag.getTags());
        try {
            internshipTags.delete(new Tag(savedTagName));
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMOVAL);
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internshipToRemoveSavedTag.getName(),
                internshipToRemoveSavedTag.getSalary(),
                internshipToRemoveSavedTag.getEmail(),
                internshipToRemoveSavedTag.getAddress(),
                internshipToRemoveSavedTag.getIndustry(),
                internshipToRemoveSavedTag.getRegion(),
                internshipToRemoveSavedTag.getRole(),
                correctTagReferences);
    }
}
