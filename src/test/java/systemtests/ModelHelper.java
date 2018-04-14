package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.internship.Internship;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Internship> PREDICATE_MATCHING_NO_INTERNSHIPS = unused -> false;

    /**
     * Updates {@code model}'s searched list to display only {@code toDisplay}.
     */
    public static void setSearchedList(Model model, List<Internship> toDisplay) {
        Optional<Predicate<Internship>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateSearchedInternshipList(predicate.orElse(PREDICATE_MATCHING_NO_INTERNSHIPS));
    }

    /**
     * @see ModelHelper#setSearchedList(Model, List)
     */
    public static void setSearchedList(Model model, Internship... toDisplay) {
        setSearchedList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Internship> toDisplay) {
        Optional<Predicate<Internship>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredInternshipList(predicate.orElse(PREDICATE_MATCHING_NO_INTERNSHIPS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Internship... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Internship} equals to {@code other}.
     */
    private static Predicate<Internship> getPredicateMatching(Internship other) {
        return internship -> internship.equals(other);
    }
}
