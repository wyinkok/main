package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345678", "87654321"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345678"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654321").build()));
    }


    @Test
    public void test_addressContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Geylang"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Geylang", "#06-04"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Geylang", "Serangoon"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Mixed-case keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("GeYlAnG"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));
    }

    @Test
    public void test_addressDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Serangoon"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("charlotte@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("charlotte@example.com", "berniceyu@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));

        // Mixed-case keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("CharLotTe@exaMPle.CoM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("bereniceyu@example.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("charlotte@example.com").build()));
    }
}
