package seedu.address.logic.commands;

import java.util.List;

//@@author niloc94
/**
 * Sort and lists all Internships in the Internship List according to the order of the keywords given
 * Keyword matching is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String SORT_SUCCESSS_MESSAGE = "Sort success! Here is your ideal list of internships. \n\n"
            + "If you would like to search, filter or sort again, "
            + "key in the respective command word(s) and the new thing(s) you would like to search/filter/sort. "
            + "Please note that redoing the above commands will clear your existing search.";

    public static final String NOTHING_TO_SORT_MESSAGE = "No internships to sort! ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all internships according "
            + "to the argument(s) given and displays them as a list with index numbers.\n"
            + "Maximum of 3 arguments will be sorted"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Industry Role Region";

    private final List<String> keywords;

    public SortCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.setComparator(keywords);
        return getCommandResult();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand); // instanceof handles nulls
    }

    /**
     * Helper method to retrieve the correct message for command results
     * @return
     */
    private CommandResult getCommandResult() {
        if (model.getFilteredInternshipList().size() > 1) {
            return new CommandResult(SORT_SUCCESSS_MESSAGE);
        } else {
            return new CommandResult(NOTHING_TO_SORT_MESSAGE);
        }
    }
}
