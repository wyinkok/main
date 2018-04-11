package seedu.address.logic.commands;

import static seedu.address.logic.UndoRedoStackUtil.prepareStack;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.saveFirstInternship;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.tag.UniqueTagList;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalInternshipBook(), new UserPrefs());
    private final SaveCommand saveCommandOne = new SaveCommand(INDEX_FIRST_INTERNSHIP);
    private final SaveCommand saveCommandTwo = new SaveCommand(INDEX_SECOND_INTERNSHIP);

    public RedoCommandTest() throws UniqueTagList.DuplicateTagException {
    }

    @Before
    public void setUp() throws Exception {
        saveCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        saveCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        saveCommandOne.preprocessUndoableCommand();
        saveCommandTwo.preprocessUndoableCommand();
    }

    @Test
    public void execute() throws DuplicateInternshipException, CommandException {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(saveCommandTwo, saveCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalInternshipBook(), new UserPrefs());

        // multiple commands in redoStack
        saveFirstInternship(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        //saveFirstInternship(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
