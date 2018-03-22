package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.InternshipCardHandle;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class InternshipCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        InternshipCard internshipCard = new InternshipCard(personWithNoTags, 1);
        uiPartRule.setUiPart(internshipCard);
        assertCardDisplay(internshipCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        internshipCard = new InternshipCard(personWithTags, 2);
        uiPartRule.setUiPart(internshipCard);
        assertCardDisplay(internshipCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        InternshipCard personCard = new InternshipCard(person, 0);

        // same person, same index -> returns true
        InternshipCard copy = new InternshipCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new InternshipCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new InternshipCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(InternshipCard personCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        InternshipCardHandle internshipCardHandle = new InternshipCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", internshipCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, internshipCardHandle);
    }
}
