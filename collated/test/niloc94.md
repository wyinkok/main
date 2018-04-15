# niloc94
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalInternshipBook(), new UserPrefs());

    @Test
    public void equals() {
        InternshipContainsAllKeywordsPredicate firstPredicate =
                new InternshipContainsAllKeywordsPredicate(Collections.singletonList("first"));
        InternshipContainsAllKeywordsPredicate secondPredicate =
                new InternshipContainsAllKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> return true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_command() {
        // execute_singleKeywords_zeroInternshipsFound
        String expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP);
        FilterCommand command = prepareCommand("TryFindingThis");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        //execute_multipleKeywords_zeroInternshipsFound
        expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE_NO_INTERNSHIP);
        command = prepareCommand("Engineering Business");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        //execute_singleKeyword_multipleInternshipsFound()
        expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE);
        command = prepareCommand("Business");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BUSINESS1, BUSINESS2, BUSINESS3, BUSINESS4));

        // execute_multipleKeywords_singleInternshipsFound()
        expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE);
        command = prepareCommand("Data Scientist");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DATASCIENCE));

        // execute_multipleKeywords_multipleInternshipsFound()
        expectedMessage = String.format(FilterCommand.MESSAGE_FILTER_RESPONSE);
        command = prepareCommand("Audit Intern");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BUSINESS2, BUSINESS4));
    }

    /**
     * Parses {@code userInput} into a {@code FilterCommand}.
     */
    private FilterCommand prepareCommand(String userInput) {
        FilterCommand command =
                new FilterCommand(
                        new InternshipContainsAllKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code JobbiBot} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage, List<Internship> expectedList) {
        JobbiBot expectedJobbiBot = new JobbiBot(model.getJobbiBot());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInternshipList());
        assertEquals(expectedJobbiBot, model.getJobbiBot());
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalInternshipForSorting(), new UserPrefs());

    @Test
    public void equals() {
        List<String> keywordsSet1 = new ArrayList<>(Arrays.asList("Name", "Role", "Industry"));
        List<String> keywordsSet2 = new ArrayList<>(Arrays.asList("Role", "Industry", "Region"));

        SortCommand firstSortCommand = new SortCommand(keywordsSet1);
        SortCommand secondSortCommand = new SortCommand(keywordsSet2);

        // same object -> returns true
        assertTrue(firstSortCommand.equals(firstSortCommand));

        // same values -> return true
        SortCommand firstSortCommandCopy = new SortCommand(keywordsSet1);
        assertTrue(firstSortCommand.equals(firstSortCommandCopy));

        // different types -> returns false
        assertFalse(firstSortCommand.equals(1));

        // null -> returns false
        assertFalse(firstSortCommand.equals(null));

        // different keywords -> returns false
        assertFalse(firstSortCommand.equals(secondSortCommand));
    }

    @Test
    public void sort() {
        // Salary keyword
        String expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        SortCommand command = prepareCommand("salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN4, IN5));

        // One keyword, non-salary attribute
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN4, IN5));

        // One keyword with negative attribute
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN5, IN4, IN3, IN2, IN1));

        // Two keyword
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Two repeated keyword
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Two keyword, negative of each other
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -role");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Two keywords, one negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("region -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN5, IN4));

        // Two Negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-region -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN5, IN4, IN3, IN2, IN1));

        // Three keyword
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // Three keyword, one negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -industry name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN5, IN4));

        // Three keyword, two negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -industry -name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN4, IN5));

        // Three keyword, three negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-role -industry -name");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN3, IN4, IN5, IN2, IN1));

        // More than three keywords
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry name salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // More than three keywords, all negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("-role -industry -name -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN3, IN4, IN5, IN2, IN1));

        // More than three keywords, 1 negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role industry name -salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN5, IN4, IN3));

        // More than three keywords, 2 negative
        expectedMessage = String.format(SortCommand.SORT_SUCCESSS_MESSAGE);
        command = prepareCommand("role -industry name salary");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(IN1, IN2, IN3, IN5, IN4));
    }

    /**
     * Parses {@code userInput} into a {@code SortCommand}.
     */
    private SortCommand prepareCommand(String arguments) {
        List<String> keywords = new ArrayList<>(Arrays.asList(arguments.split("\\s+")));
        SortCommand command = new SortCommand(keywords);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code JobbiBot} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<Internship> expectedList) {
        JobbiBot expectedJobbiBot = new JobbiBot(model.getJobbiBot());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInternshipList());
        assertEquals(expectedJobbiBot, model.getJobbiBot());
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces
        SortCommand expectedSortCommand =
                new SortCommand(Arrays.asList("role", "industry", "salary"));
        assertParseSuccess(parser, " role industry salary", expectedSortCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n role \n \t industry  \n \t salary", expectedSortCommand);

        // valid negative argument words
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "-name", "-salary"));
        assertParseSuccess(parser, " -role -name -salary", expectedSortCommand);

        // valid negative and non negative argument words
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "region", "-salary"));
        assertParseSuccess(parser, " -role region -salary", expectedSortCommand);

        // valid repeat
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "-role", "-role"));
        assertParseSuccess(parser, " -role -role -role", expectedSortCommand);

        // valid three arguments with 1 repeat
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "industry", "-role"));
        assertParseSuccess(parser, " -role industry -role", expectedSortCommand);

        // valid one argument
        expectedSortCommand = new SortCommand(Arrays.asList("-role"));
        assertParseSuccess(parser, " -role", expectedSortCommand);

        // valid two argument
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "name"));
        assertParseSuccess(parser, " -role name", expectedSortCommand);

        // valid >3 argument
        expectedSortCommand = new SortCommand(Arrays.asList("-role", "name", "salary", "role"));
        assertParseSuccess(parser, " -role name salary role", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // 1 Invalid Argument
        assertParseFailure(parser, " invalid", SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        // Mix of valid and invalid arguments
        assertParseFailure(parser, " invalid role name",
                SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        // Invalid mix of valid and invalid arguments with '-'
        assertParseFailure(parser, " name - role name",
                SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);
    }
}
```
###### \java\seedu\address\model\internship\InternshipContainsAllKeywordsPredicateTest.java
``` java
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
    public void test_containsAllKeywords_returnsTrue() {
        // One keyword
        InternshipContainsAllKeywordsPredicate predicate =
                new InternshipContainsAllKeywordsPredicate(Collections.singletonList("Charlie"));
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

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Data", "Tech", "Company"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // Mixed-case keywords
        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("DaTa", "ToWn", "COmpany"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("DaTa"));
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));

        // Zero keywords
        predicate = new InternshipContainsAllKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(PREDICATE_TEST_INTERNSHIP));
    }

    @Test
    public void test_doesNotContainAllKeywords_returnsFalse() {
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
                new InternshipContainsAllKeywordsPredicate(Arrays.asList("Test"));
        assertTrue(predicate.test(new InternshipBuilder().withIndustry("Test,").build()));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("saved"));
        assertTrue(predicate.test(new InternshipBuilder().withTags("[saved]").build()));

        predicate = new InternshipContainsAllKeywordsPredicate(Arrays.asList("Test", "saved"));
        assertTrue(predicate.test(new InternshipBuilder().withIndustry("Test,").withTags("[saved]").build()));
    }
}
```
###### \java\systemtests\FilterCommandSystemTest.java
``` java
public class FilterCommandSystemTest extends JobbiBotSystemTest {

    @Test
    public void filter() throws DuplicateInternshipException, InternshipNotFoundException {

        Model expectedModel = getModel();

        /* -------------------------Filtering on an unsearched list ------------------------------------------ */

        /* Case: filter multiple internships in internship book, command with leading spaces and trailing spaces
         * -> 2 internships found
         */
        String command = "   " + FilterCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AUDIT + "  ";
        ModelHelper.setFilteredList(expectedModel, BUSINESS2, BUSINESS4);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where internship list is displaying the internships we are finding
         * -> 2 internships found
         */
        command = FilterCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AUDIT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter internship where internship list is not displaying the internship we are finding -> 1 internship
         * found
         */
        command = FilterCommand.COMMAND_WORD + " Data";
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter internship with 1 repeated keyword entered -> 1 internship found */
        command = FilterCommand.COMMAND_WORD + " Data Data";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with single keywords -> 3 internships found */
        command = FilterCommand.COMMAND_WORD + " Engineering";
        ModelHelper.setFilteredList(expectedModel, ENGINEERING1, ENGINEERING2, ENGINEERING3);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with multiple keywords -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " ST Engineering";
        ModelHelper.setFilteredList(expectedModel, ENGINEERING1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with same 2 keywords in reverse order -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " Engineering ST";
        ModelHelper.setFilteredList(expectedModel, ENGINEERING1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with 3 keywords one of which is repeated -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " Engineering ST ST";
        ModelHelper.setFilteredList(expectedModel, ENGINEERING1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with 1 non-matching keywords -> 0 internships found */
        command = FilterCommand.COMMAND_WORD + " ST Engineering NonKeyWord";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from salary attribute -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " " + DATASCIENCE.getSalary().value;
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from name attribute -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " " + DATASCIENCE.getName().fullName;
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from role attribute -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " " + DATASCIENCE.getRole().value;
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from address attribute -> 0 internships found
         * filter does not look through address string of internship
         */
        command = FilterCommand.COMMAND_WORD + " " + DATASCIENCE.getAddress().value;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from email attribute -> 0 internships found
         * filter does not look through email string of internship
         */
        command = FilterCommand.COMMAND_WORD + " " + DATASCIENCE.getEmail().value;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from tag attribute -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " " + BUSINESS1.getTags().toString()
                .replaceAll("[\\[+\\]+]", " ");
        ModelHelper.setFilteredList(expectedModel, BUSINESS1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with keywords from role attribute -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " " + DATASCIENCE.getIndustry().value;
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> success */
        command = UndoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);

        /* Case: redo previous filter command -> success */
        command = RedoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);

        /* Case: filter with mixed case keywords -> 1 internships found */
        command = FilterCommand.COMMAND_WORD + " " + "DaTA";
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter with mixed case keywords -> 1 internships found */
        command = "fiLtEr" + " " + "Data";
        ModelHelper.setFilteredList(expectedModel, DATASCIENCE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter, keyword argument contains substring of internship attribute value -> 0 internships found */
        command = FilterCommand.COMMAND_WORD + " " + "DataEXTRA";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter,  keyword argument is substring of internship attribute value > 0 internships found */
        command = FilterCommand.COMMAND_WORD + " " + "Dat";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* -------------------------Filtering on a searched list ---------------------------------------------------- */

        /* Initialize Search List to limit filtering range */
        command = FindCommand.COMMAND_WORD + " " + "Engineering";
        expectedModel.updateInternship(ENGINEERING1, ENGINEERING1.addTagsToInternship("Engineering"));
        expectedModel.updateInternship(ENGINEERING2, ENGINEERING2.addTagsToInternship("Engineering"));
        expectedModel.updateInternship(ENGINEERING3, ENGINEERING3.addTagsToInternship("Engineering"));
        ModelHelper.setSearchedList(expectedModel, ENGINEERING1, ENGINEERING2, ENGINEERING3);
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter keyword that exists in full list but not searched list -> 0 Internships found */
        expectedModel = getModel();
        command = FilterCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_AUDIT;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> success */
        command = UndoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);

        /* Case: redo previous filter command -> success */
        command = RedoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);

        /* Case: Filter keyword that exists in full list but not searched list -> 1 Internships found */
        command = FilterCommand.COMMAND_WORD + " " + "ST";
        ModelHelper.setFilteredList(expectedModel, ENGINEERING1);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Filter keyword that exists in full list but not searched list -> 2 Internships found */
        command = FilterCommand.COMMAND_WORD + " " + "Jurong";
        ModelHelper.setFilteredList(expectedModel, ENGINEERING1, ENGINEERING2);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }
    /* --------------------------------------- Helper Methods ----------------------------------------------------- */

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_INTERNSHIPS_LISTED_OVERVIEW} with the number of internship in the filtered
     * list, and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, expectedModel.getFilteredInternshipList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
###### \java\systemtests\SortCommandSystemTest.java
``` java
public class SortCommandSystemTest extends JobbiBotSystemTest {

    private Model model = new ModelManager(getTypicalInternshipForSorting(), new UserPrefs());

    /**
     * Returns the data with saved tags to be loaded into the file in {@link #getDataFileLocation()}.
     */
    @Override
    protected JobbiBot getInitialData() {
        return TypicalInternshipsForSorting.getTypicalInternshipForSorting();
    }


    /* -------------------------Sorting on an unfiltered list ------------------------------------------------- */

    @Test
    public void sort_unsearched_unfiltered()  {

        /* Case: Sort with one argument, command with leading spaces and trailing spaces */
        String command = "   " + SortCommand.COMMAND_WORD + " " + "-salary" + "  ";
        ModelHelper.setSortedList(model, Arrays.asList("-salary"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: Sort with mixed case keywords */
        command = SortCommand.COMMAND_WORD + " sAlary";
        ModelHelper.setSortedList(model, Arrays.asList("salary"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: Sort with mixed case command word */
        command = "sORT" + " -salary";
        ModelHelper.setSortedList(model, Arrays.asList("-salary"));
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Case: Sort, keyword contains substring of valid argument  */
        command = SortCommand.COMMAND_WORD + " salaries";
        assertCommandFailure(command, SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        /* Case: Sort, keyword is substring of valid argument */
        command = SortCommand.COMMAND_WORD + " sal";
        assertCommandFailure(command, SortCommandParser.MESSAGE_INVALID_SORT_ATTRIBUTE);

        /* Case: undo previous filter command -> failure */
        command = UndoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);

        /* Case: redo previous filter command -> failure */
        command = RedoCommand.COMMAND_WORD;
        assertCommandFailure(command, UndoCommand.MESSAGE_FAILURE);
    }

    /* -------------------------Sorting on a searched list ------------------------------------------------------- */

    @Test
    public void sort_searched_unfiltered() throws DuplicateInternshipException, InternshipNotFoundException {

        /* Initialize a searched list */
        initializeSearchedList();

        /* Test Cases same as unsearched and unfiltered */
        sort_unsearched_unfiltered();
    }

    /* -------------------------Sorting on a filtered list ------------------------------------------------------- */

    @Test
    public void sort_searched_filtered() throws DuplicateInternshipException, InternshipNotFoundException {

        /* Initialize a searched and filtered list */
        initializeSearchedList();
        String command = FilterCommand.COMMAND_WORD + " " + "IndustryA";
        ModelHelper.setFilteredList(model, IN1, IN4, IN5);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();

        /* Test Cases same as unsearched and unfiltered */
        sort_unsearched_unfiltered();
    }


    /* --------------------------------------- Helper Methods ----------------------------------------------------- */

    /**
     * Helper method to initialize a searched list
     *
     * @throws DuplicateInternshipException
     * @throws InternshipNotFoundException
     */
    private void initializeSearchedList() throws DuplicateInternshipException, InternshipNotFoundException {
        String command = FindCommand.COMMAND_WORD + " " + "IndustryA IndustryB";
        model.updateInternship(IN1, IN1.addTagsToInternship("IndustryA"));
        model.updateInternship(IN4, IN4.addTagsToInternship("IndustryA"));
        model.updateInternship(IN5, IN5.addTagsToInternship("IndustryA"));
        model.updateInternship(IN2, IN2.addTagsToInternship("IndustryB"));
        ModelHelper.setSearchedList(model, IN1, IN2, IN4, IN5);
        assertCommandSuccess(command, model);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_INTERNSHIPS_LISTED_OVERVIEW} with the number of internship in the filtered
     * list, and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_INTERNSHIPS_LISTED_OVERVIEW, expectedModel.getFilteredInternshipList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see JobbiBotSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, model);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

```
