package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalInternships.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.testutil.SavedInternshipBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SaveallCommand}.
 */
public class SaveallCommandTest {

    private Model model;
    private SaveallCommand saveallCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        saveallCommand = new SaveallCommand();
        saveallCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_UnfilteredList_success() throws Exception {
        ObservableList<Internship> internshipsToSave = model.getFilteredInternshipList();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for(Internship internshipToSave : internshipsToSave) {
            Internship internshipWithSavedTag = new SavedInternshipBuilder().addTagForSaveAllCommandOnly(internshipToSave);
            expectedModel.updateInternship(internshipToSave, internshipWithSavedTag);
            String expectedMessage = String.format(SaveallCommand.MESSAGE_SAVED_ALL_INTERNSHIP_SUCCESS);
            assertCommandSuccess(saveallCommand, model, expectedMessage, expectedModel);
        }
    }

    @Test
    public void executeUndoRedo_UnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ObservableList<Internship> internshipsToSave = model.getFilteredInternshipList();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // saveall -> all internships saved
        saveallCommand.execute();
        undoRedoStack.push(saveallCommand);

        // undo -> reverts internshiplist back to previous state and filtered internship list to show all internships
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same internships saved again
        Internship internshipWithSavedTag = new SavedInternshipBuilder()
                .addTag(model.getFilteredInternshipList().get(0));
        for(Internship internshipToSave : internshipsToSave) {
            Internship internshipWithASavedTag = new SavedInternshipBuilder().addTagForSaveAllCommandOnly(internshipToSave);
            expectedModel.updateInternship(internshipToSave, internshipWithASavedTag);
        }
            assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
