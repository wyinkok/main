package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyJobbiBot;

/** Indicates the JobbiBot in the model has changed*/
public class JobbiBotChangedEvent extends BaseEvent {

    public final ReadOnlyJobbiBot data;

    public JobbiBotChangedEvent(ReadOnlyJobbiBot data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of internships " + data.getInternshipList().size() + ", number of tags "
                + data.getTagList().size();
    }
}
