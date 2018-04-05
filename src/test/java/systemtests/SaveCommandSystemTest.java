package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX;
import static seedu.address.logic.commands.SaveCommand.MESSAGE_SAVED_INTERNSHIP_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;
import static seedu.address.testutil.TestUtil.getInternship;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getSecondLastIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.SavedInternshipBuilder;

public class SaveCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_SAVE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);

    @Test
    public void save() throws CommandException {

        /* ----------------- Performing save operation while an unfiltered list is being shown -------------------- */

        /* Case: save the first person in the list, command with leading spaces and trailing spaces -> saved */
        Model expectedmodel = getModel();
        Index firstIndex = INDEX_FIRST_INTERNSHIP;
        String command = "     " + SaveCommand.COMMAND_WORD + "      " + firstIndex.getOneBased() + "       ";
        Internship editedInternship = addSavedTagToInternship(expectedmodel, firstIndex);
        assertCommandSuccess(command, firstIndex, editedInternship);

        /* Case: save the last internship in the list -> saved */
        Model modelBeforeSavingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeSavingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo saving the last internship in the list -> last internship restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeSavingLast, expectedResultMessage);

        /* Case: redo saving the last internship in the list -> last internship saved again */
        command = RedoCommand.COMMAND_WORD;
        addSavedTagToInternship(modelBeforeSavingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeSavingLast, expectedResultMessage);

        /* Case: save the middle internship in the list -> saved */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing save operation while a filtered list is being shown ---------------------- */

        /* Case: filtered internship list, save index within bounds of internship book and internship list -> save */
        showInternshipsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_INTERNSHIP;
        assertTrue(index.getZeroBased() < getModel().getFilteredInternshipList().size());
        command = SaveCommand.COMMAND_WORD + " " + index.getOneBased();
        Internship internshipWithSavedTag = new SavedInternshipBuilder()
                .addTag(getModel().getFilteredInternshipList().get(index.getZeroBased()));
        assertCommandSuccess(command, index, internshipWithSavedTag);
        /* Case: filtered internship list,
         * save index within bounds of internship book but out of bounds of internship list -> rejected
         */
        showInternshipsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getInternshipList().size();
        command = SaveCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* --------------------- Performing save operation while a internship card is selected --------------------- */

        /* Case: save the selected internship
                    -> internship list panel selects the internship before the saved internship */
        showAllInternships();
        Model expectedModel = getModel();
        Index selectedIndex = getSecondLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased());
        selectInternship(selectedIndex);
        command = SaveCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        Internship newEditedInternship = addSavedTagToInternship(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_SAVED_INTERNSHIP_SUCCESS, newEditedInternship);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid save operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = SaveCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_SAVE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = SaveCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_SAVE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getInternshipList().size() + 1);
        command = SaveCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SaveCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_SAVE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SaveCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_SAVE_COMMAND_FORMAT);

    }

    /**
     * Update the {@code Person} at the specified {@code index} in {@code model}'s internship book.
     * @return the internship person with a "saved" tag
     */
    private Internship addSavedTagToInternship(Model model, Index index) throws CommandException {
        Internship targetInternship = getInternship(model, index);
        Internship editedInternship = new SavedInternshipBuilder().addTag(targetInternship);
        try {
            model.updateInternship(targetInternship, editedInternship);
        } catch (InternshipNotFoundException pnfe) {
            throw new AssertionError("targetInternship is retrieved from model.");
        } catch (DuplicateInternshipException e) {
            throw new AssertionError("editedInternship is a duplicate in expectedModel.");
        }
        return editedInternship;
    }

    /**
     * Saves the internship at {@code toSave} by creating a default {@code SaveCommand} using {@code toSave} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see SaveCommandSystemTest#assertCommandSuccess(String, Index, Internship)
     */
    private void assertCommandSuccess(Index toSave) throws CommandException {
        Model expectedModel = getModel();
        Internship editedInternship = addSavedTagToInternship(expectedModel, toSave);
        String expectedResultMessage = String.format(MESSAGE_SAVED_INTERNSHIP_SUCCESS, editedInternship);

        assertCommandSuccess(
                SaveCommand.COMMAND_WORD + " " + toSave.getOneBased(), expectedModel, expectedResultMessage);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see SaveCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toSave, Internship editedInternship) {
        assertCommandSuccess(command, toSave, editedInternship, null);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the internship at index {@code toSave} being
     * updated to values specified {@code editedInternship}.<br>
     * @param toSave the index of the current model's filtered list.
     * @see SaveCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toSave, Internship editedInternship,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateInternship(
                    expectedModel.getFilteredInternshipList().get(toSave.getZeroBased()), editedInternship);
        } catch (DuplicateInternshipException | InternshipNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedInternship is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(SaveCommand.MESSAGE_SAVED_INTERNSHIP_SUCCESS, editedInternship),
                    expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        expectedModel.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
