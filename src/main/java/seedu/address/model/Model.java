package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicatePersonException;
import seedu.address.model.internship.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Internship> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given internship. */
    void deletePerson(Internship target) throws PersonNotFoundException;

    /** Adds the given internship */
    void addPerson(Internship internship) throws DuplicatePersonException;

    /**
     * Replaces the given internship {@code target} with {@code editedInternship}.
     *
     * @throws DuplicatePersonException if updating the internship's details causes the internship to be equivalent to
     *      another existing internship in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Internship target, Internship editedInternship)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered internship list */
    ObservableList<Internship> getFilteredPersonList();

    /**
     * Updates the filter of the filtered internship list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Internship> predicate);

}
