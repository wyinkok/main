# TanCiKang
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {

        // remove all tags from filtered list except 'saved' tags
        try {
            model.updateSearchedInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
            ModelManager.removeTagsFromInternshipList(model.getFilteredInternshipList(), model);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        model.updateFilteredInternshipList(predicate);

        // add tags that have keywords matching the internship
        try {
            ModelManager.addTagsToFilteredList(ModelManager.getKeywords(), model.getFilteredInternshipList(), model);
        } catch (CommandException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_SEARCH_RESPONSE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    @Override
    public CommandResult execute() {
        ModelManager.setKeywords(new ArrayList<String>());
        model.updateSearchedInternshipList(PREDICATE_SHOW_ALL_INTERNSHIPS);
        // remove all tags from filtered list except 'saved' tags
        try {
            ModelManager.removeTagsFromInternshipList(model.getFilteredInternshipList(), model);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Add keyword tags that matches the individual internship to the internship
     * @param keyword
     * @param internship
     * @return Internship
     * @throws CommandException
     */
    private static Internship addTagsToInternshipWithMatch(String keyword, Internship internship)
            throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());

        try {
            internshipTags.add(new Tag(keyword));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new CommandException ("Operation would result in duplicate tags");
        }

        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                internship.getIndustry(), internship.getLocation(), internship.getRole(), correctTagReferences);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Add keyword tags that matches the internship to the list of internships in filteredInternships
     * @param filterKeywords
     * @param filteredInternships
     * @param model
     * @throws CommandException
     */
    public static void addTagsToFilteredList (List<String> filterKeywords,
                                              ObservableList<Internship> filteredInternships, Model model)
            throws CommandException {

        for (String keywords : filterKeywords) {
            for (Internship filteredInternship : filteredInternships) {
                if (StringUtil.containsWordIgnoreCase(filteredInternship.toString(), keywords)) {
                    try {
                        model.updateInternship(filteredInternship,
                                addTagsToInternshipWithMatch(keywords, filteredInternship));
                    } catch (DuplicateInternshipException e) {
                        throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
                    } catch (InternshipNotFoundException e) {
                        throw new AssertionError("The target internship cannot be missing");
                    }
                }
            }
        }
        return;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Remove all tags from individual internship other than 'saved' tags
     * @param tagsToBeRemoved
     * @param internship
     * @return
     */
    private static Internship removeTagsFromInternship(Set<Tag> tagsToBeRemoved, Internship internship, Model model) {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());

        for (Tag tagToBeRemoved : tagsToBeRemoved) {
            if (!tagToBeRemoved.toString().equals("saved")) {
                try {
                    internshipTags.delete(tagToBeRemoved);
                } catch (SavedTagNotFoundException e) {
                    System.out.println("Saved tag not found!");
                }
            }
        }

        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));

        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                internship.getIndustry(), internship.getLocation(), internship.getRole(), correctTagReferences);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Remove all tags that are not 'saved' from the internships
     * @param internships
     * @param model
     * @throws CommandException
     */
    public static void removeTagsFromInternshipList(ObservableList<Internship> internships, Model model)
            throws CommandException {

        for (Internship internship : internships) {
            try {
                model.updateInternship(internship, removeTagsFromInternship(internship.getTags(), internship, model));
            } catch (DuplicateInternshipException e) {
                throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
            } catch (InternshipNotFoundException e) {
                throw new AssertionError("The target internship cannot be missing");
            }
        }
        return;
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
    }

    @Override
    public void updateSearchedInternshipList(Predicate<Internship> predicate) {
        requireNonNull(predicate);
        searchedInternships.setPredicate(predicate);
        filteredInternships.setPredicate(predicate);
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
