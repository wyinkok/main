package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.internship.UniqueInternshipList;

public class UniqueInternshipListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueInternshipList uniqueInternshipList = new UniqueInternshipList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueInternshipList.asObservableList().remove(0);
    }
}
