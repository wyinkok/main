package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysInternship;

import org.junit.Test;

import guitests.guihandles.InternshipCardHandle;
import seedu.address.model.internship.Internship;
import seedu.address.testutil.InternshipBuilder;

public class InternshipCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Internship internshipWithNoTags = new InternshipBuilder().withTags(new String[0]).build();
        InternshipCard internshipCard = new InternshipCard(internshipWithNoTags, 1);
        uiPartRule.setUiPart(internshipCard);
        assertCardDisplay(internshipCard, internshipWithNoTags, 1);

        // with tags
        Internship internshipWithTags = new InternshipBuilder().build();
        internshipCard = new InternshipCard(internshipWithTags, 2);
        uiPartRule.setUiPart(internshipCard);
        assertCardDisplay(internshipCard, internshipWithTags, 2);
    }

    @Test
    public void equals() {
        Internship internship = new InternshipBuilder().build();
        InternshipCard personCard = new InternshipCard(internship, 0);

        // same internship, same index -> returns true
        InternshipCard copy = new InternshipCard(internship, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different internship, same index -> returns false
        Internship differentInternship = new InternshipBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new InternshipCard(differentInternship, 0)));

        // same internship, different index -> returns false
        assertFalse(personCard.equals(new InternshipCard(internship, 1)));
    }

    /**
     * Asserts that {@code internshipCard} displays the details of {@code expectedInternship} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(InternshipCard personCard, Internship expectedInternship, int expectedId) {
        guiRobot.pauseForHuman();

        InternshipCardHandle internshipCardHandle = new InternshipCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", internshipCardHandle.getId());

        // verify internship details are displayed correctly
        assertCardDisplaysInternship(expectedInternship, internshipCardHandle);
    }
}
