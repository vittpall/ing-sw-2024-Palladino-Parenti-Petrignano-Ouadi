package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.CardView;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameController {
    private final VirtualView client;
    private final Stage stage;
    public StackPane usableCardDeck1Back;
    public StackPane usableCardDeck2Back;
    public StackPane visibleCardDeck1Card1;
    public StackPane visibleCardDeck1Card2;
    public StackPane visibleCardDeck2Card1;
    public StackPane visibleCardDeck2Card2;
    public StackPane objectiveCard1;
    public StackPane objectiveCard2;
    @FXML
    private ImageView boardImage;
    @FXML
    private GridPane playerDesk;
    @FXML
    private ListView<ObjectiveCard> objectiveCardsList;
    @FXML
    private ListView<GameCard> playerHandList;

    public GameController(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }

    public void initialize() throws IOException, InterruptedException {

        // Load cards into the GridPane
        HashMap<Point, GameCard> deskCards = client.getPlayerDesk();
        for (Map.Entry<Point, GameCard> entry : deskCards.entrySet()) {
            Point p = entry.getKey();
            GameCard card = entry.getValue();
            CardView cardView = new CardView(card, !card.isPlayedFaceDown());
            playerDesk.add(cardView, p.x, p.y);
        }

        ObjectiveCard[] sharedObjectiveCards = client.getSharedObjectiveCards();
        CardView objCard1 = new CardView(sharedObjectiveCards[0], true);
        CardView objCard2 = new CardView(sharedObjectiveCards[1], true);
        objectiveCard1.getChildren().add(objCard1);
        objectiveCard2.getChildren().add(objCard2);

        // Initialize the back of the last card of getUsableCards
        Card usableCard1 = client.getLastFromUsableCards(1);
        Card usableCard2 = client.getLastFromUsableCards(2);
        CardView usableCardBackView1 = new CardView(usableCard1, false);
        CardView usableCardBackView2 = new CardView(usableCard2, false);
        usableCardDeck1Back.getChildren().add(usableCardBackView1);
        usableCardDeck2Back.getChildren().add(usableCardBackView2);

        // Initialize visible cards from deck 1 and deck 2
        initializeVisibleCards(client.getVisibleCardsDeck(1), visibleCardDeck1Card1, visibleCardDeck1Card2);
        initializeVisibleCards(client.getVisibleCardsDeck(2), visibleCardDeck2Card1, visibleCardDeck2Card2);
    }

    private void initializeVisibleCards(ArrayList<GameCard> cards, Pane pane1, Pane pane2) {
        CardView card1 = new CardView(cards.get(0), true);
        CardView card2 = new CardView(cards.get(1), true);
        pane1.getChildren().add(card1);
        pane2.getChildren().add(card2);
    }
}

