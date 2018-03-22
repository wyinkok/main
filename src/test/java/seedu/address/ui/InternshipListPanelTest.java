package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InternshipCardHandle;
import guitests.guihandles.InternshipListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;

public class InternshipListPanelTest extends GuiUnitTest {
    private static final ObservableList<Person> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private InternshipListPanelHandle internshipListPanelHandle;

    @Before
    public void setUp() {
        InternshipListPanel personListPanel = new InternshipListPanel(TYPICAL_PERSONS);
        uiPartRule.setUiPart(personListPanel);

        internshipListPanelHandle = new InternshipListPanelHandle(getChildNode(personListPanel.getRoot(),
                InternshipListPanelHandle.INTERNSHIP_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            internshipListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            Person expectedInternship = TYPICAL_PERSONS.get(i);
            InternshipCardHandle actualCard = internshipListPanelHandle.getInternshipCardHandle(i);

            assertCardDisplaysPerson(expectedInternship, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        InternshipCardHandle expectedCard = internshipListPanelHandle
            .getInternshipCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        InternshipCardHandle selectedCard = internshipListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
