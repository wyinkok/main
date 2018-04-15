package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RESTART_COMMAND;
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
    private static boolean hasRestarted = false;
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
            checkIfConversationRestarted();
            return new SelectCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new FindCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new FilterCommandParser().parse(arguments);

        //@@author wyinkok
        case SaveCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new SaveCommandParser().parse(arguments);

        case UnsaveCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new UnsaveCommandParser().parse(arguments);

        //@@author niloc94
        case SortCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new SortCommandParser().parse(arguments);

        //=========== Command without arguments =============================================================

        //@@author
        case ListCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new RedoCommand();

        //@@author wyinkok
        case StartCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            hasRestarted = false;
            checkIfConversationRestarted();
        case NewChatCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            hasStarted = false;
            if (!hasRestarted) {
                hasRestarted = true;
                return new NewChatCommand();
            } else {
                throw new AssertionError("Conversation should only restart after Start Command is "
                        + "entered again");
            }

        //@@author
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    public void resetHasStarted() {
        hasStarted = false;
    }

    public boolean getHasStarted() {
        return hasStarted;
    }

    //@@author wyinkok
    /**
     * Checks if the user has typed in the start command after the new command to restart the conversation successfully
     * @throws ParseException if any other command is typed in after the new command
     */
    private void checkIfConversationRestarted() throws ParseException {
        if (hasRestarted) {
            throw new ParseException(MESSAGE_INVALID_RESTART_COMMAND);
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
