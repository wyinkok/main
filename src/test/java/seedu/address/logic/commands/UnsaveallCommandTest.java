//@@author wyinkok
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalPersonsWithSavedTag.getTypicalAddressBookWithSavedTag;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.testutil.UnsavedInternshipBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code UnsaveallCommand}.
 */
public class UnsaveallCommandTest {

    private Model model;
    private UnsaveallCommand unsaveallCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithSavedTag(), new UserPrefs());
        unsaveallCommand = new UnsaveallCommand();
        unsaveallCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_UnfilteredList_success() throws Exception {
        ObservableList<Internship> internshipsToUnsave = model.getFilteredInternshipList();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for(Internship internshipToUnsave : internshipsToUnsave) {
            Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                    .removeTagForUnsaveallCommandOnly(internshipToUnsave);
            expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);
            String expectedMessage = String.format(UnsaveallCommand.MESSAGE_UNSAVED_ALL_INTERNSHIP_SUCCESS);
            assertCommandSuccess(unsaveallCommand, model, expectedMessage, expectedModel);
        }
    }

    @Test
    public void executeUndoRedo_UnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ObservableList<Internship> internshipsToUnsave = model.getFilteredInternshipList();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // saveall -> all internships saved
        unsaveallCommand.execute();
        undoRedoStack.push(unsaveallCommand);

        // undo -> reverts internshiplist back to previous state and filtered internship list to show all internships
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same internships saved again
        for(Internship internshipToUnsave : internshipsToUnsave) {
            Internship internshipWithASavedTag = new UnsavedInternshipBuilder().
                    removeTagForUnsaveallCommandOnly(internshipToUnsave);
            expectedModel.updateInternship(internshipToUnsave, internshipWithASavedTag);
        }
            assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
