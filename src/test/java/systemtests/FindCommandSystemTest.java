package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INTERNSHIPS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalInternships.BUSINESS1;
import static seedu.address.testutil.TypicalInternships.BUSINESS2;
import static seedu.address.testutil.TypicalInternships.BUSINESS3;
import static seedu.address.testutil.TypicalInternships.BUSINESS4;
import static seedu.address.testutil.TypicalInternships.DATASCIENCE;
import static seedu.address.testutil.TypicalInternships.ENGINEERING1;
import static seedu.address.testutil.TypicalInternships.ENGINEERING2;
import static seedu.address.testutil.TypicalInternships.ENGINEERING3;
import static seedu.address.testutil.TypicalInternships.KEYWORD_MATCHING_AUDIT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.internship.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class FindCommandSystemTest extends JobbiBotSystemTest {

    @Test
    public void find() throws CommandException, DuplicateInternshipException, InternshipNotFoundException,
            UniqueTagList.DuplicateTagException {
        /* Case: find multiple internships in address book, command with leading spaces and trailing spaces
         * -> 2 internships found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AUDIT + "   ";
        Model expectedModel = getModel();
        expectedModel.updateInternship(BUSINESS2, addTag(removeAllTag(BUSINESS2), KEYWORD_MATCHING_AUDIT));
        expectedModel.updateInternship(BUSINESS4, addTag(removeAllTag(BUSINESS4), KEYWORD_MATCHING_AUDIT));
        ModelHelper.setSearchedList(expectedModel, BUSINESS2, BUSINESS4);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where internship list is displaying the internships we are finding
         * -> 2 internships found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AUDIT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship where internship list is not displaying the internship we are finding -> 1 internship
         * found
         */
        command = FindCommand.COMMAND_WORD + " Data";
        expectedModel.updateInternship(DATASCIENCE, addTag(removeAllTag(DATASCIENCE), "Data"));
        ModelHelper.setSearchedList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship with 1 repeated keyword entered -> 1 internship found */
        command = FindCommand.COMMAND_WORD + " Data Data";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple internships in address book, 2 keywords -> 2 internships found */
        command = FindCommand.COMMAND_WORD + " Data Consulting";
        expectedModel.updateInternship(DATASCIENCE, addTag(removeAllTag(DATASCIENCE), "Data"));
        expectedModel.updateInternship(BUSINESS3, addTag(removeAllTag(BUSINESS3), "Consulting"));
        ModelHelper.setSearchedList(expectedModel, DATASCIENCE, BUSINESS3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple internships in address book, 2 keywords in reversed order -> 2 internships found */
        command = FindCommand.COMMAND_WORD + " Consulting Data";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple internships in address book, 2 keywords with 1 repeat -> 2 internships found */
        command = FindCommand.COMMAND_WORD + " Consulting Data Data";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple internships in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 internships found
         */
        command = FindCommand.COMMAND_WORD + " Data Consulting NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find salary of internship in address book -> 1 internships found */
        command = FindCommand.COMMAND_WORD + " " + "999";
        expectedModel.updateInternship(ENGINEERING2, addTag(removeAllTag(ENGINEERING2), "999"));
        ModelHelper.setSearchedList(expectedModel, ENGINEERING2);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of internship in address book -> 1 internships found */
        command = FindCommand.COMMAND_WORD + " " + "Boulevard";
        expectedModel.updateInternship(BUSINESS3, addTag(removeAllTag(BUSINESS3), "Boulevard"));
        ModelHelper.setSearchedList(expectedModel, BUSINESS3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of internship in address book -> 1 internships found */
        command = FindCommand.COMMAND_WORD + " " + BUSINESS3.getEmail().value;
        expectedModel.updateInternship(BUSINESS3, addTag(removeAllTag(BUSINESS3), BUSINESS3.getEmail().value));
        ModelHelper.setSearchedList(expectedModel, BUSINESS3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship in address book, keyword is same as name but of different case -> 3 internship found */
        command = FindCommand.COMMAND_WORD + " EnGinEeRing";
        expectedModel.updateInternship(ENGINEERING1, addTag(removeAllTag(ENGINEERING1), "EnGinEeRing"));
        expectedModel.updateInternship(ENGINEERING2, addTag(removeAllTag(ENGINEERING2), "EnGinEeRing"));
        expectedModel.updateInternship(ENGINEERING3, addTag(removeAllTag(ENGINEERING3), "EnGinEeRing"));
        ModelHelper.setSearchedList(expectedModel, ENGINEERING1, ENGINEERING2, ENGINEERING3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> 3 internship found */
        command = "FiNd Engineering";
        expectedModel.updateInternship(ENGINEERING1, addTag(removeAllTag(ENGINEERING1), "Engineering"));
        expectedModel.updateInternship(ENGINEERING2, addTag(removeAllTag(ENGINEERING2), "Engineering"));
        expectedModel.updateInternship(ENGINEERING3, addTag(removeAllTag(ENGINEERING3), "Engineering"));
        ModelHelper.setSearchedList(expectedModel, ENGINEERING1, ENGINEERING2, ENGINEERING3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of internship in address book -> 0 internships found */
        List<Tag> tags = new ArrayList<>(BUSINESS1.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        expectedModel.updateInternship(BUSINESS1, addTag(removeAllTag(BUSINESS1), tags.get(0).tagName));
        ModelHelper.setSearchedList(expectedModel, BUSINESS1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship in address book, keyword is substring of name -> 0 internships found */
        command = FindCommand.COMMAND_WORD + " Engin";
        ModelHelper.setSearchedList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship in address book, name is substring of keyword -> 0 internships found */
        command = FindCommand.COMMAND_WORD + " Engineerings";
        ModelHelper.setSearchedList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship not in address book -> 0 internships found */
        command = FindCommand.COMMAND_WORD + " Analytics";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a internship is selected -> selected card deselected */
        showAllInternships();
        selectInternship(Index.fromOneBased(1));
        assertFalse(getInternshipListPanel().getHandleToSelectedCard()
            .getName().equals(BUSINESS3.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Consulting";
        expectedModel.updateInternship(BUSINESS3, addTag(removeAllTag(BUSINESS3), "Consulting"));
        ModelHelper.setSearchedList(expectedModel, BUSINESS3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

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
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Remove all tags from internship
     * @param internshipToCopy
     * @return Intership without tags
     * @throws CommandException
     */
    public Internship removeAllTag(Internship internshipToCopy)throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internshipToCopy.getTags());
        final UniqueTagList internshipTagsCopy = new UniqueTagList(internshipToCopy.getTags());

        for (Tag nameTag : internshipTagsCopy) {
            try {
                internshipTags.delete(new Tag(nameTag.getTagName()));
            } catch (TagNotFoundException e) {
                throw new CommandException("Tag to be deleted not found");
            }
        }

        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internshipToCopy.getName(),
                internshipToCopy.getSalary(),
                internshipToCopy.getEmail(),
                internshipToCopy.getAddress(),
                internshipToCopy.getIndustry(),
                internshipToCopy.getRegion(),
                internshipToCopy.getRole(),
                correctTagReferences);
    }

    /**
     * Add tag to internship
     * @param internshipToCopy
     * @param tagNames
     * @return internship with tag
     * @throws UniqueTagList.DuplicateTagException
     */
    public Internship addTag(Internship internshipToCopy, String tagNames)throws UniqueTagList.DuplicateTagException {
        final UniqueTagList internshipTags = new UniqueTagList(internshipToCopy.getTags());

        for (String tagName : tagNames.split("\\s+")) {
            internshipTags.add(new Tag(tagName));
        }

        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internshipToCopy.getName(),
                internshipToCopy.getSalary(),
                internshipToCopy.getEmail(),
                internshipToCopy.getAddress(),
                internshipToCopy.getIndustry(),
                internshipToCopy.getRegion(),
                internshipToCopy.getRole(),
                correctTagReferences);
    }
}

