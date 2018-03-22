package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.SavedPersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SaveCommand}.
 */
public class SaveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person internshipToSave = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person internshipWithSavedTag = new SavedPersonBuilder().addTag(model.getFilteredPersonList().get(0));
        SaveCommand saveCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(SaveCommand.MESSAGE_SAVED_INTERNSHIP_SUCCESS, internshipWithSavedTag);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(internshipToSave, internshipWithSavedTag);

        assertCommandSuccess(saveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SaveCommand saveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws UniqueTagList.DuplicateTagException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SaveCommand saveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person internshipToSave = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SaveCommand saveCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // save -> first person saved
        saveCommand.execute();
        undoRedoStack.push(saveCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person saved again
        Person internshipWithSavedTag = new SavedPersonBuilder().addTag(model.getFilteredPersonList().get(0));
        expectedModel.updatePerson(internshipToSave, internshipWithSavedTag);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws UniqueTagList.DuplicateTagException {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SaveCommand saveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> saveCommand not pushed into undoRedoStack
        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Save a {@code Person} from a filtered list.
     * 2. Undo the saved command.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously saved person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the save command. This ensures {@code RedoCommand} saves the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonSaved() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        SaveCommand saveCommand = prepareCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person internshipToSave = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // save -> saves second person in unfiltered person list / first person in filtered person list
        saveCommand.execute();

        undoRedoStack.push(saveCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        Person internshipWithSavedTag = new SavedPersonBuilder().addTag(model.getFilteredPersonList().get(1));
        expectedModel.updatePerson(internshipToSave, internshipWithSavedTag);
        assertNotEquals(internshipToSave, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> saves same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        SaveCommand saveFirstCommand = prepareCommand(INDEX_FIRST_PERSON);
        SaveCommand saveSecondCommand = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(saveFirstCommand.equals(saveFirstCommand));

        // same values -> returns true
        SaveCommand saveFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        saveFirstCommandCopy.preprocessUndoableCommand();
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy)); //not sure!!

        // different types -> returns false
        assertFalse(saveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(saveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(saveFirstCommand.equals(saveSecondCommand));
    }

    /**
     * Returns a {@code SaveCommand} with the parameter {@code index}.
     */
    private SaveCommand prepareCommand(Index index) throws UniqueTagList.DuplicateTagException {
        SaveCommand saveCommand = new SaveCommand(index);
        saveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return saveCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
