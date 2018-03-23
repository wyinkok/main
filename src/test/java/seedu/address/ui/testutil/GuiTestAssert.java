package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.InternshipCardHandle;
import guitests.guihandles.InternshipListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.internship.Internship;

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
        assertEquals(expectedCard.getSalary(), actualCard.getSalary());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedInternship}.
     */
    public static void assertCardDisplaysInternship(Internship expectedInternship, InternshipCardHandle actualCard) {
        assertEquals(expectedInternship.getName().fullName, actualCard.getName());
        assertEquals(expectedInternship.getSalary().value, actualCard.getSalary());
        assertEquals(expectedInternship.getEmail().value, actualCard.getEmail());
        assertEquals(expectedInternship.getAddress().value, actualCard.getAddress());
        assertEquals(expectedInternship.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code internshipListPanelHandle} displays the details of {@code internships} correctly and
     * in the correct order.
     */
    public static void assertListMatching(InternshipListPanelHandle internshipListPanelHandle, Internship... internships) {
        for (int i = 0; i < internships.length; i++) {
            assertCardDisplaysInternship(internships[i], internshipListPanelHandle.getInternshipCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code internshipListPanelHandle} displays the details of {@code internships} correctly and
     * in the correct order.
     */
    public static void assertListMatching(InternshipListPanelHandle internshipListPanelHandle, List<Internship> internships) {
        assertListMatching(internshipListPanelHandle, internships.toArray(new Internship[0]));
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
