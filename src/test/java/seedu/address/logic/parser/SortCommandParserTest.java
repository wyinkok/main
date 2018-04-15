package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        SortCommand expectedSortCommand =
                new SortCommand(Arrays.asList("role", "industry", "salary"));
        assertParseSuccess(parser, " role industry salary", expectedSortCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n role \n \t industry  \n \t salary", expectedSortCommand);

        // valid negative argument words
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "-name", "-salary"));
        assertParseSuccess(parser, " -role -name -salary", expectedSortCommand);

        // valid negative and non negative argument words
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "region", "-salary"));
        assertParseSuccess(parser, " -role region -salary", expectedSortCommand);

        // valid repeat
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "-role", "-role"));
        assertParseSuccess(parser, " -role -role -role", expectedSortCommand);

        // valid three arguments with 1 repeat
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "industry", "-role"));
        assertParseSuccess(parser, " -role industry -role", expectedSortCommand);

        // valid one argument
        expectedSortCommand = new SortCommand(Arrays.asList("-role"));
        assertParseSuccess(parser, " -role", expectedSortCommand);

        // valid two argument
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "name"));
        assertParseSuccess(parser, " -role name", expectedSortCommand);

        // valid >3 argument
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "name", "salary", "role"));
        assertParseSuccess(parser, " -role name salary role", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // 1 Invalid Argument
        assertParseFailure(parser, " invalid", SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        // Mix of valid and invalid arguments
        assertParseFailure(parser, " invalid role name",
                SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        // Invalid mix of valid and invalid arguments with '-'
        assertParseFailure(parser, " name - role name",
                SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);
    }
}
