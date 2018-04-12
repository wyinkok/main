# niloc94
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Filter the lists all internships in the internship according to all keyword arguments
 * Keyword matching is case insensitive
 *
 */

public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all internship from the list which contain the"
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "Marketing";

    public static final String MESSAGE_FILTER_RESPONSE = "How would you to sort your results by? You may sort by "
            + "Address, Industry, Location or Role. \nE.g sortby industry role region address";

    private final InternshipContainsAllKeywordsPredicate predicate;

    public FilterCommand(InternshipContainsAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredInternshipList(predicate);
        return new CommandResult(MESSAGE_FILTER_RESPONSE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sort and lists all Internships in the Internship List according to the order of the keywords given
 * Keyword matching is case insensitive.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String ALTERNATIVE_COMMAND_WORD = "sortby";
    private static final String SORT_SUCCESSS_MESSAGE = "Sort success! Here is your ideal list of internships. \n"
            + "If you would like to search, filter or sort again, "
            + "Type in the respective command word and the new things you want to search/filter/sort by. "
            + "Please note that redoing the above commands will clear your existing search.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all internships according "
            + "to the argument(s) given and displays them as a list with index numbers.\n"
            + "Maximum of 3 arguments will be sorted"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Industry Role Location";

    private final List<String> keywords;

    public SortCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.setComparator(keywords);
        return new CommandResult(SORT_SUCCESSS_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand); // instanceof handles nulls
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final String MESSAGE_INVALID_SORT_ATTRIBUTE = "Invalid attributes given! Possible attributes are : "
            + ATTRIBUTES_LIST;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        List<String> keywords = new ArrayList<>(Arrays.asList(nameKeywords));

        // Checks if keywords are proper internship attributes
        if (!keywords.stream().allMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(ATTRIBUTES_LIST, keyword))) {
            throw new ParseException(MESSAGE_INVALID_SORT_ATTRIBUTE);
        }

        return new SortCommand(keywords);
    }
}
```
###### \java\seedu\address\model\internship\Internship.java
``` java
    public String getValue(String keyword) {
        switch (keyword.toLowerCase()) {
        case "name":
            return getName().toString();

        case "salary":
            return getSalary().toString();

        case "address":
            return getAddress().toString();

        case "industry":
            return getIndustry().toString();

        case "region":
            return getLocation().toString();

        case "role":
            return getRole().toString();

        case "email":
            return getEmail().toString();

        default:
            assert false; // Keyword already parsed to attribute type. Program should never reach here
            throw new AssertionError();
        }
    }
}
```
###### \java\seedu\address\model\internship\InternshipContainsAllKeywordsPredicate.java
``` java
    @Override
    public boolean test(Internship internship) {
        // Check if all keyword can be found in all of a person's details (e.g name, contact number, address)
        return keywords.stream().allMatch(keyword ->
                StringUtil.containsWordIgnoreCase(internshipAttributeString(internship), keyword));
    }

```
###### \java\seedu\address\model\internship\InternshipContainsAllKeywordsPredicate.java
``` java
    /**
     * Helper method to collate all attributes of internship formats it for searching
     */
    private String internshipAttributeString(Internship internship) {
        // tags currently toString as [tagName], replace [] with whitespace for searching.
        // Also replaces commas with whitespace
        return new String(internship.toString().replaceAll("[\\[+\\]+\\,]", " "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipContainsAllKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsAllKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\internship\InternshipContainsKeywordsPredicate.java
``` java
    @Override
    public boolean test(Internship internship) {
        // Check if any of the keyword can be found in all of an internship's details (e.g name, industry, region)
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase
                (internshipAttributeString(internship), keyword));
    }

```
###### \java\seedu\address\model\internship\InternshipContainsKeywordsPredicate.java
``` java
    /**
     * Helper method to collate all attributes of internship formats it for searching
     */
    public String internshipAttributeString(Internship internship) {
        // tags currently toString as [tagName], replace [] with whitespace for searching.
        // Also replaces comma with whitespace
        return new String(internship.toString().replaceAll("[\\[+\\]+\\,]", " "));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InternshipContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((InternshipContainsKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setComparator(List<String> keywords) {
        Comparator<Internship> comparatorToSet = Sorter.makeComparator(keywords);
        sortedFilteredInternships.setComparator(comparatorToSet);
    }

    public static void setKeywords(List<String> keywords) {
        filterKeywords = keywords;
    }

    public static List<String> getKeywords() {
        return filterKeywords;
    }

    //=========== Add / Remove Tags Methods =============================================================

```
###### \java\seedu\address\model\util\Sorter.java
``` java
/**
 * Takes in keywords for attributes and creates comparator object for sorting
 */
public class Sorter {

    private static String attribute1;
    private static String attribute2;
    private static String attribute3;

    /**
     * Assigns keyword value to attribute
     * @param keywords
     */
    public static void assignValuesToAttributes(List<String> keywords) {
        attribute1 = keywords.get(0);
        if (keywords.size() == 2) {
            attribute2 = keywords.get(1);
            attribute3 = keywords.get(1);
        } else if (keywords.size() >= 3) {
            attribute2 = keywords.get(1);
            attribute3 = keywords.get(2);
        }
    }

    /**
     *  Creates a comparator which sort according objects according to three attributes entered by the user
     * @param keywords
     * @return
     */
    public static Comparator<Internship> makeComparator(List<String> keywords) {
        assignValuesToAttributes(keywords);
        return new Comparator<Internship>() {
            @Override
            public int compare(Internship o1, Internship o2) {
                if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0
                    && o1.getValue(attribute2).compareTo(o2.getValue(attribute2)) == 0) {
                    return (o1.getValue(attribute3).compareTo(o2.getValue(attribute3)));
                } else if (o1.getValue(attribute1).compareTo(o2.getValue(attribute1)) == 0) {
                    return (o1.getValue(attribute2).compareTo(o2.getValue(attribute2)));
                } else {
                    return o1.getValue(attribute1).compareTo(o2.getValue(attribute1));
                }
            }
        };
    }
}

```
