package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.internship.Internship.SORTABLE_ATTRIBUTES_LIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author niloc94
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String MESSAGE_INVALID_SORT_ATTRIBUTE = "Invalid attributes given! Possible attributes are : "
            + SORTABLE_ATTRIBUTES_LIST;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        List<String> keywords = new ArrayList<>(Arrays.asList(nameKeywords));

        // Checks if keywords are proper internship attributes
        if (!keywords.stream().allMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(SORTABLE_ATTRIBUTES_LIST, keyword))) {
            throw new ParseException(MESSAGE_INVALID_SORT_ATTRIBUTE);
        }

        return new SortCommand(keywords);
    }
}
