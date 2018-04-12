//@@author wyinkok
package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A utility class to help with building saved Internship objects.
 */
public class SavedInternshipBuilder {

    private static final String MESSAGE_DUPLICATE_TAG = "This internship has been saved";
    private static final String SAVED_TAG_NAME = "saved";

    /**
     * Initializes the SavedInternshipBuilder with the data of {@code internshipToAddSavedTag}.
     */
    public Internship addTag(Internship internshipToAddSavedTag) throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internshipToAddSavedTag.getTags());
        try {
            internshipTags.add(new Tag(SAVED_TAG_NAME));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }
        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of intrenship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internshipToAddSavedTag.getName(),
                internshipToAddSavedTag.getSalary(),
                internshipToAddSavedTag.getEmail(),
                internshipToAddSavedTag.getAddress(),
                internshipToAddSavedTag.getIndustry(),
                internshipToAddSavedTag.getRegion(),
                internshipToAddSavedTag.getRole(),
                correctTagReferences);
    }

}
