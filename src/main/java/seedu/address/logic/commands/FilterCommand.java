package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsAllKeywordsPredicate;


/**
 * Filter the lists all internships in the internship according to all keyword arguments
 * Keyword matching is case insensitive
 *
 */

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all internship from the list which contain the"
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "Marketing";

    private final PersonContainsAllKeywordsPredicate predicate;

    public FilterCommand(PersonContainsAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredInternshipList(predicate);
        return new CommandResult(getMessageForInternshipListShownSummary(model.getFilteredInternshipList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
