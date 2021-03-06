//@@author wyinkok
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.StartAppRequestEvent;

/**
 * Starts the conversation with Jobbi.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_START_ACKNOWLEDGEMENT = "Next, please key in all the industries and roles"
            + " that you are interested in.  To view a suggested list of possible industries and roles, "
            + "type 'help'. \n\nE.g  find finance technology marketing consulting";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new StartAppRequestEvent());
        return new CommandResult(MESSAGE_START_ACKNOWLEDGEMENT);
    }
}
