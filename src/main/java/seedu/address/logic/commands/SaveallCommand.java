//@@author wyinkok
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Saves all personally curated internships into a separate collection to access it again later.
 */

public class SaveallCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "saveall";

    public static final String MESSAGE_SAVED_ALL_INTERNSHIP_SUCCESS = "All internship saved";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "These internships already exists in the collection";

    public final String savedTagName = "saved";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        addSavedTagToAllInternships(model.getFilteredInternshipList());
        return new CommandResult(String.format(MESSAGE_SAVED_ALL_INTERNSHIP_SUCCESS));
    }

    /**
     * Adds a "saved" tag to the existing tags of all internships in a filtered list
     * @param filteredInternships
     * @return
     * @throws CommandException
     */
    private ObservableList<Internship> addSavedTagToAllInternships(ObservableList<Internship> filteredInternships)
            throws CommandException {
        for (Internship internshipToSave : filteredInternships) {
            try {
                requireNonNull(internshipToSave);
                model.updateInternship(internshipToSave, addSavedTagToInternship(internshipToSave));
            } catch (DuplicateInternshipException e) {
                throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
            } catch (InternshipNotFoundException e) {
                throw new AssertionError("The target internship cannot be missing");
            }
        }
        return filteredInternships;
    }


    /**
     * Adds a "saved" tag to the existing tags of a single internship
     * @param internship
     * @return
     * @throws CommandException
     */
    private Internship addSavedTagToInternship(Internship internship) {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());
        try {
            internshipTags.add(new Tag(savedTagName));
        } catch (UniqueTagList.DuplicateTagException e) {
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                internship.getIndustry(), internship.getLocation(), internship.getRole(), correctTagReferences);
    }

}
