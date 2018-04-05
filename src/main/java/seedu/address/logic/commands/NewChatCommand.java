//@@author wyinkok
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.StartAppRequestEvent;

/**
 * Restarts the conversation with Jobbi.
 */
public class NewChatCommand extends Command {

    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_RESTART_ACKNOWLEDGEMENT = "Restarted your conversation with Jobbi";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new StartAppRequestEvent());
        return new CommandResult(MESSAGE_RESTART_ACKNOWLEDGEMENT);
    }

}
