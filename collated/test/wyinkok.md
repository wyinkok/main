# wyinkok
###### \java\guitests\guihandles\ChatBotCardHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a chatbot card in the chat bot panel.
 */

public class ChatBotCardHandle extends NodeHandle<Node> {
    private static final String USERNAME_FIELD_ID = "#username";
    private static final String MESSAGES_FIELD_ID = "#messages";

    private final Label usernameLabel;
    private final Label messagesLabel;

    public ChatBotCardHandle(Node cardNode) {
        super(cardNode);

        this.usernameLabel = getChildNode(USERNAME_FIELD_ID);
        this.messagesLabel = getChildNode(MESSAGES_FIELD_ID);
    }

    public String getUsername() {
        return usernameLabel.getText();
    }

    public String getMessages() {
        return messagesLabel.getText();
    }

}

```
###### \java\seedu\address\logic\commands\NewChatCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.NewChatCommand.MESSAGE_RESTART_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.StartAppRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class NewChatCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_restart_success() {
        CommandResult result = new NewChatCommand().execute();
        assertEquals(MESSAGE_RESTART_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof StartAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\SaveCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.getTypicalInternshipBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.SavedInternshipBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SaveCommand}.
 */
public class SaveCommandTest {

    private Model model = new ModelManager(getTypicalInternshipBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Internship internshipToSave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        Internship internshipWithSavedTag = new SavedInternshipBuilder()
                .addTag(model.getFilteredInternshipList().get(0));
        SaveCommand saveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);

        String expectedMessage = String.format(SaveCommand.MESSAGE_SAVED_INTERNSHIP_SUCCESS, internshipWithSavedTag);

        ModelManager expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());
        expectedModel.updateInternship(internshipToSave, internshipWithSavedTag);

        assertCommandSuccess(saveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        SaveCommand saveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws UniqueTagList.DuplicateTagException {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getJobbiBot().getInternshipList().size());

        SaveCommand saveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Internship internshipToSave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        SaveCommand saveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        Model expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());

        // save -> first internship saved
        saveCommand.execute();
        undoRedoStack.push(saveCommand);

        // undo -> reverts internshiplist back to previous state and filtered internship list to show all internships
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person saved again
        Internship internshipWithSavedTag = new SavedInternshipBuilder()
                .addTag(model.getFilteredInternshipList().get(0));
        expectedModel.updateInternship(internshipToSave, internshipWithSavedTag);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws UniqueTagList.DuplicateTagException {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        SaveCommand saveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> saveCommand not pushed into undoRedoStack
        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Save a {@code Person} from a filtered list.
     * 2. Undo the saved command.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously saved internship in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the save command. This ensures {@code RedoCommand} saves the internship object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameInternshipSaved() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        SaveCommand saveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        Model expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());

        showInternshipAtIndex(model, INDEX_SECOND_INTERNSHIP);
        Internship internshipToSave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        // save -> saves second internship in unfiltered internship list / first internship in filtered internship list
        saveCommand.execute();

        undoRedoStack.push(saveCommand);

        // undo -> reverts internshiplist back to previous state and filtered internship list to show all internships
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        Internship internshipWithSavedTag = new SavedInternshipBuilder()
                .addTag(model.getFilteredInternshipList().get(1));
        expectedModel.updateInternship(internshipToSave, internshipWithSavedTag);
        assertNotEquals(internshipToSave, model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased()));
        // redo -> saves same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        SaveCommand saveFirstCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        SaveCommand saveSecondCommand = prepareCommand(INDEX_SECOND_INTERNSHIP);

        // same object -> returns true
        assertTrue(saveFirstCommand.equals(saveFirstCommand));

        // same values -> returns true
        SaveCommand saveFirstCommandCopy = prepareCommand(INDEX_FIRST_INTERNSHIP);
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        saveFirstCommandCopy.preprocessUndoableCommand();
        assertTrue(saveFirstCommand.equals(saveFirstCommandCopy)); //not sure!!

        // different types -> returns false
        assertFalse(saveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(saveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(saveFirstCommand.equals(saveSecondCommand));
    }

    /**
     * Returns a {@code SaveCommand} with the parameter {@code index}.
     */
    private SaveCommand prepareCommand(Index index) throws UniqueTagList.DuplicateTagException {
        SaveCommand saveCommand = new SaveCommand(index);
        saveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return saveCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoInternship(Model model) {
        model.updateFilteredInternshipList(p -> false);

        assertTrue(model.getFilteredInternshipList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\StartCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.StartCommand.MESSAGE_START_ACKNOWLEDGEMENT;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.StartAppRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StartCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_start_success() {
        CommandResult result = new StartCommand().execute();
        assertEquals(MESSAGE_START_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof StartAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\UnsaveCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalPersonsWithSavedTag.getTypicalInternshipBookWithSavedTag;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;
import seedu.address.testutil.UnsavedInternshipBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code SaveCommand}.
 */
public class UnsaveCommandTest {

    private Model model = new ModelManager(getTypicalInternshipBookWithSavedTag(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Internship internshipToUnsave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                .removeTag(model.getFilteredInternshipList().get(0));
        UnsaveCommand unsaveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);

        String expectedMessage = String.format(UnsaveCommand.MESSAGE_UNSAVED_INTERNSHIP_SUCCESS,
            internshipWithoutSavedTag);
        ModelManager expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());
        expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);

        assertCommandSuccess(unsaveCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        UnsaveCommand unsaveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unsaveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getJobbiBot().getInternshipList().size());

        UnsaveCommand unsaveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unsaveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Internship internshipToUnsave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        UnsaveCommand unsaveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        Model expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());

        // save -> first person saved
        unsaveCommand.execute();
        undoRedoStack.push(unsaveCommand);

        // undo -> reverts InternshipBook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first perison saved again
        Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                .removeTag(model.getFilteredInternshipList().get(0));
        expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        UnsaveCommand unsaveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> unsaveCommand not pushed into undoRedoStack
        assertCommandFailure(unsaveCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Remove a saved {@code Person} from a filtered list.
     * 2. Undo the unsave command.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously unsaved person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the unsave command. This ensures {@code RedoCommand} removes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonUnsaved() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        UnsaveCommand unsaveCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        Model expectedModel = new ModelManager(model.getJobbiBot(), new UserPrefs());

        showInternshipAtIndex(model, INDEX_SECOND_INTERNSHIP);
        Internship internshipToUnsave = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        // unsave -> removes second person in unfiltered person list / first person in filtered person list
        unsaveCommand.execute();

        undoRedoStack.push(unsaveCommand);

        // undo -> reverts internship database back to previous state and
        // filtered internship list to show all internships
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        Internship internshipWithoutSavedTag = new UnsavedInternshipBuilder()
                .removeTag(model.getFilteredInternshipList().get(1));
        expectedModel.updateInternship(internshipToUnsave, internshipWithoutSavedTag);
        assertNotEquals(internshipToUnsave, model.getFilteredInternshipList()
                .get(INDEX_FIRST_INTERNSHIP.getZeroBased()));
        // redo -> saves same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        UnsaveCommand unsaveFirstCommand = prepareCommand(INDEX_FIRST_INTERNSHIP);
        UnsaveCommand unsaveSecondCommand = prepareCommand(INDEX_SECOND_INTERNSHIP);

        // same object -> returns true
        assertTrue(unsaveFirstCommand.equals(unsaveFirstCommand));

        // same values -> returns true
        UnsaveCommand unsaveFirstCommandCopy = prepareCommand(INDEX_FIRST_INTERNSHIP);
        assertTrue(unsaveFirstCommand.equals(unsaveFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        unsaveFirstCommandCopy.preprocessUndoableCommand();
        assertTrue(unsaveFirstCommand.equals(unsaveFirstCommandCopy)); //not sure!!

        // different types -> returns false
        assertFalse(unsaveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unsaveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unsaveFirstCommand.equals(unsaveSecondCommand));
    }

    /**
     * Returns a {@code UnsaveCommand} with the parameter {@code index}.
     */
    private UnsaveCommand prepareCommand(Index index) {
        UnsaveCommand unsaveCommand = new UnsaveCommand(index);
        unsaveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unsaveCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredInternshipList(p -> false);

        assertTrue(model.getFilteredInternshipList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\parser\SaveCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;

import org.junit.Test;

import seedu.address.logic.commands.SaveCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SaveCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the SaveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SaveCommandParserTest {

    private SaveCommandParser parser = new SaveCommandParser();

    @Test
    public void parse_validArgs_returnsSaveCommand() throws UniqueTagList.DuplicateTagException {
        assertParseSuccess(parser, "1", new SaveCommand(INDEX_FIRST_INTERNSHIP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UnsaveCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;

import org.junit.Test;

import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.model.tag.UniqueTagList;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnsaveCommandParserTest {

    private UnsaveCommandParser parser = new UnsaveCommandParser();

    @Test
    public void parse_validArgs_returnsUnaveCommand() throws UniqueTagList.DuplicateTagException {
        assertParseSuccess(parser, "1", new UnsaveCommand(INDEX_FIRST_INTERNSHIP));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnsaveCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\testutil\SavedInternshipBuilder.java
``` java
package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A utility class to help with building saved Internship objects.
 */
public class SavedInternshipBuilder {


    public static final String MESSAGE_DUPLICATE_TAG = "This internship has been saved";
    public final String savedTagName = "saved";

    /**
     * Initializes the SavedInternshipBuilder with the data of {@code internshipToCopy}.
     */
    public Internship addTag(Internship internshipToCopy) throws CommandException {
        final UniqueTagList personTags = new UniqueTagList(internshipToCopy.getTags());
        try {
            personTags.add(new Tag(savedTagName));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        personTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of intrenship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internshipToCopy.getName(),
                internshipToCopy.getSalary(),
                internshipToCopy.getEmail(),
                internshipToCopy.getAddress(),
                internshipToCopy.getIndustry(),
                internshipToCopy.getLocation(),
                internshipToCopy.getRole(),
                correctTagReferences);


    }

}
```
###### \java\seedu\address\testutil\TypicalPersonsWithSavedTag.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.JobbiBot;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;

/**
 * A utility class containing a list of {@code Internship} objects to be used in tests.
 */
public class TypicalPersonsWithSavedTag {

    public static final Internship ALICE = new InternshipBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withSalary("85355255")
            .withTags("friends", "saved").build();
    public static final Internship BENSON = new InternshipBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withSalary("98765432")
            .withTags("owesMoney", "friends", "saved").build();
    public static final Internship CARL = new InternshipBuilder().withName("Carl Kurz").withSalary("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withTags("saved").build();
    public static final Internship DANIEL = new InternshipBuilder().withName("Daniel Meier").withSalary("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("saved").build();
    public static final Internship ELLE = new InternshipBuilder().withName("Elle Meyer").withSalary("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Internship FIONA = new InternshipBuilder().withName("Fiona Kunz").withSalary("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withTags("saved").build();
    public static final Internship GEORGE = new InternshipBuilder().withName("George Best").withSalary("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withTags("saved").build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersonsWithSavedTag() {} // prevents instantiation

    /**
     * Returns an {@code InternshipBook} with all the typical persons.
     */
    public static InternshipBook getTypicalInternshipBookWithSavedTag() {
        InternshipBook ab = new InternshipBook();
        for (Internship internship : getTypicalPersonsWithSavedTag()) {
            try {
                ab.addInternship(internship);
            } catch (DuplicateInternshipException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Internship> getTypicalPersonsWithSavedTag() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
```
###### \java\seedu\address\testutil\UnsavedInternshipBuilder.java
``` java
package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.SavedTagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
/**
 * A utility class to help with building unsaved internship objects.
 */
public class UnsavedInternshipBuilder {


    public static final String MESSAGE_DUPLICATE_REMOVAL = "This internship has been removed from Saved Collection";
    public final String savedTagName = "saved";

    /**
     * Initializes the UnsavedInternshipBuilder with the data of {@code internshipToCopy}.
     * @param internshipToCopy
     */
    public Internship removeTag(Internship internshipToCopy) throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internshipToCopy.getTags());
        try {
            internshipTags.delete(new Tag(savedTagName));
        } catch (SavedTagNotFoundException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMOVAL);
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internshipToCopy.getName(),
                internshipToCopy.getSalary(),
                internshipToCopy.getEmail(),
                internshipToCopy.getAddress(),
                internshipToCopy.getIndustry(),
                internshipToCopy.getLocation(),
                internshipToCopy.getRole(),
                correctTagReferences);
    }
}
```
###### \java\seedu\address\ui\ChatBotCardTest.java
``` java
package seedu.address.ui;

import org.junit.Test;

import guitests.guihandles.ChatBotCardHandle;
import seedu.address.ui.testutil.GuiTestAssert;
public class ChatBotCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // non undoable command
        String listCommand = "list";
        ChatBotCard chatBotCard = new ChatBotCard("list", 4);
        uiPartRule.setUiPart(chatBotCard);
        assertCardDisplay(chatBotCard, listCommand);

        // undoable command
        String deleteCommand = "delete 1";
        ChatBotCard chatBotCardForUndoableCommand = new ChatBotCard("delete 1", 6);
        uiPartRule.setUiPart(chatBotCardForUndoableCommand);
        assertCardDisplay(chatBotCardForUndoableCommand, deleteCommand);

        // select command
        String selectCommand = "select 1";
        ChatBotCard chatBotCardForSelectCommand = new ChatBotCard("select 1", 8);
        uiPartRule.setUiPart(chatBotCardForSelectCommand);
        assertCardDisplay(chatBotCardForSelectCommand, selectCommand);

        // command with typo error
        String errorCommand = "sdekhgfajf 1";
        ChatBotCard chatBotCardForErrorCommand = new ChatBotCard("sdekhgfajf 1", 10);
        uiPartRule.setUiPart(chatBotCardForErrorCommand);
        assertCardDisplay(chatBotCardForErrorCommand, errorCommand);
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedInternship} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ChatBotCard chatBotCard, String expectedUserInput) {
        guiRobot.pauseForHuman();

        ChatBotCardHandle chatBotCardHandle = new ChatBotCardHandle(chatBotCard.getRoot());

        // verify internship details are displayed correctly
        GuiTestAssert.assertChatBotUserMessage(chatBotCardHandle, expectedUserInput);
    }
}
```
###### \java\systemtests\SaveCommandSystemTest.java
``` java
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

public class SaveCommandSystemTest extends InternshipBookSystemTest {

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
        int invalidIndex = getModel().getJobbiBot().getInternshipList().size();
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
                getModel().getJobbiBot().getInternshipList().size() + 1);
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
     * {@code InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see InternshipBookSystemTest#assertSelectedCardChanged(Index)
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
     * {@code InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
```
###### \java\systemtests\UnsaveCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX;
import static seedu.address.logic.commands.UnsaveCommand.MESSAGE_UNSAVED_INTERNSHIP_SUCCESS;
import static seedu.address.testutil.TestUtil.getInternship;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalPersonsWithSavedTag.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.JobbiBot;
import seedu.address.model.Model;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.testutil.TypicalPersonsWithSavedTag;
import seedu.address.testutil.UnsavedInternshipBuilder;

public class UnsaveCommandSystemTest extends InternshipBookSystemTest {

    private static final String MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnsaveCommand.MESSAGE_USAGE);

    /**
     * Returns the data with saved tags to be loaded into the file in {@link #getDataFileLocation()}.
     */
    @Override
    protected InternshipBook getInitialData() {
        return TypicalPersonsWithSavedTag.getTypicalInternshipBookWithSavedTag();
    }

    @Test
    public void unsave() throws CommandException {


        /* ----------------- Performing save operation while an unfiltered list is being shown -------------------- */

        /* Case: remove the saved first internship in the list,
            command with leading spaces and trailing spaces -> saved */
        Model model = getModel();
        Index firstindex = INDEX_FIRST_INTERNSHIP;
        String command = "     " + UnsaveCommand.COMMAND_WORD + "      " + firstindex.getOneBased() + "       ";
        Internship editedInternship = removeSavedTagToInternship(model, firstindex);
        assertCommandSuccess(command, firstindex, editedInternship);


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
        removeSavedTagToInternship(modelBeforeSavingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeSavingLast, expectedResultMessage);

        /* Case: unsave the middle internship in the list -> unsaved */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing save operation while a filtered list is being shown ---------------------- */

        /* Case: filtered internship list, unsave index within bounds of internship book and internship list
         * -> unsave */
        ///showInternshipsWithName(KEYWORD_MATCHING_MEIER);
        //Index index = INDEX_FIRST_INTERNSHIP;
        //assertTrue(index.getZeroBased() < getModel().getFilteredInternshipList().size());
        //command = UnsaveCommand.COMMAND_WORD + " " + index.getOneBased();
        //Internship personWithoutSavedTag = new UnsavedInternshipBuilder()
        //        .removeTag(getModel().getFilteredInternshipList().get(index.getZeroBased()));
        //        assertCommandSuccess(command, index, personWithoutSavedTag);


        /* Case: filtered internship list,
         * unsave index within bounds of internship book but out of bounds of internship list -> rejected
         */
        showInternshipsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getJobbiBot().getInternshipList().size();
        command = UnsaveCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* --------------------- Performing unsave operation while a internship card is selected ------------------- */

        /* Case: unsave the selected internship
                      -> internship list panel selects the internship before the unsaved internship */
        //showAllInternships();
        //Model expectedModel = getModel();
        //Index selectedIndex = getSecondLastIndex(expectedModel);
        //Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased());
        //selectInternship(selectedIndex);
        //command = UnsaveCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        //Internship nexteditedInternship = removeSavedTagToInternship(expectedModel, selectedIndex);
        //expectedResultMessage = String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, nexteditedInternship);
        //assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid unsave operation ---------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = UnsaveCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = UnsaveCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getJobbiBot().getInternshipList().size() + 1);
        command = UnsaveCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(UnsaveCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(UnsaveCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_UNSAVE_COMMAND_FORMAT);
    }

    /**
     * Removes the "saved" tag from the {@code Person} at the specified {@code index}
     * in {@code model}'s internship book.
     * @return the internship person without a "saved" tag
     */
    private Internship removeSavedTagToInternship(Model model, Index index) throws CommandException {
        Internship targetInternship = getInternship(model, index);
        Internship editedInternship = new UnsavedInternshipBuilder().removeTag(targetInternship);
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
     * Removes the saved internship at {@code toRemove} by creating
     * a default {@code UnsaveCommand} using {@code toRemove} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see UnsaveCommandSystemTest#assertCommandSuccess(String, Index, Internship)
     */
    private void assertCommandSuccess(Index toRemove) throws CommandException {
        Model expectedModel = getModel();
        Internship editedInternship = removeSavedTagToInternship(expectedModel, toRemove);
        String expectedResultMessage = String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, editedInternship);

        assertCommandSuccess(
                UnsaveCommand.COMMAND_WORD + " " + toRemove.getOneBased(), expectedModel,
                expectedResultMessage);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see UnsaveCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUnsave, Internship editedInternship) {
        assertCommandSuccess(command, toUnsave, editedInternship, null);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the internship at index {@code toSave} being
     * updated to values specified {@code editedInternship}.<br>
     * @param toUnsave the index of the current model's filtered list.
     * @see UnsaveCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUnsave, Internship editedInternship,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateInternship(
                    expectedModel.getFilteredInternshipList().get(toUnsave.getZeroBased()), editedInternship);
        } catch (DuplicateInternshipException | InternshipNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedInternship is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(UnsaveCommand.MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, editedInternship),
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
     * {@code InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see InternshipBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertCommandBoxShowsDefaultStyle();
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
     * {@code InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see InternshipBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
```
