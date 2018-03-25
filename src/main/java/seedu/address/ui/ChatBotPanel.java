package seedu.address.ui;


import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import javafx.collections.FXCollections;

import org.fxmisc.easybind.EasyBind;

import javafx.scene.control.Label;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

public class ChatBotPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ChatBotPanel.class);
    private int index = 1;
    private static final String FXML = "ChatBotPanel.fxml";
    private Logic logic;
    private ListElementPointer historySnapshot;
    private ObservableList<String> messagelist = FXCollections.observableArrayList();
    private final StringProperty displayed = new SimpleStringProperty();


    @FXML
    private ListView<ChatBotCard> chatBotListView;
    @FXML
    private Label username;
    @FXML
    private Label welcome;


    public ChatBotPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        initChatBot();
        registerAsAnEventHandler(this);
    }

    public void initChatBot() {
        ObservableList<String> initialmessageList = initList(messagelist); // Creates a list of inputs
        ObservableList<ChatBotCard> initialmappedList = EasyBind.map(
                initialmessageList, (msg) -> new ChatBotCard(msg, index));
        chatBotListView.setSelectionModel(new NoSelectionModel<>());
        chatBotListView.setItems(initialmappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }

    public ObservableList<String> initList(ObservableList<String> initiallist) {
        initiallist.add("Hello, I am Jobbi! I am here to help you find your ideal internship today, How can I help you?");
        return initiallist;
    }

    public ObservableList<String> addToList(ObservableList<String> listtoupdate) {
        historySnapshot = logic.getHistorySnapshot();
        listtoupdate.add(historySnapshot.current());
        return listtoupdate;
    }


    public void buildChat(ObservableList<String> buildlist){
        ObservableList<String> updatedmessageList = addToList(buildlist); // Creates a list of inputs
        ObservableList<ChatBotCard> mappedList = EasyBind.map(
                updatedmessageList, (msg) -> new ChatBotCard(msg, index++));
        chatBotListView.setItems(mappedList);
        chatBotListView.setCellFactory(listView -> new ChatBotPanel.ChatBotListViewCell());
    }

    @Subscribe
    private void handleNewResultAvailableForChatBot(NewResultAvailableEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        buildChat(messagelist);

        Platform.runLater(() -> displayed.setValue((event.message)));
    }

    /**
     * Scrolls to the {@code InternshipCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            chatBotListView.scrollTo(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InternshipCard}.
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


    public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public void selectIndices(int index, int... indices) {
        }

        @Override
        public void selectAll() {
        }

        @Override
        public void selectFirst() {
        }

        @Override
        public void selectLast() {
        }

        @Override
        public void clearAndSelect(int index) {
        }

        @Override
        public void select(int index) {
        }

        @Override
        public void select(T obj) {
        }

        @Override
        public void clearSelection(int index) {
        }

        @Override
        public void clearSelection() {
        }

        @Override
        public boolean isSelected(int index) {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void selectPrevious() {
        }

        @Override
        public void selectNext() {
        }
    }

    }






