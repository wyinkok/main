# wyinkok
###### \java\seedu\address\commons\events\ui\StartAppRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to start conversation with Jobbi
 */
public class StartAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\NewChatCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.StartAppRequestEvent;

/**
 * Restarts the conversation with Jobbi.
 */
public class NewChatCommand extends Command {

    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_RESTART_ACKNOWLEDGEMENT = "Restarted your conversation with Jobbi";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new StartAppRequestEvent());
        return new CommandResult(MESSAGE_RESTART_ACKNOWLEDGEMENT);
    }

}
```
###### \java\seedu\address\logic\commands\SaveCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Saves personally curated internships into a separate collection to access it again later.
 */

public class SaveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves an internship to your Saved Collection "
            + "by the index number used in the last internship listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SAVED_INTERNSHIP_SUCCESS = "New internship saved: %1$s";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP = "This internship already exists in the collection";
    public static final String MESSAGE_DUPLICATE_TAG = "This internship has been saved";

    public final String savedTagName = "saved";
    private final Index targetIndex;
    private Internship internshipWithSavedTag;
    private Internship internshipToSave;

    public SaveCommand(Index targetIndex) throws UniqueTagList.DuplicateTagException {

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(internshipToSave);
        try {
            model.updateInternship(internshipToSave, internshipWithSavedTag);
        } catch (DuplicateInternshipException e) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
        } catch (InternshipNotFoundException e) {
            throw new AssertionError("The target internship cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_SAVED_INTERNSHIP_SUCCESS, internshipWithSavedTag));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Internship> lastShownList = model.getFilteredInternshipList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        internshipToSave = lastShownList.get(targetIndex.getZeroBased());
        internshipWithSavedTag = addSavedTagToInternship(internshipToSave);
    }

    /**
     * Adds a "saved" tag to the existing tags of an internship
     * @param internship
     * @return
     * @throws CommandException
     */

    private Internship addSavedTagToInternship(Internship internship) throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());
        try {
            internshipTags.add(new Tag(savedTagName));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                internship.getIndustry(), internship.getLocation(), internship.getRole(), correctTagReferences);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveCommand // instanceof handles nulls
                && this.targetIndex.equals(((SaveCommand) other).targetIndex)); // state check
    }
}

```
###### \java\seedu\address\logic\commands\StartCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.StartAppRequestEvent;

/**
 * Starts the conversation with Jobbi.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_START_ACKNOWLEDGEMENT = "Next, please key in all the industries and roles"
            + " that you are interested in.  To view the full list of possible industries and roles "
            + "key in 'help' \nE.g: search healthcare technology datanalytics humanresource ";
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new StartAppRequestEvent());
        return new CommandResult(MESSAGE_START_ACKNOWLEDGEMENT);
    }

}
```
###### \java\seedu\address\logic\commands\UnsaveCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.internship.exceptions.InternshipNotFoundException;
import seedu.address.model.internship.exceptions.SavedTagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Undo saved internships into a separate collection.
 */

public class UnsaveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unsave";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a saved internship to your Saved Collection "
            + "by the index number used in the last internship listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNSAVED_INTERNSHIP_SUCCESS =
            "New internship removed from Saved Collection: %1$s";
    public static final String MESSAGE_DUPLICATE_REMOVAL = "This internship already removed from the collection";


    public final String savedTagName = "saved";
    private final Index targetIndex;
    private Internship internshipWithoutSavedTag;
    private Internship internshipToUnsave;

    public UnsaveCommand(Index targetIndex) {

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(internshipToUnsave);
        try {
            model.updateInternship(internshipToUnsave, internshipWithoutSavedTag);
        } catch (DuplicateInternshipException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMOVAL);
        } catch (InternshipNotFoundException e) {
            throw new AssertionError("The target internship cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_UNSAVED_INTERNSHIP_SUCCESS, internshipWithoutSavedTag));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Internship> lastShownList = model.getFilteredInternshipList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        internshipToUnsave = lastShownList.get(targetIndex.getZeroBased());
        internshipWithoutSavedTag = removeSavedTagToInternship(internshipToUnsave);
    }

    /**
     * Removes a "saved" tag to the existing tags of an internship
     * @param internship
     * @return
     * @throws CommandException
     */
    private Internship removeSavedTagToInternship(Internship internship) throws CommandException {
        final UniqueTagList personTags = new UniqueTagList(internshipToUnsave.getTags());
        try {
            personTags.delete(new Tag(savedTagName));
        } catch (SavedTagNotFoundException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMOVAL);
        }

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        personTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(),
                internship.getAddress(), internship.getIndustry(), internship.getLocation(), internship.getRole(),
                correctTagReferences);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnsaveCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnsaveCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\ListElementPointer.java
``` java
    /**
     * Returns true if calling {@code #hasElement} does not throw an {@code NoSuchElementException}.
     */
    public boolean hasElement(String toSearch) {
        if (list.contains(toSearch)) {
            return true;
        } else {
            return false;
        }
    }

```
###### \java\seedu\address\logic\parser\InternshipBookParser.java
``` java
        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case UnsaveCommand.COMMAND_WORD:
            return new UnsaveCommandParser().parse(arguments);

        case StartCommand.COMMAND_WORD:
            return new StartCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.ALTERNATIVE_COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case NewChatCommand.COMMAND_WORD:
            return new NewChatCommand();

```
###### \java\seedu\address\logic\parser\SaveCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class SaveCommandParser implements Parser<SaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SaveCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\UnsaveCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UnsaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class UnsaveCommandParser implements Parser<UnsaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnsaveCommand
     * and returns an UnsaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnsaveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnsaveCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnsaveCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\internship\exceptions\SavedTagNotFoundException.java
``` java
package seedu.address.model.internship.exceptions;

/**
 * Signals that the operation is unable to find the specified internship.
 */
public class SavedTagNotFoundException extends Exception {}
```
###### \java\seedu\address\ui\ChatBotCard.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * An UI component that displays information of a chat message.
 */
public class ChatBotCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ChatBotCard.class);
    private static final String FXML = "ChatBotCard.fxml";

    @FXML
    private HBox messagePane;
    @FXML
    private Label username;
    @FXML
    private Label messages;


    public ChatBotCard(String msg, int index) {
        super(FXML);
        setMessage(msg, index);
        registerAsAnEventHandler(this);
    }

    /**
     * Displays messages alternating between Jobbi and User
     * @param msg
     * @param index
     */
    public void setMessage(String msg, int index) {
        if (index % 2 == 0) {
            username.setText("Jobbi: ");
            messages.setText(msg);
        } else {
            username.setText("User: ");
            messages.setText(msg);
        }
    }
}
```
###### \java\seedu\address\ui\ChatBotPanel.java
``` java
package seedu.address.ui;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;


/**
 * Panel containing the message thread between chatbot and user.
 */
public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);
    private static final String FXML = "ChatBotPanel.fxml";

    private Logic logic;
    private ListElementPointer historySnapshot;
    private ObservableList<String> messagelist = FXCollections.observableArrayList();

    @FXML
    private ListView<ChatBotCard> chatBotListView;
    @FXML
    private Label username;
    @FXML
    private Label welcome;

    /**
     *  Creates the chatbot thread of messages
     */
    public ChatBotPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        initChatBot();
        registerAsAnEventHandler(this);
    }

    /**
     * Initiates the chatbot thread of messages with Jobbi's first message
     */
    public void initChatBot() {
        ObservableList<String> initialMessage = createInitialMessage(messagelist);
        ObservableList<ChatBotCard> initialMappedList = EasyBind.map(
                initialMessage, (msg) -> new ChatBotCard(msg, 0));

        // prevents user from selecting list cell
        chatBotListView.setSelectionModel(new DisableSelectionOfListCell<>());

        chatBotListView.setItems(initialMappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }

    /**
     * Creates the first welcome message from Jobbi
     */
    public ObservableList<String> createInitialMessage(ObservableList<String> initialMessage) {
        initialMessage.add("Hello there, I am Jobbi! "
                + "I am here to help you find your ideal internship today. Type 'start' to begin your search.");
        return initialMessage;
    }

    /**
     *  Creates subsequent message thread between user and Jobbi
     */

    public void buildChat(ObservableList<String> listToBuild) {
        ObservableList<String> updatedMessages = addUserResponse(listToBuild);
        // if the user has not started conversation with jobbi using the `start` command
        if (updatedMessages.size() == (1)) {
            ObservableList<ChatBotCard> mappedList = EasyBind.map(
                    updatedMessages, (msg) -> new ChatBotCard(msg, 0));
            chatBotListView.setItems(mappedList);
            chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());

            // if the conversation has started between user and jobbi
        } else {
            AtomicInteger index = new AtomicInteger();
            ObservableList<ChatBotCard> mappedList = EasyBind.map(
                    updatedMessages, (msg) -> new ChatBotCard(msg, index.getAndIncrement()));
            chatBotListView.setItems(mappedList);
            chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
        }
    }

    /**
     *  Adds subsequent messages from the user end into the message list
     */

    public ObservableList<String> addUserResponse(ObservableList<String> listToUpdate) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            listToUpdate.add(historySnapshot.current());
            if (historySnapshot.current().equals("new")) {
                listToUpdate.clear();
                initChatBot();
            }
        }
        return listToUpdate;
    }

    /**
     * Checks if the user has initiated conversation with Jobbi and adds Jobbi's response if he/she has.
     * @param currentMessageList
     * @param message
     * @return
     */
    public ObservableList<String> addJobbiResponse(ObservableList<String> currentMessageList, String message) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            currentMessageList.add(message);
            if (historySnapshot.current().equals("new")) {
                currentMessageList.clear();
                initChatBot();
            }
        }
        return currentMessageList;
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildChat(messagelist); // Adds to message thread whenever and whatever user types something in the command box
        addJobbiResponse(messagelist, event.message);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ChatBotCard}.
     */
    class ChatBotListViewCell extends ListCell<ChatBotCard> {
        @Override
        protected void updateItem(ChatBotCard message, boolean empty) {
            super.updateItem(message, empty);
            if (empty || message == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(message.getRoot());

            }
        }
    }
}
```
###### \resources\view\ChatBotCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox fx:id="messagePane" style="-fx-border-insets: 5;" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="150" />
    </columnConstraints>
      <HBox alignment="CENTER_LEFT" spacing="5" >

          <Label fx:id="username" alignment="TOP_LEFT" contentDisplay="TOP" ellipsisString="\\$username" prefWidth="70.0" styleClass="cell_medium_label" text="\$username" textAlignment="JUSTIFY" textOverrun="WORD_ELLIPSIS" />
         <VBox alignment="CENTER">
            <children>
                <Label fx:id="messages" alignment="TOP_LEFT" lineSpacing="2.0" styleClass="cell_medium_label" text="\$messages" textAlignment="JUSTIFY" wrapText="true" />
            </children>
            <HBox.margin>
               <Insets bottom="8.0" top="8.0" />
            </HBox.margin>
         </VBox>

            </HBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>

      </GridPane>

</HBox>
```
###### \resources\view\ChatBotPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ListView fx:id="chatBotListView" VBox.vgrow="ALWAYS" />
   </children>
</VBox>
```
###### \resources\view\ChatBotTheme.css
``` css
.background {
    -fx-background-color:  #f4f4f4;
    background-color: #f4f4f4; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Avenir Next";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Avenir";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Avenir";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Avenir";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #ffffff;
    -fx-control-inner-background: #ffffff;
    -fx-background-color: #ffffff;
    -fx-table-cell-border-color: #ffffff;
    -fx-table-header-border-color: #ffffff;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: #f4f4f4;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Avenir";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color:  #f4f4f4;
    -fx-border-color: transparent transparent transparent  #d6d6d6;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color:  #f4f4f4;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 10 10 10 4;
    -fx-background-color: #f4f4f4;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 5;
    -fx-padding: 3px;
}

.list-cell:filled:even {
    -fx-background-color:#c5def5;
}

.list-cell:filled:odd {
    -fx-background-color: #f9d0c4;
}

.list-cell:filled:selected {
    -fx-background-color: #e99695;
    -fx-border-width: 0;
}



.list-cell:filled:selected #cardPane {
    -fx-border-color: #ffd57a;
    -fx-border-width: 0;
 }

.list-cell .label {
    -fx-text-fill: #414141;
}

.cell_big_label {
    -fx-font-family: "Avenir Bold";
    -fx-font-size: 18px;
    -fx-text-fill:#f4f4f4;
}

/** ChatBot **/
.cell_medium_label {
    -fx-font-family: "Calibri";
    -fx-font-size: 14px;
    -fx-text-fill:#f4f4f4;
}

.cell_small_label {
    -fx-font-family: "Avenir Medium";
    -fx-font-size: 13px;
    -fx-text-fill: #f4f4f4;
}

/* Command Line */
.anchor-pane {
     -fx-background-color: #ffffff;
}

.pane-with-border {
     -fx-background-color: #f4f4f4;
     -fx-border-color: #f4f4f4;
     -fx-border-top-width: 0px;
}

.status-bar {
    -fx-background-color: #f4f4f4;
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: #f4f4f4;
    -fx-font-family: "Avenir";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}


.result-display .label {
    -fx-text-fill: black !important;
}

/* Footer Text */
.status-bar .label {
    -fx-font-family: "Avenir";
    -fx-font-size: 9pt;
    -fx-text-fill: #4d4d4d;
}

.status-bar-with-border {
    -fx-background-color: #f4f4f4;
    -fx-border-color: #f4f4f4;
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: #f4f4f4;
}

.grid-pane {
    -fx-background-color: #f4f4f4;
    -fx-border-color: #f4f4f4;
    -fx-border-width: 0px;
}

.grid-pane .anchor-pane {
    -fx-background-color: #f4f4f4;
}

.context-menu {
    -fx-background-color: #f4f4f4;
}

.context-menu .label {
    -fx-text-fill: white;
}

/* Menu Bar */
.menu-bar {
    -fx-background-color: #e99695;
}

.menu-bar .label {
    -fx-font-size: 11pt;
    -fx-font-family: "Avenir";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

/* Scroll Bar */
.scroll-bar {
    -fx-background-color:#f9d0c4;
}

.scroll-bar .thumb {
    -fx-background-color: #ffffff;
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: #ffffff;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 0 0 0 0;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 0 0 0 0;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 12px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #ffffff transparent #ffffff;
    -fx-background-insets: 0;
    -fx-border-color: #ffffff #ffffff #ffffff #ffffff;
    -fx-border-insets: 0;
    -fx-border-width: 0;
    -fx-font-family: "Avenir";
    -fx-font-size: 15pt;
    -fx-text-fill: #4d4d4d;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

/* Result Display */
#resultDisplay .content {
    -fx-background-color: #f4f4f4;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### \resources\view\Extensions.css
``` css
.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: #f4f4f4;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.Scene?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.stage.Stage?>

<fx:root minHeight="600" minWidth="450" type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8.0.161" xmlns:fx="http://javafx.com/fxml/1">
  <icons>
    <Image url="@/images/job_icon.png" />
  </icons>
  <scene>
    <Scene>
      <stylesheets>
        <URL value="@ChatBotTheme.css" />
        <URL value="@Extensions.css" />
      </stylesheets>

      <VBox prefWidth="320.0">
        <MenuBar fx:id="menuBar" VBox.vgrow="NEVER">
          <Menu mnemonicParsing="false" text="Help">
            <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
          </Menu>
          <Menu mnemonicParsing="false" text="Quit">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
          </Menu>
        </MenuBar>

        <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.889937106918239, 0.5" minHeight="250.0" VBox.vgrow="ALWAYS">
          <VBox fx:id="internshipList" SplitPane.resizableWithParent="false">
            <padding>
              <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
            <StackPane fx:id="internshipListPanelPlaceholder" VBox.vgrow="ALWAYS" />
          </VBox>
               <StackPane fx:id="chatBotPanelPlaceholder" />

          <StackPane fx:id="browserPlaceholder">
            <padding>
              <Insets bottom="10" left="10" right="10" top="10" />
            </padding>
          </StackPane>
        </SplitPane>

        <StackPane fx:id="commandBoxPlaceholder" minHeight="70.0" prefHeight="60.0" prefWidth="320.0" styleClass="pane-with-border" VBox.vgrow="NEVER">
          <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
          </padding>
        </StackPane>

        <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
      </VBox>
    </Scene>
  </scene>
</fx:root>
```
