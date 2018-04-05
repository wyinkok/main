package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.internship.exceptions.SavedTagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Undo all saved internships from the Saved collection.
 */

public class UnsaveallCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unsaveall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a saved internship to your Saved Collection "
            + "by the index number used in the last internship listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNSAVED_INTERNSHIP_SUCCESS =
            "All internships removed from Saved Collection";
    public static final String MESSAGE_DUPLICATE_REMOVAL = "These internships have been removed from the collection already";


    public final String savedTagName = "saved";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        removeSavedTagFromAllInternships(model.getFilteredInternshipList());
        return new CommandResult(String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS));
    }

    /**
     * Removes a "saved" tag from the existing tags of all the filtered internship
     * @param filteredInternships
     * @return
     * @throws CommandException
     */
    private ObservableList<Internship> removeSavedTagFromAllInternships(ObservableList<Internship> filteredInternships)
            throws CommandException {
        for (Internship internshipToUnsave : filteredInternships) {
            try {
                requireNonNull(internshipToUnsave);
                model.updateInternship(internshipToUnsave, removeSavedTagToInternship(internshipToUnsave));
            } catch (DuplicateInternshipException e) {
                throw new CommandException(MESSAGE_DUPLICATE_REMOVAL);
            } catch (InternshipNotFoundException e) {
                throw new AssertionError("The target internship cannot be missing");
            }
        }
        return filteredInternships;
    }

    /**
     * Removes a "saved" tag to the existing tags of an internship
     * @param internship
     * @return
     * @throws CommandException
     */
    private Internship removeSavedTagToInternship(Internship internship) throws CommandException {
        final UniqueTagList personTags = new UniqueTagList(internship.getTags());
        try {
            personTags.delete(new Tag(savedTagName));
        } catch (SavedTagNotFoundException e) {
        }

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        personTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(),
                internship.getAddress(), internship.getIndustry(), internship.getLocation(), internship.getRole(),
                correctTagReferences);
    }


}
