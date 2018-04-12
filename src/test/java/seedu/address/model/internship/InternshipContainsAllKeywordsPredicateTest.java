package seedu.address.model.internship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.InternshipBuilder;

//@@author niloc94
public class InternshipContainsAllKeywordsPredicateTest {

    public static final Internship PREDICATE_TEST_INTERNSHIP = new InternshipBuilder()
            .withName("Alice Bob Charlie Company").withSalary("1500").withEmail("ABC@example.com")
            .withAddress("ABC Building").withTags("saved")
            .withIndustry("Tech").withRegion("Town").withRole("Data Scientist").build();

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        InternshipContainsAllKeywordsPredicate firstPredicate =
                new InternshipContainsAllKeywordsPredicate(firstPredicateKeywordList);
        InternshipContainsAllKeywordsPredicate secondPredicate =
                new InternshipContainsAllKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InternshipContainsAllKeywordsPredicate firstPredicateCopy =
                new InternshipContainsAllKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different internship -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ContainsAllKeywords_returnsTrue() {
        // One keyword
        InternshipContainsAllKeywordsPredicate predicate =
                new InternshipContainsAllKeywordsPredicate(Collections.singletonList("Charlie"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Collections.singletonList("ABC"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Collections.singletonList("Data"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // Multiple keywords
        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Alice", "Town", "saved"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Data", "Scientist"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Data", "Tech", "ABC"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // Mixed-case keywords
        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("DaTa", "ToWn", "aBc"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("AbC@Example.com", "TECH"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // Zero keywords
        predicate = new InternshipContainsAllKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));
    }

    @Test
    public void test_DoesNotContainAllKeywords_returnsFalse() {
        // One non-matching keyword
        InternshipContainsAllKeywordsPredicate predicate =
                new InternshipContainsAllKeywordsPredicate(Arrays.asList("NonMatchingKeyword"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // Substrings
        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Alic"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Alice1"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // One non-matching keyword
        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Bob", "Dick"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Tech", "NonMatchingKeyword"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("ABC", "NonMatchingKeyword"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("NonMatchingKeyword", "Tech", "Alice"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Alice", "Bob", "Tech",
                "NonMatchingKeyword"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // All non-matching keyword
        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Alicia", "Bobby", "Techno"));
        assertFalse(predicate.test(PREDICATE_TEST_INTERNSHIP));
    }

    @Test
    public void test_canHandleNonAlphaNumericKeywords_returnsTrue() {
        // Can Handle Commas, [], and both
        InternshipContainsAllKeywordsPredicate predicate =
                new InternshipContainsAllKeywordsPredicate(Arrays.asList("Street"));
        assertTrue(predicate.test(new InternshipBuilder().withAddress("Street,").build()));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("saved"));
        assertTrue(predicate.test(new InternshipBuilder().withTags("[saved]").build()));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Street", "saved"));
        assertTrue(predicate.test(new InternshipBuilder().withAddress("Street,").withTags("[saved]").build()));
    }
}
