package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Undo saved internships into a separate collection.
 */

public class UnsaveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unsave";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a saved internship to your Saved Collection "
            + "by the index number used in the last internship listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNSAVED_INTERNSHIP_SUCCESS =
            "New internship removed from Saved Collection: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "This internship already removed from the collection";


    public final String savedTagName = "saved";
    private final Index targetIndex;
    private Person internshipWithoutSavedTag;
    private Person internshipToUnsave;

    public UnsaveCommand(Index targetIndex) {

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(internshipToUnsave);
        try {
            model.updatePerson(internshipToUnsave, internshipWithoutSavedTag);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
        } catch (PersonNotFoundException e) {
            throw new AssertionError("The target internship cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, internshipToUnsave));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        internshipToUnsave = lastShownList.get(targetIndex.getZeroBased());
        internshipWithoutSavedTag = removeSavedTagToInternship(internshipToUnsave);
    }

    /**
     * Removes a "saved" tag to the existing tags of an internship
     * @param person
     * @return
     * @throws CommandException
     */
    private Person removeSavedTagToInternship(Person person) {
        final UniqueTagList personTags = new UniqueTagList(internshipToUnsave.getTags());
        personTags.delete(new Tag(savedTagName));

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        personTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), correctTagReferences);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnsaveCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnsaveCommand) other).targetIndex)); // state check
    }
}
