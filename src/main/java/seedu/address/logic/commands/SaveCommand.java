package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Saves personally curated internships into a separate collection to access it again later.
 */

public class SaveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves an internship to your Saved Collection "
            + "by the index number used in the last internship listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SAVED_INTERNSHIP_SUCCESS = "New internship saved: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "This internship already exists in the collection";
    public static final String MESSAGE_DUPLICATE_TAG = "This internship has been saved";

    public final String savedTagName = "saved";
    private final Index targetIndex;
    private Internship internshipWithSavedTag;
    private Internship internshipToSave;

    public SaveCommand(Index targetIndex) throws UniqueTagList.DuplicateTagException {

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(internshipToSave);
        try {
            model.updateInternship(internshipToSave, internshipWithSavedTag);
        } catch (DuplicateInternshipException e) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
        } catch (InternshipNotFoundException e) {
            throw new AssertionError("The target internship cannot be missing");
        }
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        return new CommandResult(String.format(MESSAGE_SAVED_INTERNSHIP_SUCCESS, internshipToSave));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Internship> lastShownList = model.getFilteredInternshipList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        internshipToSave = lastShownList.get(targetIndex.getZeroBased()); //add a tag to this internship!!
        internshipWithSavedTag = addSavedTagToInternship(internshipToSave);
    }

    /**
     * Adds a "saved" tag to the existing tags of an internship
     * @param internship
     * @return
     * @throws CommandException
     */
    private Internship addSavedTagToInternship(Internship internship) throws CommandException {
        final UniqueTagList personTags = new UniqueTagList(internshipToSave.getTags());
        try {
            personTags.add(new Tag(savedTagName));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        personTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
<<<<<<< HEAD
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                correctTagReferences);
=======
        return new Person(
                person.getName(), person.getSalary(), person.getEmail(), person.getAddress(), correctTagReferences);
>>>>>>> fdd0cc6349183cf8986b03133ee6918870419952
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveCommand // instanceof handles nulls
                && this.targetIndex.equals(((SaveCommand) other).targetIndex)); // state check
    }
}
