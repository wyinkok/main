package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.internship.Internship;
import seedu.address.ui.InternshipCard;

/**
 * Provides a handle for {@code InternshipListPanel} containing the list of {@code InternshipCard}.
 */
public class InternshipListPanelHandle extends NodeHandle<ListView<InternshipCard>> {
    public static final String INTERNSHIP_LIST_VIEW_ID = "#internshipListView";

    private Optional<InternshipCard> lastRememberedSelectedInternshipCard;

    public InternshipListPanelHandle(ListView<InternshipCard> internshipListPanelNode) {
        super(internshipListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code InternshipCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public InternshipCardHandle getHandleToSelectedCard() {
        List<InternshipCard> internshipList = getRootNode().getSelectionModel().getSelectedItems();

        if (internshipList.size() != 1) {
            throw new AssertionError("Internship list size expected 1.");
        }

        return new InternshipCardHandle(internshipList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<InternshipCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the internship.
     */
    public void navigateToCard(Internship internship) {
        List<InternshipCard> cards = getRootNode().getItems();
        Optional<InternshipCard> matchingCard = cards.stream().filter(card -> card.internship.equals(internship)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Internship does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the internship card handle of an internship associated with the {@code index} in the list.
     */
    public InternshipCardHandle getInternshipCardHandle(int index) {
        return getInternshipCardHandle(getRootNode().getItems().get(index).internship);
    }

    /**
     * Returns the {@code InternshipCardHandle} of the specified {@code internship} in the list.
     */
    public InternshipCardHandle getInternshipCardHandle(Internship internship) {
        Optional<InternshipCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.internship.equals(internship))
                .map(card -> new InternshipCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Internship does not exist."));
    }

    /**
     * Selects the {@code InternshipCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code PersonCard} in the list.
     */
    public void rememberSelectedInternshipCard() {
        List<InternshipCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedInternshipCard = Optional.empty();
        } else {
            lastRememberedSelectedInternshipCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedInternshipCard()} call.
     */
    public boolean isSelectedInternshipCardChanged() {
        List<InternshipCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedInternshipCard.isPresent();
        } else {
            return !lastRememberedSelectedInternshipCard.isPresent()
                    || !lastRememberedSelectedInternshipCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
