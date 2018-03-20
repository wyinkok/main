package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.InternshipCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class InternshipPanelSelectionChangedEvent extends BaseEvent {


    private final InternshipCard newSelection;

    public InternshipPanelSelectionChangedEvent(InternshipCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public InternshipCard getNewSelection() {
        return newSelection;
    }
}
