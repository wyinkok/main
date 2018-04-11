package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewChatCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.InternshipBuilder;
import seedu.address.testutil.InternshipUtil;

public class JobbiBotParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final InternshipBookParser parser = new InternshipBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Internship internship = new InternshipBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(InternshipUtil.getAddCommand(internship));
        assertEquals(new AddCommand(internship), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        try {
            parser.parseCommand(ClearCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_INTERNSHIP), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Internship internship = new InternshipBuilder().build();
        EditCommand.EditInternshipDescriptor descriptor = new EditInternshipDescriptorBuilder(internship).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_INTERNSHIP.getOneBased() + " " + InternshipUtil.getInternshipDetails(internship));
        assertEquals(new EditCommand(INDEX_FIRST_INTERNSHIP, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        try {
            parser.parseCommand(ExitCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new InternshipContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        try {
            parser.parseCommand(HelpCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        try {
            parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        try {
            parser.parseCommand(ListCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_INTERNSHIP), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        try {
            parser.parseCommand(RedoCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        try {
            parser.parseCommand(UndoCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_save() throws Exception {
        SaveCommand command = (SaveCommand) parser.parseCommand(
                SaveCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP.getOneBased());
        assertEquals(new SaveCommand(INDEX_FIRST_INTERNSHIP), command);
    }

    @Test
    public void parseCommand_start() throws Exception {
        assertTrue(parser.parseCommand(StartCommand.COMMAND_WORD) instanceof StartCommand);
        try {
            parser.parseCommand(StartCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_new() throws Exception {
        assertTrue(parser.parseCommand(NewChatCommand.COMMAND_WORD) instanceof NewChatCommand);
        try {
            parser.parseCommand(NewChatCommand.COMMAND_WORD + " 3");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(new String(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE)),
                    pe.getMessage());
        }
    }

    @Test
    public void parseCommand_unsave() throws Exception {
        UnsaveCommand command = (UnsaveCommand) parser.parseCommand(
                UnsaveCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP.getOneBased());
        assertEquals(new UnsaveCommand(INDEX_FIRST_INTERNSHIP), command);
    }

}

