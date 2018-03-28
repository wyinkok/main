package seedu.address.model.internship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;

/**
 * A list of internships that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Internship#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueInternshipList implements Iterable<Internship> {

    private final ObservableList<Internship> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent internship as the given argument.
     */
    public boolean contains(Internship toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a internship to the list.
     *
     * @throws DuplicateInternshipException if the internship to add is a duplicate of an existing internship in the
     * list.
     */
    public void add(Internship toAdd) throws DuplicateInternshipException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInternshipException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the internship {@code target} in the list with {@code editedInternship}.
     *
     * @throws DuplicateInternshipException if the replacement is equivalent to another existing internship in the list.
     * @throws InternshipNotFoundException if {@code target} could not be found in the list.
     */
    public void setInternship(Internship target, Internship editedInternship)
            throws DuplicateInternshipException, InternshipNotFoundException {
        requireNonNull(editedInternship);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InternshipNotFoundException();
        }

        if (!target.equals(editedInternship) && internalList.contains(editedInternship)) {
            throw new DuplicateInternshipException();
        }

        internalList.set(index, editedInternship);
    }

    /**
     * Removes the equivalent internship from the list.
     *
     * @throws InternshipNotFoundException if no such internship could be found in the list.
     */
    public boolean remove(Internship toRemove) throws InternshipNotFoundException {
        requireNonNull(toRemove);
        final boolean internshipFoundAndDeleted = internalList.remove(toRemove);
        if (!internshipFoundAndDeleted) {
            throw new InternshipNotFoundException();
        }
        return internshipFoundAndDeleted;
    }

    public void setInternships(UniqueInternshipList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setInternships(List<Internship> internships) throws DuplicateInternshipException {
        requireAllNonNull(internships);
        final UniqueInternshipList replacement = new UniqueInternshipList();
        for (final Internship internship : internships) {
            replacement.add(internship);
        }
        setInternships(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Internship> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Internship> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueInternshipList // instanceof handles nulls
                        && this.internalList.equals(((UniqueInternshipList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
