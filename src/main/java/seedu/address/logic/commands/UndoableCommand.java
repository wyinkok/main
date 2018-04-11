package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyJobbiBot previousInternshipBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#InternshipBook}.
     */
    private void saveInternshipBookSnapshot() {
        requireNonNull(model);
        this.previousInternshipBook = new JobbiBot(model.getJobbiBot());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the JobbiBot to the state before this command
     * was executed and updates the filtered internship list to
     * show all internships.
     */
    protected final void undo() {
        requireAllNonNull(model, previousInternshipBook);
        model.resetData(previousInternshipBook);
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
    }

    /**
     * Executes the command and updates the filtered internship
     * list to show all internships.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
    }

    @Override
    public final CommandResult execute() throws CommandException, DuplicateInternshipException {
        saveInternshipBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
