package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.saveFirstInternship;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.SavedInternshipBuilder;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalInternshipBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalInternshipBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        saveFirstInternship(expectedModel);
        assertEquals(expectedModel, model);

        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        // undo() should cause the model's filtered list to show all internships
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalInternshipBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() throws DuplicateInternshipException, CommandException {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        // redo() should cause the model's filtered list to show all internships
        dummyCommand.redo();
        saveFirstInternship(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first internship in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Internship internshipToSave = model.getFilteredInternshipList().get(0);
            Internship internshipWithSavedTag = new SavedInternshipBuilder()
                        .addTag(internshipToSave);
            try {
                model.updateInternship(internshipToSave, internshipWithSavedTag);
            } catch (InternshipNotFoundException pnfe) {
                throw new AssertionError("Internship in filtered list must exist in model.", pnfe);
            } catch (DuplicateInternshipException e) {
                throw new CommandException("Internship already exists in the Collection");
            }
            return new CommandResult("");
        }
    }
}
