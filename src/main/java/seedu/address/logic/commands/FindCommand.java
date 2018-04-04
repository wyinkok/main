package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_INTERNSHIP;

/**
 * Finds and lists all Internships in address book whose name, address, salary, email or industry contains any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String ALTERNATIVE_COMMAND_WORD = "search";
    private Internship internshipWithKeywordTags;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all internships whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final InternshipContainsKeywordsPredicate predicate;

    public FindCommand(InternshipContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    private Internship addTagsToInternshipWithMatch(String keyword, Internship internship) throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());

        try {
            internshipTags.add(new Tag(keyword));
        } catch (UniqueTagList.DuplicateTagException e){
            throw new CommandException ("Operation would result in duplicate tags");
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                internship.getIndustry(), internship.getLocation(), internship.getRole(), correctTagReferences);
    }

    private void addTagsToFilteredList (List<String> filterKeywords, ObservableList<Internship> filteredInternships)
    throws CommandException{

        for (String keywords : filterKeywords){
            for (Internship filteredInternship : filteredInternships){
                if(StringUtil.containsWordIgnoreCase(filteredInternship.toString(), keywords)) {
                    internshipWithKeywordTags = addTagsToInternshipWithMatch(keywords, filteredInternship);
                    try {
                        model.updateInternship(filteredInternship, internshipWithKeywordTags);
                    }catch (DuplicateInternshipException e) {
                        throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
                    } catch (InternshipNotFoundException e) {
                        throw new AssertionError("The target internship cannot be missing");
                    }
                }
           }
        }
        return;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredInternshipList(predicate);

        CommandResult returnValue =
                new CommandResult(getMessageForInternshipListShownSummary(model.getFilteredInternshipList().size()));
        try {
            addTagsToFilteredList(ModelManager.getKeywords(), model.getFilteredInternshipList());
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
