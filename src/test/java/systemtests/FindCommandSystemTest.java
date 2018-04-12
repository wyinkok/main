package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INTERNSHIPS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalInternships.BENSON;
import static seedu.address.testutil.TypicalInternships.CARL;
import static seedu.address.testutil.TypicalInternships.CARL2;
import static seedu.address.testutil.TypicalInternships.DANIEL;
import static seedu.address.testutil.TypicalInternships.FIONA;
import static seedu.address.testutil.TypicalInternships.KEYWORD_MATCHING_MEIER;

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
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        expectedModel.updateInternship(BENSON, addTag(removeAllTag(BENSON), KEYWORD_MATCHING_MEIER));
        expectedModel.updateInternship(DANIEL, addTag(removeAllTag(DANIEL), KEYWORD_MATCHING_MEIER));
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where internship list is displaying the internships we are finding
         * -> 2 internships found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship where internship list is not displaying the internship we are finding -> 1 internship
         * found
         */
        command = FindCommand.COMMAND_WORD + " Carl";
        expectedModel.updateInternship(CARL, addTag(removeAllTag(CARL), "Carl"));
        expectedModel.updateInternship(CARL2, addTag(removeAllTag(CARL2), "Carl"));
        ModelHelper.setFilteredList(expectedModel, CARL, CARL2);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple internships in address book, 2 keywords -> 2 internships found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        expectedModel.updateInternship(BENSON, addTag(removeAllTag(BENSON), "Benson"));
        expectedModel.updateInternship(DANIEL, addTag(removeAllTag(DANIEL), "Daniel"));
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple internships in address book, 2 keywords in reversed order -> 2 internships found */
        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple internships in address book, 2 keywords with 1 repeat -> 2 internships found */
        /* command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();*/

        /* Case: find multiple internships in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 internships found
         */
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
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
        command = FindCommand.COMMAND_WORD + " " + FIONA.getSalary().value;
        expectedModel.updateInternship(FIONA, addTag(removeAllTag(FIONA), FIONA.getSalary().toString()));
        ModelHelper.setFilteredList(expectedModel, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of internship in address book -> 1 internships found */
        command = FindCommand.COMMAND_WORD + " " + FIONA.getAddress().value;
        expectedModel.updateInternship(FIONA, addTag(removeAllTag(FIONA), FIONA.getAddress().value));
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of internship in address book -> 1 internships found */
        command = FindCommand.COMMAND_WORD + " " + FIONA.getEmail().value;
        expectedModel.updateInternship(FIONA, addTag(removeAllTag(FIONA), FIONA.getEmail().value));
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship in address book, keyword is same as name but of different case -> 2 internship found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        expectedModel.updateInternship(BENSON, addTag(removeAllTag(BENSON), "MeIeR"));
        expectedModel.updateInternship(DANIEL, addTag(removeAllTag(DANIEL), "MeIeR"));
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of internship in address book -> 0 internships found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship in address book, keyword is substring of name -> 0 internships found */
        command = FindCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship in address book, name is substring of keyword -> 0 internships found */
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find internship not in address book -> 0 internships found */
        command = FindCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a internship is selected -> selected card deselected */
        showAllInternships();
        selectInternship(Index.fromOneBased(1));
        assertFalse(getInternshipInternshipListPanel().getHandleToSelectedCard()
            .getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Daniel";
        expectedModel.updateInternship(DANIEL, addTag(removeAllTag(DANIEL), "Daniel"));
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: mixed case command word -> 2 person found */
        command = "FiNd Meier";
        expectedModel.updateInternship(BENSON, addTag(removeAllTag(BENSON), "Meier"));
        expectedModel.updateInternship(DANIEL, addTag(removeAllTag(DANIEL), "Meier"));
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
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

