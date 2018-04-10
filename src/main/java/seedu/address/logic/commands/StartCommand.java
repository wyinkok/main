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
            + " that you are interested in.  To view the full list of possible industries and roles "
            + "key in 'help' \n\nE.g  search healthcare technology dataanalytics humanresource ";
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new StartAppRequestEvent());
        return new CommandResult(MESSAGE_START_ACKNOWLEDGEMENT);
    }

}
