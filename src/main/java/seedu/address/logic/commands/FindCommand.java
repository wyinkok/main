package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;

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
            ModelManager.removeTagsFromInternshipList(model.getFilteredInternshipList(), model);
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
