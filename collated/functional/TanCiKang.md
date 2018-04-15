# TanCiKang
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.removeTagsFromAllInternshipList();
        model.updateSearchedInternshipList(predicate);
        model.addTagsToFilteredList();
        return getCommandResult();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

    /**
     * Helper method to retrieve the correct message for command results
     * @return
     */
    private CommandResult getCommandResult() {
        if (model.getFilteredInternshipList().size() > 0) {
            return new CommandResult(MESSAGE_SEARCH_RESPONSE);
        } else {
            return new CommandResult(MESSAGE_SEARCH_RESPONSE_NO_INTERNSHIPS);
        }
    }
}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    @Override
    public CommandResult execute() {
        ModelManager.setKeywords(new ArrayList<>());
        model.updateSearchedInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        model.removeTagsFromFilteredList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\model\internship\Industry.java
``` java
/**
 * Represents a Internship's industry in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIndustry(String)}
 */
public class Industry {

    public static final String MESSAGE_INDUSTRY_CONSTRAINTS =
            "Internship industry can take any values and it should not be blank";

    /*
     * The first character of the industry must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INDUSTRY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Industry}.
     *
     * @param industry A valid industry.
     */
    public Industry (String industry) {
        requireNonNull(industry);
        checkArgument(isValidIndustry(industry), MESSAGE_INDUSTRY_CONSTRAINTS);
        this.value = industry;
    }

    /**
     * Returns true if a given string is a valid internship industry.
     */
    public static boolean isValidIndustry(String test) {
        return test.matches(INDUSTRY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Industry // instanceof handles nulls
                && this.value.equals(((Industry) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\internship\Internship.java
``` java
    private String createUrl() {
        StringBuilder url = new StringBuilder();
        url.append(getName()).append(' ').append(getRole());
        return url.toString().replace(" ", "-");
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Internship)) {
            return false;
        }

        Internship otherInternship = (Internship) other;
        return otherInternship.getName().equals(this.getName())
                && otherInternship.getSalary().equals(this.getSalary())
                && otherInternship.getEmail().equals(this.getEmail())
                && otherInternship.getAddress().equals(this.getAddress())
                && otherInternship.getIndustry().equals(this.getIndustry())
                && otherInternship.getRegion().equals(this.getRegion())
                && otherInternship.getRole().equals(this.getRole());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, salary, email, address, industry, region, role, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Salary: ")
                .append(getSalary())
                .append(" Industry: ")
                .append(getIndustry())
                .append(" Region: ")
                .append(getRegion())
                .append(" Role: ")
                .append(getRole())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

```
###### \java\seedu\address\model\internship\Internship.java
``` java
    /**
     * Remove all tags other than 'saved' tags from individual internship
     * @return internship
     */
    public Internship removeTagsFromInternship() {
        final UniqueTagList internshipTags = new UniqueTagList(getTags());

        for (Tag tagToBeRemoved : tags) {
            if (!tagToBeRemoved.toString().equals(SAVED_TAG_NAME)) {
                try {
                    internshipTags.delete(tagToBeRemoved);
                } catch (TagNotFoundException e) {
                    assert false;
                    throw new AssertionError("Impossible! Should not have TagNotFoundException");
                }
            }
        }

        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                getName(), getSalary(), getEmail(), getAddress(),
                getIndustry(), getRegion(), getRole(), correctTagReferences);
    }

```
###### \java\seedu\address\model\internship\Internship.java
``` java
    /**
     * Adds keyword tags that matches the individual internship to the internship except keywords with only
     * non-alphanumeric characters
     * @param keyword
     * @return Internship
     * @throws CommandException
     */
    public Internship addTagsToInternship(String keyword) {
        final UniqueTagList internshipTags = new UniqueTagList(getTags());

        try {
            internshipTags.add(new Tag(keyword));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new AssertionError("Operation would result in duplicate tags");
        }

        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                getName(), getSalary(), getEmail(), getAddress(),
                getIndustry(), getRegion(), getRole(), correctTagReferences);
    }
}
```
###### \java\seedu\address\model\internship\Location.java
``` java
/**
 * Represents a Internship's location in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Internship location can take any values and it should not be blank";

    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public Location (String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid location industry.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && this.value.equals(((Location) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\internship\Role.java
``` java
/**
 * Represents a Internship's role in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_ROLE_CONSTRAINTS =
            "Internship role can take any values and it should not be blank";

    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ROLE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Role}.
     *
     * @param role A valid role.
     */
    public Role (String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_ROLE_CONSTRAINTS);
        this.value = role;
    }

    /**
     * Returns true if a given string is a valid internship role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(ROLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && this.value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     *  Remove tags from the entire internship list
     */
    void removeTagsFromAllInternshipList();

    /**
     *  Remove tags from the filtered list
     */
    void removeTagsFromFilteredList();

    /**
     * Add keyword tags that matches the internship to the list of filteredInternships
     */
    void addTagsToFilteredList();
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
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

```
###### \java\seedu\address\model\ModelManager.java
``` java
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

```
###### \java\seedu\address\model\ModelManager.java
``` java
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
```
