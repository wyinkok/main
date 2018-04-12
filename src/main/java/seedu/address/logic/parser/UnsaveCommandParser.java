//@@author wyinkok
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnsaveCommand object
 */
public class UnsaveCommandParser implements Parser<UnsaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnsaveCommand
     * and returns an UnsaveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public UnsaveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnsaveCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnsaveCommand.MESSAGE_USAGE));
        }
    }
}
