package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.InternshipCardHandle;
import guitests.guihandles.InternshipListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Person;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(InternshipCardHandle expectedCard, InternshipCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, InternshipCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code internshipListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(InternshipListPanelHandle internshipListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], internshipListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code internshipListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(InternshipListPanelHandle internshipListPanelHandle, List<Person> persons) {
        assertListMatching(internshipListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code internshipListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(InternshipListPanelHandle internshipListPanelHandle, int size) {
        int numberOfPeople = internshipListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
