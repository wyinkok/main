package seedu.address.logic.commands;

import seedu.address.model.internship.InternshipContainsAllKeywordsPredicate;

//@@author niloc94
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

    public static final String MESSAGE_FILTER_RESPONSE = "How would you to sort your results by? You may sort by "
            + "name industry role etc \nE.g sortby industry role industry";

    public static final String MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP = "No internships found ! You may want to try "
            + "using lesser keywords or change your keywords \n"
            + "E.g filter singapore ";

    private final InternshipContainsAllKeywordsPredicate predicate;

    public FilterCommand(InternshipContainsAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredInternshipList(predicate);
        return getCommandResult();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }

    /**
     * Helper method to retrieve the correct message for command results
     * @return
     */
    private CommandResult getCommandResult() {
        if (model.getFilteredInternshipList().size() > 1) {
            return new CommandResult(MESSAGE_FILTER_RESPONSE);
        } else {
            return new CommandResult(MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP);
        }
    }
}
