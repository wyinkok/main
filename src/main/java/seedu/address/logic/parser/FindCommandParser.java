package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        Pattern pattern = Pattern.compile("\"(?:|[^\"])*\"|\\S+");
        Matcher matcher = pattern.matcher(trimmedArgs);
        ArrayList nameKeywords = new ArrayList<String>();
        while (matcher.find()){
            System.out.println(matcher.group().replaceAll("\"",""));
            nameKeywords.add(matcher.group().replaceAll("\"",""));
            System.out.println(matcher.group());
        }

        ModelManager.setKeywords(nameKeywords);
        return new FindCommand(new InternshipContainsKeywordsPredicate((nameKeywords)));
    }
}
