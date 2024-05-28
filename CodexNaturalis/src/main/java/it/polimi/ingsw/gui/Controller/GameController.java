package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.CardView;
import it.polimi.ingsw.gui.GameBoard;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.*;

public class GameController implements FXMLController {
    public AnchorPane gameBoardContainer;
    public HBox objectiveCardsContainer;
    public HBox resourceDeck;
    public HBox goldenDeck;
    public VBox playerHandBox;
    private BaseClient client;
    private Stage stage;
    private Integer selectedCardIndex = null;
    private boolean playCardFaceDown;
    private GameBoard gameBoard;

    public void initialize() {
        gameBoard = new GameBoard();
        gameBoardContainer.getChildren().add(gameBoard);
        gameBoard.setPrefSize(gameBoardContainer.getPrefWidth(), gameBoardContainer.getPrefHeight());

    }

    public void initializeGame() {
        try {
            loadDeskCards();
            loadObjectiveCards();
            loadUsableCards();
            loadVisibleCards();
            loadPlayerHand();
            updatePlayerHandInteraction();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDeskCards() throws IOException, InterruptedException {
        HashMap<Point, GameCard> deskCards = client.getPlayerDesk();
        for (Map.Entry<Point, GameCard> entry : deskCards.entrySet()) {
            Point p = entry.getKey();
            GameCard card = entry.getValue();
            gameBoard.addCard(card, !card.isPlayedFaceDown(), p.x, p.y);
        }
    }

    private void loadObjectiveCards() throws IOException, InterruptedException {
        List<ObjectiveCard> objectiveCards = new ArrayList<>(Arrays.asList(client.getSharedObjectiveCards()));
        objectiveCards.add(client.getPlayerObjectiveCard());
        for (ObjectiveCard objCard : objectiveCards) {
            CardView objCardView = new CardView(objCard, true);
            objectiveCardsContainer.getChildren().add(objCardView);
        }
    }

    private void loadUsableCards() throws IOException {
        Card usableCard1 = client.getLastFromUsableCards(1);
        Card usableCard2 = client.getLastFromUsableCards(2);
        CardView usableCardBackView1 = new CardView(usableCard1, false);
        CardView usableCardBackView2 = new CardView(usableCard2, false);
        resourceDeck.getChildren().add(usableCardBackView1);
        goldenDeck.getChildren().add(usableCardBackView2);
    }

    private void loadVisibleCards() throws IOException, InterruptedException {
        initializeVisibleCards(client.getVisibleCardsDeck(1), resourceDeck);
        initializeVisibleCards(client.getVisibleCardsDeck(2), goldenDeck);
    }

    private void loadPlayerHand() throws IOException, InterruptedException {
        ArrayList<GameCard> playerHand = client.getPlayerHand();
        for (int i = 0; i < 3; i++) {
            if (i < playerHand.size()) {
                GameCard card = playerHand.get(i);
                CardView cardView = new CardView(card, !card.isPlayedFaceDown());
                playerHandBox.getChildren().add(cardView);
            }
        }
    }


    private void initializeVisibleCards(ArrayList<GameCard> cards, HBox pane) {
        CardView card1 = new CardView(cards.get(0), true);
        CardView card2 = new CardView(cards.get(1), true);
        pane.getChildren().add(card1);
        pane.getChildren().add(card2);
    }


    private void showAvailablePositions() throws IOException, InterruptedException {
        HashSet<Point> availablePlaces = client.getAvailablePlaces();
        availablePlaces.forEach(point -> {
            CardView placeholder = new CardView(true);
            placeholder.getStyleClass().add("placeholder");
            placeholder.setOnMouseClicked(event -> {
                try {
                    handlePositionSelection(point);
                } catch (PlaceNotAvailableException | RequirementsNotMetException | IOException | InterruptedException |
                         CardNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            gameBoard.addCardView(placeholder, point.x, point.y);
        });
    }


    private void handleCardSelection(int cardIndex, Node cardNode) throws IOException, InterruptedException {
        if (selectedCardIndex != null) {
            playerHandBox.getChildren().get(selectedCardIndex).getStyleClass().remove("selected-card");
        }
        selectedCardIndex = cardIndex;
        cardNode.getStyleClass().add("selected-card");
        promptCardOrientation();
    }


    private void promptCardOrientation() throws IOException, InterruptedException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Card Orientation");
        alert.setHeaderText("Choose how you want to play the card:");
        ButtonType buttonTypeOne = new ButtonType("Face Up");
        ButtonType buttonTypeTwo = new ButtonType("Face Down");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        Optional<ButtonType> result = alert.showAndWait();
        playCardFaceDown = result.isEmpty() || result.get() != buttonTypeOne;

        showAvailablePositions();
    }

    private void updatePlayerHandInteraction() throws IOException, InterruptedException {
        PlayerState state = client.getCurrentPlayerState();
        boolean canPlay = state == PlayerState.PLAY_CARD;
        int index = 0;
        for (Node cardNode : playerHandBox.getChildren()) {
            cardNode.setDisable(!canPlay);
            if (canPlay) {
                final int currentIndex = index; // Local variable for lambda expression
                cardNode.setOnMouseClicked(event -> {
                    try {
                        handleCardSelection(currentIndex, cardNode);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                index++;
            }
        }
    }

    private int getSelectedCardIndex() {
        return selectedCardIndex != null ? selectedCardIndex : -1; // Returns -1 if no card is selected
    }


    private void handlePositionSelection(Point selectedPoint) throws PlaceNotAvailableException, RequirementsNotMetException, IOException, InterruptedException, CardNotFoundException {
        int cardIndex = getSelectedCardIndex();
        if (cardIndex == -1) {
            showError("No card selected");
            return;
        }
        client.playCard(cardIndex, playCardFaceDown, selectedPoint);

    }


    private void showError(String message) {
        // Display error messages to the user
    }


    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

