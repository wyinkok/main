//@@author wyinkok
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;

import org.junit.Test;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SaveCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the SaveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SaveCommandParserTest {

    private SaveCommandParser parser = new SaveCommandParser();

    @Test
    public void parse_validArgs_returnsSaveCommand() {
        assertParseSuccess(parser, "1", new SaveCommand(INDEX_FIRST_INTERNSHIP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_nonAlphanumeric_throwsParseException() {
        assertParseFailure(parser, "!", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }
}
