package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.getTypicalInternships;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysInternship;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InternshipCardHandle;
import guitests.guihandles.InternshipListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.internship.Internship;

public class InternshipListPanelTest extends GuiUnitTest {
    private static final ObservableList<Internship> TYPICAL_INTERNSHIPS =
            FXCollections.observableList(getTypicalInternships());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRequestEvent(INDEX_SECOND_INTERNSHIP);

    private InternshipListPanelHandle internshipListPanelHandle;

    @Before
    public void setUp() {
        InternshipListPanel personListPanel = new InternshipListPanel(TYPICAL_INTERNSHIPS);
        uiPartRule.setUiPart(personListPanel);

        internshipListPanelHandle = new InternshipListPanelHandle(getChildNode(personListPanel.getRoot(),
                InternshipListPanelHandle.INTERNSHIP_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_INTERNSHIPS.size(); i++) {
            internshipListPanelHandle.navigateToCard(TYPICAL_INTERNSHIPS.get(i));
            Internship expectedInternship = TYPICAL_INTERNSHIPS.get(i);
            InternshipCardHandle actualCard = internshipListPanelHandle.getInternshipCardHandle(i);

            assertCardDisplaysInternship(expectedInternship, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        InternshipCardHandle expectedCard = internshipListPanelHandle
            .getInternshipCardHandle(INDEX_SECOND_INTERNSHIP.getZeroBased());
        InternshipCardHandle selectedCard = internshipListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
