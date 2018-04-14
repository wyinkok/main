package seedu.address.logic.commands;

import seedu.address.model.ModelManager;
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
            + "Example: " + COMMAND_WORD + " Marketing Analytics Singapore";

    public static final String MESSAGE_SEARCH_RESPONSE = "Awesome, would you like to narrow down your search even "
            + "more? You may filter by region and specific address \n\nE.g  filter singapore hongkong tanjong pagar";

    public static final String MESSAGE_SEARCH_RESPONSE_NO_INTERNSHIPS = "Woops, no internship found ! "
            + "Try using lesser keywords or entering other keywords  \nE.g: search marketing business";

    private final InternshipContainsKeywordsPredicate predicate;

    public FindCommand(InternshipContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author TanCiKang
    @Override
    public CommandResult execute() {

        ModelManager.removeTagsFromAllInternshipList(model);
        model.updateSearchedInternshipList(predicate);
        // add tags that have keywords matching the internship
        ModelManager.addTagsToFilteredList(ModelManager.getKeywords(), model.getFilteredInternshipList(), model);

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
