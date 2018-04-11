package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalInternships.ALICE;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.internship.Internship;
import seedu.address.model.tag.Tag;

public class JobbiBotTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final JobbiBot jobbiBot = new JobbiBot();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), jobbiBot.getInternshipList());
        assertEquals(Collections.emptyList(), jobbiBot.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        jobbiBot.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        JobbiBot newData = getTypicalInternshipBook();
        jobbiBot.resetData(newData);
        assertEquals(newData, jobbiBot);
    }

    @Test
    public void resetData_withDuplicateInternships_throwsAssertionError() {
        // Repeat ALICE twice
        List<Internship> newInternships = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        JobbiBotStub newData = new JobbiBotStub(newInternships, newTags);

        thrown.expect(AssertionError.class);
        jobbiBot.resetData(newData);
    }

    @Test
    public void getInternshipList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        jobbiBot.getInternshipList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        jobbiBot.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyJobbiBot whose internships and tags lists can violate interface constraints.
     */
    private static class JobbiBotStub implements ReadOnlyJobbiBot {
        private final ObservableList<Internship> internships = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        JobbiBotStub(Collection<Internship> internships, Collection<? extends Tag> tags) {
            this.internships.setAll(internships);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Internship> getInternshipList() {
            return internships;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
