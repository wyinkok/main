package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.StartCommand.MESSAGE_START_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.StartAppRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StartCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_start_success() {
        CommandResult result = new StartCommand().execute();
        assertEquals(MESSAGE_START_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof StartAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
