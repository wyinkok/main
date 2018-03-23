package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.UniqueInternshipList;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueInternshipList internships;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        internships = new UniqueInternshipList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Internships and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setInternships(List<Internship> internships) throws DuplicateInternshipException {
        this.internships.setInternships(internships);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Internship> syncedInternshipList = newData.getInternshipList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setInternships(syncedInternshipList);
        } catch (DuplicateInternshipException e) {
            throw new AssertionError("AddressBooks should not have duplicate internships");
        }
    }

    //// internship-level operations

    /**
     * Adds a internship to the address book.
     * Also checks the new internship's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the internship to point to those in {@link #tags}.
     *
     * @throws DuplicateInternshipException if an equivalent internship already exists.
     */
    public void addInternship(Internship p) throws DuplicateInternshipException {
        Internship internship = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any internship
        // in the internship list.
        internships.add(internship);
    }

    /**
     * Replaces the given internship {@code target} in the list with {@code editedInternship}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedInternship}.
     *
     * @throws DuplicateInternshipException if updating the internship's details causes the internship to be equivalent
     * to another existing internship in the list.
     * @throws InternshipNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Internship)
     */
    public void updateInternship(Internship target, Internship editedInternship)
            throws DuplicateInternshipException, InternshipNotFoundException {
        requireNonNull(editedInternship);

        Internship syncedEditedInternship = syncWithMasterTagList(editedInternship);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any internship
        // in the internship list.
        internships.setInternship(target, syncedEditedInternship);
    }

    /**
     *  Updates the master tag list to include tags in {@code internship} that are not in the list.
     *  @return a copy of this {@code internship} such that every tag in this internship points to a Tag object in the
     *  master list.
     */
    private Internship syncWithMasterTagList(Internship internship) {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());
        tags.mergeFrom(internshipTags);

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws InternshipNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeInternship(Internship key) throws InternshipNotFoundException {
        if (internships.remove(key)) {
            return true;
        } else {
            throw new InternshipNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return internships.asObservableList().size() + " internships, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Internship> getInternshipList() {
        return internships.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.internships.equals(((AddressBook) other).internships)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(internships, tags);
    }
}
