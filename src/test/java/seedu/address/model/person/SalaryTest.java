package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SalaryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Salary(null));
    }

    @Test
    public void constructor_invalidSalary_throwsIllegalArgumentException() {
        String invalidSalary = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Salary(invalidSalary));
    }

    @Test
    public void isValidSalary() {
        // null salary
        Assert.assertThrows(NullPointerException.class, () -> Salary.isValidSalary(null));

        // invalid salary
        assertFalse(Salary.isValidSalary("")); // empty string
        assertFalse(Salary.isValidSalary(" ")); // spaces only
        assertFalse(Salary.isValidSalary("salary")); // non-numeric
        assertFalse(Salary.isValidSalary("11p0")); // alphabets within digits
        assertFalse(Salary.isValidSalary("10 4")); // spaces within digits
        assertFalse(Salary.isValidSalary("-1000")); // negative numbers
        assertFalse(Salary.isValidSalary("21.10")); // decimals points

        // valid salary
        assertTrue(Salary.isValidSalary("1"));
        assertTrue(Salary.isValidSalary("10"));
        assertTrue(Salary.isValidSalary("100"));
        assertTrue(Salary.isValidSalary("1000"));
        assertTrue(Salary.isValidSalary("10000"));
        assertTrue(Salary.isValidSalary("124293842033123")); // long numbers
    }
}
