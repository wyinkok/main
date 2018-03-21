package seedu.address.model.internship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class InternshipContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        InternshipContainsKeywordsPredicate firstPredicate =
                new InternshipContainsKeywordsPredicate(firstPredicateKeywordList);
        InternshipContainsKeywordsPredicate secondPredicate =
                new InternshipContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InternshipContainsKeywordsPredicate firstPredicateCopy =
                new InternshipContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different internship -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        InternshipContainsKeywordsPredicate predicate =
                new InternshipContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        InternshipContainsKeywordsPredicate predicate = new InternshipContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_salaryContainsKeywords_returnsTrue() {
        // One keyword
        InternshipContainsKeywordsPredicate predicate =
                new InternshipContainsKeywordsPredicate(Collections.singletonList("1000"));
        assertTrue(predicate.test(new PersonBuilder().withSalary("1000").build()));

        // Only one matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("1000", "2000"));
        assertTrue(predicate.test(new PersonBuilder().withSalary("1000").build()));
    }

    @Test
    public void test_salaryDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        InternshipContainsKeywordsPredicate predicate = new InternshipContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSalary("1000").build()));

        // Non-matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("1000"));
        assertFalse(predicate.test(new PersonBuilder().withSalary("2000").build()));
    }


    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        InternshipContainsKeywordsPredicate predicate =
                new InternshipContainsKeywordsPredicate(Collections.singletonList("Geylang"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Multiple keywords
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("Geylang", "#06-04"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Only one matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Mixed-case keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("GeYlAnG"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        InternshipContainsKeywordsPredicate predicate = new InternshipContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Non-matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("Serangoon"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        InternshipContainsKeywordsPredicate predicate =
                new InternshipContainsKeywordsPredicate(Collections.singletonList("charlotte@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));

        // Only one matching keyword
        predicate =
                new InternshipContainsKeywordsPredicate(Arrays.asList("charlotte@example.com", "berniceyu@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));

        // Mixed-case keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("CharLotTe@exaMPle.CoM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        InternshipContainsKeywordsPredicate predicate = new InternshipContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));

        // Non-matching keyword
        predicate = new InternshipContainsKeywordsPredicate(Arrays.asList("bereniceyu@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));
    }
}
