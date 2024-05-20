package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.InizializeStarterCardStateGUI;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;

public class ObjectiveCardSelectionController {
    private final Stage stage;
    @FXML
    private ImageView card1ImageView;
    @FXML
    private ImageView card2ImageView;
    @FXML
    private Label messageLabel;

    private final VirtualView client;
    private ArrayList<ObjectiveCard> playerObjectiveCards;

    public ObjectiveCardSelectionController(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        loadCards();
    }

    private void loadCards() {
        try {
            playerObjectiveCards = client.getPlayerObjectiveCards();
            if (playerObjectiveCards.size() >= 2) {
                String frontImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + playerObjectiveCards.getFirst().getImageFrontPath())).toExternalForm();
                String backImagePath = Objects.requireNonNull(getClass().getResource("/Images/" + playerObjectiveCards.get(1).getImageFrontPath())).toExternalForm();

                card1ImageView.setImage(new Image(frontImagePath));
                card2ImageView.setImage(new Image(backImagePath));
            } else {
                messageLabel.setText("Not enough cards loaded.");
            }
        } catch (RemoteException ex) {
            messageLabel.setText("Error while getting the drawn objective cards: " + ex.getMessage());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSelectCard1() {
        if (!playerObjectiveCards.isEmpty()) {
            selectCard(0);
        }
    }

    public void handleSelectCard2() {
        if (playerObjectiveCards.size() > 1) {
            selectCard(1);
        }
    }

    private void selectCard(int cardIndex) {
        try {
            client.setObjectiveCard(cardIndex);
            client.setCurrentState(new InizializeStarterCardStateGUI(stage, client));
            client.showState();
            messageLabel.setText("Card selected: " + playerObjectiveCards.get(cardIndex).getImageFrontPath());
        } catch (CardNotFoundException | IOException | InterruptedException ex) {
            messageLabel.setText("Error selecting card: " + ex.getMessage());
        }
    }
}