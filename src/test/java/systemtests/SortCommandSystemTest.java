package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INTERNSHIPS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalInternships.BUSINESS1;
import static seedu.address.testutil.TypicalInternships.BUSINESS2;
import static seedu.address.testutil.TypicalInternships.BUSINESS4;
import static seedu.address.testutil.TypicalInternships.DATASCIENCE;
import static seedu.address.testutil.TypicalInternships.ENGINEERING1;
import static seedu.address.testutil.TypicalInternships.ENGINEERING2;
import static seedu.address.testutil.TypicalInternships.ENGINEERING3;
import static seedu.address.testutil.TypicalInternships.KEYWORD_MATCHING_AUDIT;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN1;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN2;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN3;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN4;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN5;
import static seedu.address.testutil.TypicalInternshipsForSorting.getTypicalInternshipForSorting;

import java.util.Arrays;

import org.junit.Test;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.TypicalInternshipsForSorting;

//@@author niloc94
public class SortCommandSystemTest extends JobbiBotSystemTest {

    /**
     * Returns the data with saved tags to be loaded into the file in {@link #getDataFileLocation()}.
     */
    @Override
    protected JobbiBot getInitialData() {
        return TypicalInternshipsForSorting.getTypicalInternshipForSorting();
    }

    private Model model = new ModelManager(getTypicalInternshipForSorting(), new UserPrefs());

    @Test
    public void sort() {

    /* -------------------------Sorting on an unfiltered list ---------------------------------------------------- */

        /* Case: Sort with one argument, command with leading spaces and trailing spaces */
        String command = "   " + SortCommand.COMMAND_WORD + " " + "role" + "  ";
        ModelHelper.setSortedList(model, Arrays.asList("role"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

    /* -------------------------Sorting on a filtered list ------------------------------------------------------- */







    /* -------------------------Sorting on a searched list ------------------------------------------------------- */






    }



    /* --------------------------------------- Helper Methods ----------------------------------------------------- */

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_INTERNSHIPS_LISTED_OVERVIEW} with the number of internship in the filtered
     * list, and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, expectedModel.getFilteredInternshipList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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