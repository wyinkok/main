package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.InternshipCardHandle;
import seedu.address.model.internship.Internship;
import seedu.address.testutil.PersonBuilder;

public class InternshipCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Internship internshipWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        InternshipCard internshipCard = new InternshipCard(internshipWithNoTags, 1);
        uiPartRule.setUiPart(internshipCard);
        assertCardDisplay(internshipCard, internshipWithNoTags, 1);

        // with tags
        Internship internshipWithTags = new PersonBuilder().build();
        internshipCard = new InternshipCard(internshipWithTags, 2);
        uiPartRule.setUiPart(internshipCard);
        assertCardDisplay(internshipCard, internshipWithTags, 2);
    }

    @Test
    public void equals() {
        Internship internship = new PersonBuilder().build();
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
        Internship differentInternship = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new InternshipCard(differentInternship, 0)));

        // same internship, different index -> returns false
        assertFalse(personCard.equals(new InternshipCard(internship, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedInternship} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(InternshipCard personCard, Internship expectedInternship, int expectedId) {
        guiRobot.pauseForHuman();

        InternshipCardHandle internshipCardHandle = new InternshipCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", internshipCardHandle.getId());

        // verify internship details are displayed correctly
        assertCardDisplaysPerson(expectedInternship, internshipCardHandle);
    }
}
