package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalInternships.BUSINESS1;
import static seedu.address.testutil.TypicalInternships.BUSINESS2;
import static seedu.address.testutil.TypicalInternships.BUSINESS3;
import static seedu.address.testutil.TypicalInternships.BUSINESS4;
import static seedu.address.testutil.TypicalInternships.DATASCIENCE;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.InternshipContainsAllKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalInternshipBook(), new UserPrefs());

    @Test
    public void equals() {
        InternshipContainsAllKeywordsPredicate firstPredicate =
                new InternshipContainsAllKeywordsPredicate(Collections.singletonList("first"));
        InternshipContainsAllKeywordsPredicate secondPredicate =
                new InternshipContainsAllKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> return true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_singleKeywords_zeroInternshipsFound() {
        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP);
        FilterCommand command = prepareCommand("TryFindingThis");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_zeroInternshipsFound() {
        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP);
        FilterCommand command = prepareCommand("ENGINEERING BUSINESS");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleKeyword_multipleInternshipsFound() {
        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE);
        FilterCommand command = prepareCommand("Business");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BUSINESS1, BUSINESS2, BUSINESS3, BUSINESS4));
    }

    @Test
    public void execute_multipleKeywords_singleInternshipsFound() {
        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE);
        FilterCommand command = prepareCommand("Data Scientist");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DATASCIENCE));
    }

    @Test
    public void execute_multipleKeywords_multipleInternshipsFound() {
        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE);
        FilterCommand command = prepareCommand("Audit Intern");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BUSINESS2, BUSINESS4));
    }

    /**
     * Parses {@code userInput} into a {@code FilterCommand}.
     */
    private FilterCommand prepareCommand(String userInput) {
        FilterCommand command =
                new FilterCommand(
                        new InternshipContainsAllKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code JobbiBot} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage, List<Internship> expectedList) {
        JobbiBot expectedJobbiBot = new JobbiBot(model.getJobbiBot());
        CommandResult commandResult = command.executeUndoableCommand();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInternshipList());
        assertEquals(expectedJobbiBot, model.getJobbiBot());
    }
}
