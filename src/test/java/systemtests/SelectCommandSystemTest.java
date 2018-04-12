package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_INTERNSHIP_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalInternships.getTypicalInternships;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class SelectCommandSystemTest extends JobbiBotSystemTest {
    @Test
    public void select() {
        /* ------------------------ Perform select operations on the shown unfiltered list -------------------------- */

        /* Case: select the first card in the internship list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_INTERNSHIP);

        /* Case: select the last card in the internship list -> selected */
        Index internshipCount = Index.fromOneBased(getTypicalInternships().size());
        command = SelectCommand.COMMAND_WORD + " " + internshipCount.getOneBased();
        assertCommandSuccess(command, internshipCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the internship list -> selected */
        Index middleIndex = Index.fromOneBased(internshipCount.getOneBased() / 2);
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: mixed case command word -> selected */
        assertCommandSuccess("SeLeCt 1", INDEX_FIRST_INTERNSHIP);

        /* ------------------------ Perform select operations on the shown filtered list ---------------------------- */

        /* Case: filtered internship list, select index within bounds of address book but out of bounds of internship
         * list -> rejected
         */
        showInternshipsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getJobbiBot().getInternshipList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " "
                + invalidIndex, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* Case: filtered internship list, select index within bounds of address book and internship list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredInternshipList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid select operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredInternshipList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " "
                + invalidIndex, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected internship.<br>
     * 4. {@code Model}, {@code Storage} and {@code InternshipListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see JobbiBotSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_INTERNSHIP_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getInternshipInternshipListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code InternshipListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
