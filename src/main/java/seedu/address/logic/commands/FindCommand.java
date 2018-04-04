package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;
import seedu.address.model.internship.UniqueInternshipList;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_INTERNSHIP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

/**
 * Finds and lists all Internships in address book whose name, address, salary, email or industry contains any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String ALTERNATIVE_COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all internships whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final InternshipContainsKeywordsPredicate predicate;

    public FindCommand(InternshipContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {

// remove all tags from filtered list except 'saved' tags
    try {
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        ModelManager.removeTagsFromFilteredList(model.getFilteredInternshipList(), model);
    } catch (CommandException e) {
            e.printStackTrace();
        }

        model.updateFilteredInternshipList(predicate);

        // add tags that have keywords matching the internship
        try {
            ModelManager.addTagsToFilteredList(ModelManager.getKeywords(), model.getFilteredInternshipList(), model);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return new CommandResult(getMessageForInternshipListShownSummary(model.getFilteredInternshipList().size()));
        }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
