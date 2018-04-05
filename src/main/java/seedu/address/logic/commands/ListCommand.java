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

    @Override
    public CommandResult execute() {
        ModelManager.setKeywords(new ArrayList<String>());
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        // remove all tags from filtered list except 'saved' tags
        try {
            ModelManager.removeTagsFromInternshipList(model.getFilteredInternshipList(), model);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
