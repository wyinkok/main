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
            + "Example: " + COMMAND_WORD + " Marketing";

    public static final String MESSAGE_FILTER_RESPONSE = "How would you to sort your results by? You may only sort by "
            + "Industry Region Name Role Salary \n\nE.g sort industry role salary";

    public static final String MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP = "No internships found ! You may want to try "
            + "using lesser keywords or change your keywords \n"
            + "E.g filter Central";

    private final InternshipContainsAllKeywordsPredicate predicate;

    public FilterCommand(InternshipContainsAllKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredInternshipList(predicate);
        return getCommandResult();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }

    /**
     * Helper method to retrieve the correct message for command results
     * @return
     */
    private CommandResult getCommandResult() {
        if (model.getFilteredInternshipList().size() > 0) {
            return new CommandResult(MESSAGE_FILTER_RESPONSE);
        } else {
            return new CommandResult(MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP);
        }
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

    public static final String SORT_SUCCESSS_MESSAGE = "Sort success! Here is your ideal list of internships. \n\n"
            + "If you would like to search, filter or sort again, "
            + "key in the respective command word(s) and the new thing(s) you would like to search/filter/sort. "
            + "Please note that redoing the above commands will clear your existing search.";

    public static final String NOTHING_TO_SORT_MESSAGE = "No internships to sort! ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all internships according "
            + "to the argument(s) given and displays them as a list with index numbers.\n"
            + "Up to 3 arguments will be sorted."
            + "Negative valid arguments (e.g sort -industry) sorts in reverse orders\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Industry Role Region";

    private final List<String> keywords;

    public SortCommand(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.setComparator(keywords);
        return getCommandResult();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand) // instanceof handles nulls
                && this.keywords.equals(((SortCommand) other).keywords);
    }

    /**
     * Helper method to retrieve the correct message for command results
     * @return
     */
    private CommandResult getCommandResult() {
        if (model.getFilteredInternshipList().size() > 0) {
            return new CommandResult(SORT_SUCCESSS_MESSAGE);
        } else {
            return new CommandResult(NOTHING_TO_SORT_MESSAGE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        // Create a unique (no duplicates) list of keywords, using the Sets Collection
        ArrayList<String> uniqueKeywords = new ArrayList<>(new HashSet<>(Arrays.asList(nameKeywords)));

        ModelManager.setKeywords(uniqueKeywords);

        return new FindCommand(new InternshipContainsKeywordsPredicate(uniqueKeywords));
    }
}
```
###### \java\seedu\address\logic\parser\InternshipBookParser.java
``` java
        case SortCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new SortCommandParser().parse(arguments);

        //=========== Command without arguments =============================================================

        case ListCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            checkIfConversationRestarted();
            return new RedoCommand();

```
###### \java\seedu\address\logic\parser\InternshipBookParser.java
``` java
    /**
     *  Helper method to check if commands without arguments have arguments added to it
     * @throws ParseException
     */
    private void checkIfContainArguments(String arguments) throws ParseException {
        if (!arguments.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String MESSAGE_INVALID_SORT_ATTRIBUTE = "Invalid attributes given! Possible attributes are : "
            + SORTABLE_ATTRIBUTES_LIST;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     *
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

        // Checks if keywords are proper internship attributes, can contain -attribute for reverse sort
        if (!keywords.stream().allMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(SORTABLE_ATTRIBUTES_LIST_WITH_NEGATIVE, keyword))) {
            throw new ParseException(MESSAGE_INVALID_SORT_ATTRIBUTE);
        }

        return new SortCommand(keywords);
    }
}
```
###### \java\seedu\address\model\internship\Internship.java
``` java
    /**
     * Helper method to return an Internship attributes string value
     * Negative attributes are for use with sorter class
     */
    public String getValue(String keyword) {
        switch (keyword.toLowerCase()) {
        case "name":
        case "-name":
            return getName().toString();

        case "salary":
        case "-salary":
            return getSalary().toString();

        case "industry":
        case "-industry":
            return getIndustry().toString();

        case "region":
        case "-region":
            return getRegion().toString();

        case "role":
        case "-role":
            return getRole().toString();

        case "address":
        case "-address":
            return getAddress().toString();

        case "email":
        case "-email":
            return getEmail().toString();

        default:
            assert false; // Keyword already parsed to attribute type. Program should never reach here
            throw new AssertionError();
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
        return internship.toString().replaceAll("[\\[+\\]+\\,]", " ");
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
        // Check if any of the keyword can be found in all of an internship's details (e.g name, industry, location)
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
        return internship.toString().replaceAll("[\\[+\\]+\\,]", " ");
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

    public static void setKeywords(List<String> uniqueKeywords) {
        filterKeywords = uniqueKeywords;
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
     *
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
        } else {
            attribute2 = keywords.get(0);
            attribute3 = keywords.get(0);
        }
    }

    /**
     * Helper method to compare string and integer values in attributes
     * String values are compareTo each other without case sensitivity
     * Integer values (salary) are compareTo each other to sort from highest to lowest
     * '-' char at the start of attributeToCompare flips the order of the Internship objects being compared
     *
     * @param attributeToCompare the internship attribute to compare
     * @param o1 internship object 1
     * @param o2 internship object 2
     * @return
     */
    private static int attributeCompare(Internship o1, Internship o2, String attributeToCompare) {
        if (attributeToCompare.equalsIgnoreCase("salary")) {
            return new Integer(Integer.parseInt(o2.getValue(attributeToCompare)))
                    .compareTo(new Integer(Integer.parseInt(o1.getValue(attributeToCompare))));
        } else if (attributeToCompare.equalsIgnoreCase("-salary")) {
            return new Integer(Integer.parseInt(o1.getValue(attributeToCompare)))
                    .compareTo(new Integer(Integer.parseInt(o2.getValue(attributeToCompare))));
        } else if (attributeToCompare.charAt(0) == '-') {
            return o2.getValue(attributeToCompare).toLowerCase()
                    .compareTo(o1.getValue(attributeToCompare).toLowerCase());
        } else {
            return o1.getValue(attributeToCompare).toLowerCase()
                    .compareTo(o2.getValue(attributeToCompare).toLowerCase());
        }
    }

    /**
     * Creates a comparator which sort according objects according to three attributes entered by the user
     * Keyword attributes are sorted from A-Z by default (ignoring case sensitivity)
     * Integer values are sorted from highest to lowest by default
     * For keywords with '-' at the start of that keyword attribute,
     * the reverses sort order for that attribute is applied
     *
     * @param keywords the list of string keys entered by the user
     * @return a custom comparator which compare two Internship Object according to their the keyword attributes given
     */
    public static Comparator<Internship> makeComparator(List<String> keywords) {
        assignValuesToAttributes(keywords);
        return new Comparator<Internship>() {
            @Override
            public int compare(Internship o1, Internship o2) {
                if (attributeCompare(o1, o2, attribute1) == 0 && attributeCompare(o1, o2, attribute2) == 0) {
                    return attributeCompare(o1, o2, attribute3);
                } else if (attributeCompare(o1, o2, attribute1) == 0 && attributeCompare(o1, o2, attribute2) != 0) {
                    return attributeCompare(o1, o2, attribute2);
                } else {
                    return attributeCompare(o1, o2, attribute1);
                }
            }
        };
    }
}


```
