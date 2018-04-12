package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewChatCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class InternshipBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static boolean hasStarted = false;

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        //@@author wyinkok
        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case UnsaveCommand.COMMAND_WORD:
            return new UnsaveCommandParser().parse(arguments);

        //@@author niloc94
        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        //=========== Command without arguments =============================================================

        case ListCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new RedoCommand();

        case StartCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            if (!hasStarted) {
                hasStarted = true;
                return new StartCommand();
            } else {
                throw new ParseException("Our conversation has already started"
                        + "\nType 'new' to restart our conversation");
            }

        //@@author wyinkok
        case NewChatCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new NewChatCommand();

        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    //@@author niloc94
    /**
     *  Helper method to check if commands without arguments have arguments added to it
     * @throws ParseException
     */
    private void checkIfContainArguments(String arguments) throws ParseException {
        if (!arguments.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }
}
