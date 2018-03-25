package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERNSHIPS;

/**
 * Lists all internships in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all internships";


    @Override
    public CommandResult execute() {
        model.updateFilteredInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
