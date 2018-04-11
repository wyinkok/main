//@@author wyinkok
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalPersonsWithSavedTag.getTypicalInternshipBookWithSavedTag;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.testutil.UnsavedInternshipBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SaveCommand}.
 */
public class UnsaveCommandTest {

    private Model model = new ModelManager(getTypicalInternshipBookWithSavedTag(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Internship internshipToUnsave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                .removeTag(model.getFilteredInternshipList().get(0));
        UnsaveCommand unsaveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);

        String expectedMessage = String.format(UnsaveCommand.MESSAGE_UNSAVED_INTERNSHIP_SUCCESS,
            internshipWithoutSavedTag);
        ModelManager expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());
        expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);

        assertCommandSuccess(unsaveCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        UnsaveCommand unsaveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unsaveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getJobbiBot().getInternshipList().size());

        UnsaveCommand unsaveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unsaveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Internship internshipToUnsave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        UnsaveCommand unsaveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        Model expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());

        // save -> first person saved
        unsaveCommand.execute();
        undoRedoStack.push(unsaveCommand);

        // undo -> reverts InternshipBook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first perison saved again
        Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                .removeTag(model.getFilteredInternshipList().get(0));
        expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        UnsaveCommand unsaveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> unsaveCommand not pushed into undoRedoStack
        assertCommandFailure(unsaveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Remove a saved {@code Person} from a filtered list.
     * 2. Undo the unsave command.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously unsaved person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the unsave command. This ensures {@code RedoCommand} removes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonUnsaved() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        UnsaveCommand unsaveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        Model expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());

        showInternshipAtIndex(model, INDEX_SECOND_INTERNSHIP);
        Internship internshipToUnsave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        // unsave -> removes second person in unfiltered person list / first person in filtered person list
        unsaveCommand.execute();

        undoRedoStack.push(unsaveCommand);

        // undo -> reverts internship database back to previous state and
        // filtered internship list to show all internships
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                .removeTag(model.getFilteredInternshipList().get(1));
        expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);
        assertNotEquals(internshipToUnsave, model.getFilteredInternshipList()
                .get(INDEX_FIRST_INTERNSHIP.getZeroBased()));
        // redo -> saves same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        UnsaveCommand unsaveFirstCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        UnsaveCommand unsaveSecondCommand = prepareCommand(INDEX_SECOND_INTERNSHIP);

        // same object -> returns true
        assertTrue(unsaveFirstCommand.equals(unsaveFirstCommand));

        // same values -> returns true
        UnsaveCommand unsaveFirstCommandCopy = prepareCommand(INDEX_FIRST_INTERNSHIP);
        assertTrue(unsaveFirstCommand.equals(unsaveFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        unsaveFirstCommandCopy.preprocessUndoableCommand();
        assertTrue(unsaveFirstCommand.equals(unsaveFirstCommandCopy)); //not sure!!

        // different types -> returns false
        assertFalse(unsaveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unsaveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unsaveFirstCommand.equals(unsaveSecondCommand));
    }

    /**
     * Returns a {@code UnsaveCommand} with the parameter {@code index}.
     */
    private UnsaveCommand prepareCommand(Index index) {
        UnsaveCommand unsaveCommand = new UnsaveCommand(index);
        unsaveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unsaveCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredInternshipList(p -> false);

        assertTrue(model.getFilteredInternshipList().isEmpty());
    }
}
