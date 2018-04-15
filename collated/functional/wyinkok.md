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

    public static final String MESSAGE_RESTART_ACKNOWLEDGEMENT = "We've successfully restarted our conversation";

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
 * Saves internships into a separate Saved collection to access it again later.
 */

public class SaveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves an internship to your Saved Collection "
            + "by the index number used in the last internship listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SAVED_INTERNSHIP_SUCCESS = "New internship saved: %1$s";
    public static final String MESSAGE_DUPLICATE_SAVED_INTERNSHIP = "This internship has been saved";
    private static final String SAVED_TAG = "saved";

    private final Index targetIndex;
    private Internship internshipWithSavedTag;
    private Internship internshipToSave;

    public SaveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(internshipToSave);
        try {
            model.updateInternship(internshipToSave, internshipWithSavedTag);
        } catch (DuplicateInternshipException e) {
            throw new CommandException(MESSAGE_DUPLICATE_SAVED_INTERNSHIP);
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
     * Adds a "saved" tag to the existing tags of an internship.
     *
     * @param internship
     * @return internship with a saved tag.
     * @throws CommandException
     */
    private Internship addSavedTagToInternship(Internship internship) throws CommandException {
        final UniqueTagList internshipTags = new UniqueTagList(internship.getTags());
        try {
            internshipTags.add(new Tag(SAVED_TAG));
        } catch (UniqueTagList.DuplicateTagException e) {
            throw new CommandException(MESSAGE_DUPLICATE_SAVED_INTERNSHIP);
        }

        // Create map with values = tag object references in the master list
        // used for checking internship tag references.
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        internshipTags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of internship tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        internshipTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Internship(
                internship.getName(), internship.getSalary(), internship.getEmail(), internship.getAddress(),
                internship.getIndustry(), internship.getRegion(), internship.getRole(), correctTagReferences);
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
            + " that you are interested in.  To view a suggested list of possible industries and roles, "
            + "type 'help'. \n\nE.g  find finance technology marketing consulting";

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
import seedu.address.model.internship.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Removes saved internships from the saved collection.
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
    public static final String MESSAGE_DUPLICATE_REMOVAL = "This internship has been removed from the collection";

    private static final String SAVED_TAG = "saved";
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
     * Removes a "saved" tag to the existing tags of an internship.
     *
     * @param internship
     * @return
     * @throws CommandException
     */
    private Internship removeSavedTagToInternship(Internship internship) throws CommandException {
        final UniqueTagList personTags = new UniqueTagList(internshipToUnsave.getTags());
        try {
            personTags.delete(new Tag(SAVED_TAG));
        } catch (TagNotFoundException e) {
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
                internship.getAddress(), internship.getIndustry(), internship.getRegion(), internship.getRole(),
                correctTagReferences);
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
            checkIfConversationRestarted();
            return new SaveCommandParser().parse(arguments);

        case UnsaveCommand.COMMAND_WORD:
            checkIfConversationRestarted();
            return new UnsaveCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\InternshipBookParser.java
``` java
        case StartCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            hasRestarted = false;
            checkIfConversationRestarted();
            if (!hasStarted) {
                hasStarted = true;
                return new StartCommand();
            } else {
                throw new ParseException("Our conversation has already started"
                        + "Type 'new' if you would like to restart our conversation");
            }

        case NewChatCommand.COMMAND_WORD:
            checkIfContainArguments(arguments);
            hasStarted = false;
            if (!hasRestarted) {
                hasRestarted = true;
                return new NewChatCommand();
            } else {
                throw new AssertionError("Conversation should only restart after Start Command is "
                        + "entered again");
            }

```
###### \java\seedu\address\logic\parser\InternshipBookParser.java
``` java
    /**
     * Checks if the user has typed in the start command after the new command to restart the conversation successfully
     * @throws ParseException if any other command is typed in after the new command
     */
    private void checkIfConversationRestarted() throws ParseException {
        if (hasRestarted) {
            throw new ParseException(MESSAGE_INVALID_RESTART_COMMAND);
        }
    }

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
 * Parses input arguments and creates a new SaveCommand object.
 */
public class SaveCommandParser implements Parser<SaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
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
 * Parses input arguments and creates a new UnsaveCommand object
 */
public class UnsaveCommandParser implements Parser<UnsaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnsaveCommand
     * and returns an UnsaveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
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
###### \java\seedu\address\model\internship\exceptions\TagNotFoundException.java
``` java
package seedu.address.model.internship.exceptions;

/**
 * Signals that the operation is unable to find the specified internship.
 */
public class TagNotFoundException extends Exception {}
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
    private Label messages;


    public ChatBotCard(String msg) {
        super(FXML);
        setMessage(msg);
        registerAsAnEventHandler(this);
    }

    /**
     * Displays messages from Jobbi or User.
     *
     * @param msg
     */
    public void setMessage(String msg) {
        messages.setText(msg);

    }
}
```
###### \java\seedu\address\ui\ChatBotPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;


/**
 * Panel containing the message thread between the chatbot and user.
 */
public class ChatBotPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);
    private static final String FXML = "ChatBotPanel.fxml";

    private Logic logic;
    private ListElementPointer historySnapshot;
    private ObservableList<String> messageList = FXCollections.observableArrayList();

    @FXML
    private ListView<ChatBotCard> chatBotListView;

    /**
     *  Creates the chatbot thread of messages
     */
    public ChatBotPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        initiateChatBot();
        registerAsAnEventHandler(this);
    }

    /**
     * Initiates the chatbot thread of messages with Jobbi's welcome message
     */
    public void initiateChatBot() {
        ObservableList<String> initialMessage = createInitialMessage(messageList);
        ObservableList<ChatBotCard> initialMappedList = EasyBind.map(
                initialMessage, (msg) -> new ChatBotCard(msg));

        // prevents user from selecting list cell
        chatBotListView.setSelectionModel(new DisableSelectionOfListCell<>());

        chatBotListView.setItems(initialMappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotListViewCell());
    }

    /**
     * Creates the first welcome message from Jobbi
     */
    public ObservableList<String> createInitialMessage(ObservableList<String> initialMessage) {
        initialMessage.add("JOBBI:   " + "Hello there, I am Jobbi! "
                + "I am here to help you find your ideal internship today. Type 'start' to begin your search.");
        return initialMessage;
    }

    /**
     *  Expands on the message thread between user and Jobbi
     */

    public void buildConversation(ObservableList<String> listToBuild) {
        ObservableList<String> updatedMessages = handleUserResponse(listToBuild);
        ObservableList<ChatBotCard> mappedList = EasyBind.map(
                updatedMessages, (msg) -> new ChatBotCard(msg));
        chatBotListView.setItems(mappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
        chatBotListView.scrollTo(chatBotListView.getItems().size());
    }


    /**
     *  Checks if the user has initiated conversation with Jobbi and adds User's response if he/she has.
     */

    public ObservableList<String> handleUserResponse(ObservableList<String> listToUpdateWithUserResponse) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            listToUpdateWithUserResponse.add("USER:   " + historySnapshot.current());
            if (historySnapshot.current().equals("new")) {
                listToUpdateWithUserResponse.clear();
                initiateChatBot();
            }
        }
        return listToUpdateWithUserResponse;
    }

    /**
     * Checks if the user has initiated conversation with Jobbi and adds Jobbi's response if he/she has.
     * @param listToUpdateWithJobbiResponse
     * @param message
     * @return
     */
    public ObservableList<String> handleJobbiResponse(ObservableList<String> listToUpdateWithJobbiResponse,
                                                      String message) {
        historySnapshot = logic.getHistorySnapshot();
        if (historySnapshot.hasElement("start")) {
            listToUpdateWithJobbiResponse.add("JOBBI:  " + message);
            if (historySnapshot.current().equals("new")) {
                listToUpdateWithJobbiResponse.clear();
                initiateChatBot();
            }
        }
        return listToUpdateWithJobbiResponse;
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildConversation(messageList);
        handleJobbiResponse(messageList, event.message);
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
      <HBox alignment="CENTER_LEFT" spacing="5">
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
/** CSS changes for ChatBot and Internship Panel **/
.cell_medium_label {
    -fx-font-family: "Calibri";
    -fx-font-size: 14px;
    -fx-text-fill:#f4f4f4;
}

.cell_small_label_title {
    -fx-font-family: "Calibri";
    -fx-font-style: italic;
    -fx-font-size: 11.5px;
    -fx-opacity: 0.75;
}

.cell_small_label {
    -fx-font-family: "Avenir Medium";
    -fx-font-size: 13px;
    -fx-text-fill: #f4f4f4;
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
