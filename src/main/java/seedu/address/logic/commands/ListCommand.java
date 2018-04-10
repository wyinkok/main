package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;

/**
 * Lists all internships in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all internships";

    //@@author TanCiKang
    @Override
    public CommandResult execute() {
        ModelManager.setKeywords(new ArrayList<String>());
        model.updateSearchedInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        // remove all tags from filtered list except 'saved' tags
            ModelManager.removeTagsFromInternshipList(model.getFilteredInternshipList(), model);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
