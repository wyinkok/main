package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.SaveCommand.MESSAGE_DUPLICATE_SAVED_INTERNSHIP;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.JobbiBotChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.util.Sorter;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static List<String> filterKeywords;
    private final JobbiBot jobbiBot;
    private final FilteredList<Internship> searchedInternships;
    private final FilteredList<Internship> filteredInternships;
    private final SortedList<Internship> sortedFilteredInternships;

    /**
     * Initializes a ModelManager with the given jobbiBot and userPrefs.
     */
    public ModelManager(ReadOnlyJobbiBot internshipBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(internshipBook, userPrefs);

        logger.fine("Initializing with internship book: " + internshipBook + " and user prefs " + userPrefs);

        this.jobbiBot = new JobbiBot(internshipBook);
        this.searchedInternships = new FilteredList<>(this.jobbiBot.getInternshipList());
        this.filteredInternships = new FilteredList<>(searchedInternships);
        this.sortedFilteredInternships = new SortedList<>(filteredInternships);
        filterKeywords = new ArrayList<>();
    }

    public ModelManager() {
        this(new JobbiBot(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyJobbiBot newData) {
        jobbiBot.resetData(newData);
        indicateInternshipBookChanged();
    }

    @Override
    public ReadOnlyJobbiBot getJobbiBot() {
        return jobbiBot;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateInternshipBookChanged() {
        raise(new JobbiBotChangedEvent(jobbiBot));
    }

    @Override
    public void updateInternship(Internship target, Internship editedInternship)
            throws DuplicateInternshipException, InternshipNotFoundException {
        requireAllNonNull(target, editedInternship);

        jobbiBot.updateInternship(target, editedInternship);
        indicateInternshipBookChanged();
    }

    //@@author niloc94
    @Override
    public void setComparator(List<String> keywords) {
        Comparator<Internship> comparatorToSet = Sorter.makeComparator(keywords);
        sortedFilteredInternships.setComparator(comparatorToSet);
    }

    public static void setKeywords(List<String> uniqueKeywords) {
        filterKeywords = uniqueKeywords;
    }

    public static List<String> getKeywords() {
        return filterKeywords;
    }

    //=========== Add / Remove Tags Methods =============================================================

    //@@author TanCiKang
    @Override
    /**
     * Add keyword tags that matches the internship to the list of filteredInternships
     */
    public void addTagsToFilteredList() {
        for (String keyword: filterKeywords) {
            filteredInternships.forEach(filteredInternship -> {
                if (StringUtil.containsWordIgnoreCase(filteredInternship.toString(), keyword)) {
                    try {
                        updateInternship(filteredInternship, filteredInternship.addTagsToInternship(keyword));
                    } catch (InternshipNotFoundException e) {
                        throw new AssertionError("The target internship cannot be missing");
                    } catch (DuplicateInternshipException e) {
                        throw new AssertionError(MESSAGE_DUPLICATE_SAVED_INTERNSHIP);
                    }
                }
            });
        }
    }

    //@@author TanCiKang
    @Override
    /**
     * Remove all tags that are not 'saved' from the filtered list
     *
     */
    public void removeTagsFromFilteredList() {
        for (Internship internship : getFilteredInternshipList()) {
            try {
                updateInternship(internship, internship.removeTagsFromInternship());
            } catch (DuplicateInternshipException e) {
                throw new AssertionError(MESSAGE_DUPLICATE_SAVED_INTERNSHIP);
            } catch (InternshipNotFoundException e) {
                throw new AssertionError("The target internship cannot be missing");
            }
        }
    }

    //@@author TanCiKang
    @Override
    /**
     * Remove all tags from the internship list with the exception of saved tag
     * @param model
     */
    public void removeTagsFromAllInternshipList() {
        updateSearchedInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        removeTagsFromFilteredList();
    }

    //=========== Filtered Internship List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Internship} backed by the internal list of
     * {@code jobbiBot}
     */
    @Override
    public ObservableList<Internship> getFilteredInternshipList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredInternships);
    }

    @Override
    public void updateFilteredInternshipList(Predicate<Internship> predicate) {
        requireNonNull(predicate);
        filteredInternships.setPredicate(predicate);
        logger.info("Updating only Filtered Internship List");
    }

    @Override
    public void updateSearchedInternshipList(Predicate<Internship> predicate) {
        requireNonNull(predicate);
        searchedInternships.setPredicate(predicate);
        filteredInternships.setPredicate(predicate);
        logger.info("Updating both Searched Internship List and Filtered Internship List");
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return jobbiBot.equals(other.jobbiBot)
                && filteredInternships.equals(other.filteredInternships);
    }
}
