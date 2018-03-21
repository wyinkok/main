package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InternshipPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of internships, research and other development opportunities.
 */
public class InternshipListPanel extends UiPart<Region> {
    private static final String FXML = "InternshipListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InternshipListPanel.class);

    @FXML
    private ListView<InternshipCard> internshipListView;

    public InternshipListPanel(ObservableList<Person> internshipList) {
        super(FXML);
        setConnections(internshipList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Person> internshipList) {
        ObservableList<InternshipCard> mappedList = EasyBind.map(
                internshipList, (person) -> new InternshipCard(person, internshipList.indexOf(person) + 1));
        internshipListView.setItems(mappedList);
        internshipListView.setCellFactory(listView -> new InternshipListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        internshipListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new InternshipPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code InternshipCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            internshipListView.scrollTo(index);
            internshipListView.getSelectionModel().clearAndSelect(index);
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
    class InternshipListViewCell extends ListCell<InternshipCard> {

        @Override
        protected void updateItem(InternshipCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
