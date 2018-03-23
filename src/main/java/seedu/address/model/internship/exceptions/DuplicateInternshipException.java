package seedu.address.model.internship.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Internship objects.
 */
public class DuplicateInternshipException extends DuplicateDataException {
    public DuplicateInternshipException() {
        super("Operation would result in duplicate internships");
    }
}
