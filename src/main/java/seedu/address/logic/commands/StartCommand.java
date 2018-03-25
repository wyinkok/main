package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.StartAppRequestEvent;

/**
 * Starts the conversation with Jobbi.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_START_ACKNOWLEDGEMENT = "Started conversation with Jobbi";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new StartAppRequestEvent());
        return new CommandResult(MESSAGE_START_ACKNOWLEDGEMENT);
    }

}
