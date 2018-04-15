package seedu.address.logic.commands;

import seedu.address.model.internship.InternshipContainsKeywordsPredicate;

/**
 * Finds and lists all Internships in address book whose name, address, salary, email or industry contains any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all internships whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Marketing Analytics";

    public static final String MESSAGE_SEARCH_RESPONSE = "Awesome, would you like to narrow down your search even "
            + ("more? You may like to filter by region and salary. If not, you may go on to sort your results "
            + "by typing 'sort [KEYWORD]' \n\nE.g  filter Central 800. ");

    public static final String MESSAGE_SEARCH_RESPONSE_NO_INTERNSHIPS = "Woops, no internship found ! "
            + "Try using lesser keywords or entering other keywords  \nE.g: find finance";

    private final InternshipContainsKeywordsPredicate predicate;

    public FindCommand(InternshipContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author TanCiKang
    @Override
    public CommandResult execute() {
        model.removeTagsFromAllInternshipList();
        model.updateSearchedInternshipList(predicate);
        model.addTagsToFilteredList();
        return getCommandResult();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

    /**
     * Helper method to retrieve the correct message for command results
     * @return
     */
    private CommandResult getCommandResult() {
        if (model.getFilteredInternshipList().size() > 0) {
            return new CommandResult(MESSAGE_SEARCH_RESPONSE);
        } else {
            return new CommandResult(MESSAGE_SEARCH_RESPONSE_NO_INTERNSHIPS);
        }
    }
}
