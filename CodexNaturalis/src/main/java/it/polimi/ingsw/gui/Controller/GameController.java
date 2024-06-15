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
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class GameController implements FXMLController {
    public AnchorPane gameBoardContainer;
    public HBox objectiveCardsContainer;
    public HBox resourceDeck;
    public HBox goldenDeck;
    public VBox playerHandBox;
    public ScrollPane gameBoardScrollPane;
    private BaseClient client;
    private Stage stage;
    private boolean playCardFaceDown = false;
    private GameBoard gameBoard;

    public void initialize() {
        gameBoard = new GameBoard();
        gameBoardContainer.getChildren().add(gameBoard);

        setupZoomControls();

        //center scroll pane
        double hValue = (gameBoard.getPrefWidth() - gameBoardScrollPane.getViewportBounds().getWidth()) / 2 / gameBoard.getPrefWidth();
        double vValue = (gameBoard.getPrefHeight() - gameBoardScrollPane.getViewportBounds().getHeight()) / 2 / gameBoard.getPrefHeight();
        gameBoardScrollPane.setHvalue(hValue);
        gameBoardScrollPane.setVvalue(vValue);
    }


    private void setupZoomControls() {
        gameBoardScrollPane.setOnMouseEntered(event -> {
            gameBoardScrollPane.requestFocus();
            event.consume();
        });

        gameBoardScrollPane.setOnKeyPressed(event -> {
            double zoomFactor = 1.1;
            if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.EQUALS) {
                scaleContent(gameBoard, zoomFactor);
            } else if (event.getCode() == KeyCode.MINUS) {
                scaleContent(gameBoard, 1 / zoomFactor);
            }
            event.consume();
        });

        gameBoardScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                double zoomFactor = 1.05;
                if (event.getDeltaY() > 0) {
                    scaleContent(gameBoard, zoomFactor);
                } else {
                    scaleContent(gameBoard, 1 / zoomFactor);
                }
                event.consume();  // Consuma l'evento per evitare lo scrolling del ScrollPane
            }
        });
    }


    private void scaleContent(Node node, double scaleFactor) {
        double newScale = node.getScaleX() * scaleFactor;

        if (newScale < 0.5) newScale = 0.5;
        if (newScale > 3.0) newScale = 3.0;

        node.setScaleX(newScale);
        node.setScaleY(newScale);
    }


    public void initializeGame() {
        try {
            loadDeskCards();
            loadObjectiveCards();
            loadUsableCards();
            loadVisibleCards();
            loadPlayerHand();
            updatePlayerHandInteraction();
            if (isPlayerTurn())
                showAvailablePositions();
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

    private void loadUsableCards() throws IOException, InterruptedException {
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
        playerHandBox.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            if (i < playerHand.size()) {
                GameCard card = playerHand.get(i);
                CardView cardView = new CardView(card, !playCardFaceDown);
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
            setPlaceholderEvents(placeholder, point);
            gameBoard.addCardView(placeholder, point.x, point.y);
        });
    }

    private void setPlaceholderEvents(CardView placeholder, Point point) {
        placeholder.setOnDragOver(event -> {
            if (event.getGestureSource() != placeholder && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        placeholder.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    handlePositionSelection(point, Integer.parseInt(db.getString()));
                    loadPlayerHand();
                    updatePlayerHandInteraction();
                    loadDeskCards();
                    updatePlayerDrawInteraction();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }


    private boolean isPlayerTurn() throws IOException, InterruptedException {
        return client.getCurrentPlayerState() == PlayerState.PLAY_CARD;
    }

    private boolean isPlayerStateDraw() throws IOException, InterruptedException {
        return client.getCurrentPlayerState() == PlayerState.DRAW;
    }

    private void updatePlayerDrawInteraction() throws IOException, InterruptedException {
        int index = 3;
        for (Node cardNode : resourceDeck.getChildren()) {
            cardNode.setDisable(!isPlayerStateDraw());
            if (isPlayerStateDraw()) {
                final int finalIndex = index;
                cardNode.setOnMouseClicked(event -> {
                    try {
                        client.drawCard(1, finalIndex);
                        resourceDeck.getChildren().clear();
                        goldenDeck.getChildren().clear();
                        loadUsableCards();
                        loadVisibleCards();
                        loadPlayerHand();
                    } catch (IOException | InterruptedException | CardNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
            if (index == 3)
                index = 1;
            else
                index++;
        }
        index = 3;
        for (Node cardNode : goldenDeck.getChildren()) {
            cardNode.setDisable(!isPlayerStateDraw());
            if (isPlayerStateDraw()) {
                final int finalIndex = index;
                cardNode.setOnMouseClicked(event -> {
                    try {
                        client.drawCard(2, finalIndex);
                        resourceDeck.getChildren().clear();
                        goldenDeck.getChildren().clear();
                        loadUsableCards();
                        loadVisibleCards();
                        loadPlayerHand();
                    } catch (IOException | InterruptedException | CardNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            }
            if (index == 3)
                index = 1;
            else
                index++;
        }
    }

    private void updatePlayerHandInteraction() throws IOException, InterruptedException {
        int index = 0;
        for (Node cardNode : playerHandBox.getChildren()) {
            cardNode.setDisable(!isPlayerTurn());
            if (isPlayerTurn()) {
                final int currentIndex = index;
                cardNode.setOnDragDetected(event -> {

                    WritableImage cardImage = cardNode.snapshot(new SnapshotParameters(), null);
                    Dragboard db = cardNode.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(Integer.toString(currentIndex)); // Pass the index as a string
                    db.setDragView(cardImage);
                    db.setContent(content);
                    event.consume();
                });
                index++;
            }
        }
    }


    private void handlePositionSelection(Point selectedPoint, int cardIndex) throws IOException, InterruptedException {
        if (cardIndex == -1) {
            showError("No card selected");
            return;
        }
        try {
            client.playCard(cardIndex, playCardFaceDown, selectedPoint);
            CardView cardView = (CardView) playerHandBox.getChildren().remove(cardIndex);
            gameBoard.addCardView(cardView, selectedPoint.x, selectedPoint.y);
            clearPlaceholders();

        } catch (RequirementsNotMetException | PlaceNotAvailableException | CardNotFoundException e) {
            showError("Requirements not met");
        }
    }


    private void clearPlaceholders() {
        gameBoard.getChildren().removeIf(node -> node instanceof CardView && ((CardView) node).isPlaceholder());
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

    public void handleShowBackButton() throws IOException, InterruptedException {
        playCardFaceDown = !playCardFaceDown;
        loadPlayerHand();
        updatePlayerHandInteraction();
    }

    public void setMyTurn(boolean myTurn) {
        try {
            resourceDeck.getChildren().clear();
            goldenDeck.getChildren().clear();
            loadUsableCards();
            loadVisibleCards();
            if (myTurn) {
                updatePlayerHandInteraction();
                showAvailablePositions();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

