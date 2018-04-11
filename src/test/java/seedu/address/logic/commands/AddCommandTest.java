package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyJobbiBot;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.InternshipBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullInternship_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_internshipAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingInternshipAdded modelStub = new ModelStubAcceptingInternshipAdded();
        Internship validInternship = new InternshipBuilder().build();

        CommandResult commandResult = getAddCommandForInternship(validInternship, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validInternship), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validInternship), modelStub.internshipsAdded);
    }

    @Test
    public void execute_duplicateInternship_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateInternshipException();
        Internship validInternship = new InternshipBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_INTERNSHIP);

        getAddCommandForInternship(validInternship, modelStub).execute();
    }

    @Test
    public void equals() {
        Internship alice = new InternshipBuilder().withName("Alice").build();
        Internship bob = new InternshipBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different internship -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given internship.
     */
    private AddCommand getAddCommandForInternship(Internship internship, Model model) {
        AddCommand command = new AddCommand(internship);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addInternship(Internship internship) throws DuplicateInternshipException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyJobbiBot newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyJobbiBot getJobbiBot() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteInternship(Internship target) throws InternshipNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateInternship(Internship target, Internship editedInternship)
                throws DuplicateInternshipException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Internship> getFilteredInternshipList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredInternshipList(Predicate<Internship> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateSearchedInternshipList(Predicate<Internship> predicate) {
            fail("This method should not be called");
        }

        @Override
        public void setComparator(List<String> keywords) {
            fail("This method should not be called");
        }
    }

    /**
     * A Model stub that always throw a DuplicateInternshipException when trying to add a internship.
     */
    private class ModelStubThrowingDuplicateInternshipException extends ModelStub {
        @Override
        public void addInternship(Internship internship) throws DuplicateInternshipException {
            throw new DuplicateInternshipException();
        }

        @Override
        public ReadOnlyJobbiBot getJobbiBot() {
            return new JobbiBot();
        }
    }

    /**
     * A Model stub that always accept the internship being added.
     */
    private class ModelStubAcceptingInternshipAdded extends ModelStub {
        final ArrayList<Internship> internshipsAdded = new ArrayList<>();

        @Override
        public void addInternship(Internship internship) throws DuplicateInternshipException {
            requireNonNull(internship);
            internshipsAdded.add(internship);
        }

        @Override
        public ReadOnlyJobbiBot getJobbiBot() {
            return new JobbiBot();
        }
    }

}
