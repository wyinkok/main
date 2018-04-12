package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowGlossaryRequestEvent;

//@@author wyinkok-unused
/**
 * Displays a full glossary of potential jobs, industries and other related information a user might consider.
 */
public class GlossaryCommand extends Command {

    public static final String COMMAND_WORD = "glossary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows user a list of potential jobs, industries, skills and other related information to consider.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_GLOSSARY_MESSAGE = "Opened glossary window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowGlossaryRequestEvent());
        return new CommandResult(SHOWING_GLOSSARY_MESSAGE);
    }
}
