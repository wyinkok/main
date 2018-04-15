package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INTERNSHIPS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN1;
import static seedu.address.testutil.TypicalInternshipsForSorting.IN2;
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
import seedu.address.logic.parser.SortCommandParser;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.TypicalInternshipsForSorting;

//@@author niloc94
public class SortCommandSystemTest extends JobbiBotSystemTest {

    private Model model = new ModelManager(getTypicalInternshipForSorting(), new UserPrefs());

    /**
     * Returns the data with saved tags to be loaded into the file in {@link #getDataFileLocation()}.
     */
    @Override
    protected JobbiBot getInitialData() {
        return TypicalInternshipsForSorting.getTypicalInternshipForSorting();
    }


    /* -------------------------Sorting on an unfiltered list ------------------------------------------------- */

    @Test
    public void sort_unsearched_unfiltered()  {

        /* Case: Sort with one argument, command with leading spaces and trailing spaces */
        String command = "   " + SortCommand.COMMAND_WORD + " " + "-salary" + "  ";
        ModelHelper.setSortedList(model, Arrays.asList("-salary"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: Sort with mixed case keywords */
        command = SortCommand.COMMAND_WORD + " sAlary";
        ModelHelper.setSortedList(model, Arrays.asList("salary"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: Sort with mixed case command word */
        command = "sORT" + " -salary";
        ModelHelper.setSortedList(model, Arrays.asList("-salary"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: Sort, keyword contains substring of valid argument  */
        command = SortCommand.COMMAND_WORD + " salaries";
        assertCommandFailure(command, SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        /* Case: Sort, keyword is substring of valid argument */
        command = SortCommand.COMMAND_WORD + " sal";
        assertCommandFailure(command, SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        /* Case: undo previous filter command -> failure */
        command = UndoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);

        /* Case: redo previous filter command -> failure */
        command = RedoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);
    }

    /* -------------------------Sorting on a searched list ------------------------------------------------------- */

    @Test
    public void sort_searched_unfiltered() throws DuplicateInternshipException, InternshipNotFoundException {

        /* Initialize a searched list */
        initializeSearchedList();

        /* Test Cases same as unsearched and unfiltered */
        sort_unsearched_unfiltered();
    }

    /* -------------------------Sorting on a filtered list ------------------------------------------------------- */

    @Test
    public void sort_searched_filtered() throws DuplicateInternshipException, InternshipNotFoundException {

        /* Initialize a searched and filtered list */
        initializeSearchedList();
        String command = FilterCommand.COMMAND_WORD + " " + "IndustryA";
        ModelHelper.setFilteredList(model, IN1, IN4, IN5);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Test Cases same as unsearched and unfiltered */
        sort_unsearched_unfiltered();
    }


    /* --------------------------------------- Helper Methods ----------------------------------------------------- */

    /**
     * Helper method to initialize a searched list
     *
     * @throws DuplicateInternshipException
     * @throws InternshipNotFoundException
     */
    private void initializeSearchedList() throws DuplicateInternshipException, InternshipNotFoundException {
        String command = FindCommand.COMMAND_WORD + " " + "IndustryA IndustryB";
        model.updateInternship(IN1, IN1.addTagsToInternship("IndustryA"));
        model.updateInternship(IN4, IN4.addTagsToInternship("IndustryA"));
        model.updateInternship(IN5, IN5.addTagsToInternship("IndustryA"));
        model.updateInternship(IN2, IN2.addTagsToInternship("IndustryB"));
        ModelHelper.setSearchedList(model, IN1, IN2, IN4, IN5);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();
    }

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
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, model);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

