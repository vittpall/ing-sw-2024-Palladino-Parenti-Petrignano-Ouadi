package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.*;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.*;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.*;

public class GameController implements FXMLController {
    public AnchorPane gameDeskContainer;
    public VBox objectiveCardsContainer;
    public HBox resourceDeck;
    public HBox goldenDeck;
    public VBox playerHandBox;
    public ScrollPane gameDeskScrollPane;
    public Label infoGame;
    public Label titlePopUp;
    public Button closePopUpButton;
    public Label messagePopUp;
    public BorderPane popUp;
    public Button showBackButton;
    public TabPane playerDeskTabPane;
    public AnchorPane gameBoardAnchorPane;
    public Label plantLabel;
    public Label animalLabel;
    public Label fungiLabel;
    public Label insectLabel;
    public Label manuscriptLabel;
    public Label quillLabel;
    public Label inkwellLabel;
    public Button centerButton;
    public Button exitButton;
    private BaseClient client;
    private Stage stage;
    private boolean playCardFaceDown = false;
    private GameDesk gameDesk;
    private boolean isYourDeskShowing = true;
    private String playerDeskShown;
    public TabPane chatGameTabPane;
    private GameBoard gameBoard;

    public void initialize() {
        popUp.setVisible(false);
        gameDesk = new GameDesk();
        gameDeskContainer.getChildren().add(gameDesk);

        setupZoomControls();

        //center scroll pane
        centerGameDesk();


        playerDeskTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab.getText() != null && !newTab.getText().isEmpty()) {
                int selectedIndex = playerDeskTabPane.getSelectionModel().getSelectedIndex();
                handleShowPlayerDesk(selectedIndex);
            }
        });

        gameBoard = new GameBoard(gameBoardAnchorPane);
        plantLabel.setText("0");
        animalLabel.setText("0");
        fungiLabel.setText("0");
        insectLabel.setText("0");
        manuscriptLabel.setText("0");
        quillLabel.setText("0");
        inkwellLabel.setText("0");
    }

    private void centerGameDesk() {
        gameDeskScrollPane.setHvalue(0.5);
        gameDeskScrollPane.setVvalue(0.5);
    }

    public void handleExit() throws RemoteException {
        try {
            client.returnToLobby();
        } catch (IOException | InterruptedException e) {
            throw new RemoteException();
        }
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    private void setupZoomControls() {
        gameDeskScrollPane.setOnMouseEntered(event -> {
            gameDeskScrollPane.requestFocus();
            event.consume();
        });

        gameDeskScrollPane.setOnKeyPressed(event -> {
            double zoomFactor = 1.1;
            if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.EQUALS) {
                scaleContent(gameDesk, zoomFactor);
            } else if (event.getCode() == KeyCode.MINUS) {
                scaleContent(gameDesk, 1 / zoomFactor);
            }
            event.consume();
        });

        gameDeskScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.isControlDown()) {
                double zoomFactor = 1.05;
                if (event.getDeltaY() > 0) {
                    scaleContent(gameDesk, zoomFactor);
                } else {
                    scaleContent(gameDesk, 1 / zoomFactor);
                }
                event.consume();
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
            String username = client.getUsername();
            ArrayList<Player> players = client.getPlayers(client.getIdGame());
            playerDeskTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            for (Player player : players) {
                Tab tab = new Tab();

                String imagePath = player.getTokenColor() != null ? Objects.requireNonNull(getClass().getResource("/Images/" + player.getTokenColor().getImageName())).toExternalForm() : null;
                if (imagePath != null) {
                    ImageView imageView = createImageView(imagePath);
                    tab.setGraphic(imageView);
                }
                playerDeskTabPane.getTabs().add(tab);

                if (player.getUsername().equals(username)) {
                    tab.setText("Your Desk");
                    playerDeskTabPane.getSelectionModel().select(tab);
                } else {
                    tab.setText(player.getUsername() + "'s Desk");
                }
            }

            for (Player player : players) {
                String imagePath = player.getTokenColor() != null ? "/Images/" + player.getTokenColor().getImageName() : null;
                if (imagePath != null) {
                    gameBoard.addToken(player.getUsername(), Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
                }
            }

            loadCurrentPlayerDesk();
            loadObjectiveCards();
            loadUsableCards();
            loadVisibleCards();
            loadPlayerHand();
            updatePlayerHandInteraction();
            if (!(client.getGameState().equals(GameState.SETUP_GAME.toString()))) {
                if (isPlayerTurn()) {
                    infoGame.setText("It's your turn: you should play a card");
                    showAvailablePositions();
                } else{
                    for(Player player : players){
                        if(player.getPlayerState().equals(PlayerState.PLAY_CARD)){
                            infoGame.setText(player.getUsername() + " is playing");
                            break;
                        }
                    }
                }
            } else {
                infoGame.setText("Waiting for other players to choose their token's color and starter card");
            }
            initialiseChat();
            updateResourcesObjectsLabels();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateResourcesObjectsLabels() throws IOException, InterruptedException {
        EnumMap<Resource, Integer> resources = client.getPlayers(client.getIdGame()).get(client.getIdClientIntoGame()).getPlayerDesk().getTotalResources();
        resources.keySet().forEach(resource -> {
            switch (resource) {
                case Resource.PLANT_KINGDOM -> plantLabel.setText(String.valueOf(resources.get(resource)));
                case Resource.ANIMAL_KINGDOM -> animalLabel.setText(String.valueOf(resources.get(resource)));
                case Resource.FUNGI_KINGDOM -> fungiLabel.setText(String.valueOf(resources.get(resource)));
                case Resource.INSECT_KINGDOM -> insectLabel.setText(String.valueOf(resources.get(resource)));
            }
        });
        EnumMap<CornerObject, Integer> cornerObjects = client.getPlayers(client.getIdGame()).get(client.getIdClientIntoGame()).getPlayerDesk().getTotalObjects();
        cornerObjects.keySet().forEach(cornerObject -> {
            switch (cornerObject) {
                case CornerObject.MANUSCRIPT ->
                        manuscriptLabel.setText(String.valueOf(cornerObjects.get(cornerObject)));
                case CornerObject.QUILL -> quillLabel.setText(String.valueOf(cornerObjects.get(cornerObject)));
                case CornerObject.INKWELL -> inkwellLabel.setText(String.valueOf(cornerObjects.get(cornerObject)));
            }
        });
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

        applyHoverEffects(usableCardBackView1);
        applyHoverEffects(usableCardBackView2);

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
                applyHoverEffects(cardView);
                playerHandBox.getChildren().add(cardView);
            }
        }
    }


    private void initializeVisibleCards(ArrayList<GameCard> cards, HBox pane) {
        CardView card1 = new CardView(cards.get(0), true);
        CardView card2 = new CardView(cards.get(1), true);

        applyHoverEffects(card1);
        applyHoverEffects(card2);

        pane.getChildren().add(card1);
        pane.getChildren().add(card2);
    }


    private void applyHoverEffects(CardView cardView) {
        cardView.getStyleClass().add("card-image");
    }


    private void showAvailablePositions() throws IOException, InterruptedException {
        HashSet<Point> availablePlaces = client.getAvailablePlaces();
        availablePlaces.forEach(point -> {
            CardView placeholder = new CardView(true);
            placeholder.getStyleClass().add("placeholder");
            setPlaceholderEvents(placeholder, point);
            gameDesk.addCardView(placeholder, point.x, point.y);
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
                        infoGame.setText(client.getPlayers(client.getIdGame()).get((client.getIdClientIntoGame() + 1) % client.getnPlayer(client.getIdGame())).getUsername() + " is playing");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
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
                        infoGame.setText(client.getPlayers(client.getIdGame()).get((client.getIdClientIntoGame() + 1) % client.getnPlayer(client.getIdGame())).getUsername() + " is playing");
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
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
            int points = client.playCard(cardIndex, playCardFaceDown, selectedPoint);
            CardView cardView = (CardView) playerHandBox.getChildren().remove(cardIndex);
            gameDesk.addCardView(cardView, selectedPoint.x, selectedPoint.y);
            clearPlaceholders();
            loadPlayerHand();
            updatePlayerHandInteraction();
            loadCurrentPlayerDesk();
            updatePlayerDrawInteraction();
            updateResourcesObjectsLabels();
            gameBoard.updateTokenPosition(client.getUsername(), points);
            if (isPlayerStateDraw())
                infoGame.setText("It's your turn: you should draw a card");
            else
                infoGame.setText(client.getPlayers(client.getIdGame()).get((client.getIdClientIntoGame() + 1) % client.getnPlayer(client.getIdGame())).getUsername() + " is playing");
            if (client.getIdGame() != null) {
                clearPlaceholders();
                loadPlayerHand();
                System.out.println(client.getIdGame());
                updatePlayerHandInteraction();
                loadCurrentPlayerDesk();
                updatePlayerDrawInteraction();
                gameBoard.updateTokenPosition(client.getUsername(), points);
                if (isPlayerStateDraw())
                    infoGame.setText("It's your turn: you should draw a card");
                else
                    infoGame.setText(client.getPlayers(client.getIdGame()).get((client.getIdClientIntoGame() + 1) % client.getnPlayer(client.getIdGame())).getUsername() + " is playing");
            }
        } catch (RequirementsNotMetException | PlaceNotAvailableException e) {
            infoGame.setText("It's your turn: you should play another card");
            clearPlaceholders();
            showError("Requirements not met. You should play another card");
            updatePlayerHandInteraction();
            showAvailablePositions();
        }
    }


    private void clearPlaceholders() {
        gameDesk.getChildren().removeIf(node -> node instanceof CardView && ((CardView) node).isPlaceholder());
    }

    private void showError(String message) {
        titlePopUp.setText("Error");
        messagePopUp.setText(message);
        popUp.setVisible(true);
    }


    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleShowBackButton() throws IOException, InterruptedException {
        playCardFaceDown = !playCardFaceDown;
        showBackButton.setText(playCardFaceDown ? "Show Front" : "Show Back");
        loadPlayerHand();
        updatePlayerHandInteraction();
    }

    public void handleShowPlayerDesk(int playerIndex) {
        try {
            if (playerDeskShown == null || !playerDeskShown.equals(client.getPlayers(client.getIdGame()).get(playerIndex).getUsername())) {
                if (client.getUsername().equals(client.getPlayers(client.getIdGame()).get(playerIndex).getUsername())) {
                    isYourDeskShowing = true;
                    loadCurrentPlayerDesk();
                    if (isPlayerTurn())
                        showAvailablePositions();
                } else {
                    isYourDeskShowing = false;
                    loadOtherPlayerDesk(playerIndex);
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadOtherPlayerDesk(int playerIndex) throws IOException, InterruptedException {
        Player player = client.getPlayers(client.getIdGame()).get(playerIndex);
        playerDeskShown = player.getUsername();
        playerDeskTabPane.getSelectionModel().select(playerIndex);
        HashMap<Point, GameCard> deskCards = player.getPlayerDesk().getDesk();
        String imageName = player.getTokenColor() != null ? player.getTokenColor().getImageName() : null;
        loadDesk(deskCards, playerIndex, imageName);
    }

    private void loadCurrentPlayerDesk() throws IOException, InterruptedException {
        playerDeskShown = client.getUsername();
        playerDeskTabPane.getSelectionModel().select(client.getIdClientIntoGame());
        HashMap<Point, GameCard> deskCards = client.getPlayerDesk();
        loadDesk(deskCards, client.getIdClientIntoGame(), client.getTokenColor().getImageName());
    }

    private void loadDesk(HashMap<Point, GameCard> deskCards, int playerIndex, String tokenColorImageName) {
        gameDesk.getChildren().clear();
        for (Map.Entry<Point, GameCard> entry : deskCards.entrySet()) {
            Point p = entry.getKey();
            GameCard card = entry.getValue();
            gameDesk.addCard(card, !card.isPlayedFaceDown(), p.x, p.y);
            if (p.x == 0 && p.y == 0) {
                if (playerIndex == 0) {
                    gameDesk.addTokenToCard(Objects.requireNonNull(getClass().getResource("/Images/CODEX_pion_noir.png")).toExternalForm(), true);
                }

                if (tokenColorImageName != null) {
                    String tokenPath = "/Images/" + tokenColorImageName;
                    gameDesk.addTokenToCard(Objects.requireNonNull(getClass().getResource(tokenPath)).toExternalForm(), false);
                }
            }
        }
    }


    public void setMyTurn(String usernameCurrentPlayer) {
        try {
            resourceDeck.getChildren().clear();
            goldenDeck.getChildren().clear();
            loadUsableCards();
            loadVisibleCards();
            if (client.getUsername().equals(usernameCurrentPlayer)) {
                infoGame.setText("It's your turn: you should play a card");
                if (!isYourDeskShowing)
                    loadCurrentPlayerDesk();
                updatePlayerHandInteraction();
                showAvailablePositions();
            } else
                infoGame.setText(usernameCurrentPlayer + " is playing");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void cardPlayedNotification(String username, HashMap<String, Integer> playersPoints) {
        int indexPlayer = 0;
        try {
            ArrayList<Player> players = client.getPlayers(client.getIdGame());
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getUsername().equals(username)) {
                    indexPlayer = i;
                    break;
                }
            }
            if (playerDeskShown.equals(username)) {
                loadOtherPlayerDesk(indexPlayer);
            }
            if (!(client.getGameState().equals(GameState.SETUP_GAME.toString())) &&
                    client.getPlayers(client.getIdGame()).get(indexPlayer).getPlayerDesk().getDesk().size() == 1) {
                if (isPlayerTurn()) {
                    infoGame.setText("It's your turn: you should play a card");
                    showAvailablePositions();
                } else{
                    for(Player player : players){
                        if(player.getPlayerState().equals(PlayerState.PLAY_CARD)){
                            infoGame.setText(player.getUsername() + " is playing");
                            break;
                        }
                    }
                }
            }else if (!(client.getGameState().equals(GameState.SETUP_GAME.toString()))&&!(players.get(indexPlayer).getPlayerState().equals(PlayerState.DRAW)))
                infoGame.setText(players.get((indexPlayer + 1) % client.getnPlayer(client.getIdGame())).getUsername() + " is playing");

            gameBoard.updateTokenPosition(username, playersPoints.get(username));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void lastTurnSetNotification(String username) {
        messagePopUp.setText(username + " has reached 20 points.\n The last turn has begun!");
        titlePopUp.setText("Last Turn!");
        popUp.setVisible(true);
    }

    public void closePopUp() {
        popUp.setVisible(false);
    }

    public void endGameNotification(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        client.setCurrentState(new GetWinnerStateGUI(stage, client));
        ((GetWinnerStateGUI) client.getClientCurrentState()).initializeWinner(winner, scores, playersTokens);
    }

    public void initialiseChat() throws IOException, InterruptedException {
        ArrayList<Player> players = client.getPlayers(client.getIdGame());

        //initialize the tab for the global chat
        ArrayList<Message> globalChatMessages = client.getMessages(null);
        initialiseSingleChatTab("GlobalChat", globalChatMessages);

        ArrayList<Message> alreadyExistingMessages;
        //initialize the tab for the private chat
        for (Player player : players) {
            if (!(player.getUsername()).equals(client.getUsername())) {
                alreadyExistingMessages = client.getMessages(player.getUsername());
                initialiseSingleChatTab(player.getUsername(), alreadyExistingMessages);
            }

        }

    }

    public void initialiseSingleChatTab(String username, ArrayList<Message> alreadyExistingMessages) {
        ChatTab newTab = new ChatTab(username);

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(150.0);
        textArea.setPrefWidth(400.0);
        textArea.setEditable(false);

        TextField messageField = new TextField();
        messageField.setPrefWidth(400.0);


        for (Message message : alreadyExistingMessages) {
            if (message.getSender().equals(client.getUsername()))
                textArea.appendText("You: " + message.getContent() + "\n");
            else
                textArea.appendText(message.getSender() + ": " + message.getContent() + "\n");
        }

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                textArea.appendText("Me: " + message + "\n");
                messageField.clear();
                try {
                    if (username.equals("GlobalChat"))
                        sendMessage(null, message);
                    else
                        sendMessage(username, message);
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error while sending message, retry later");
                }
            }
        });

        newTab.setOnSelectionChanged(event -> {
            if (newTab.isSelected()) {
                newTab.resetUnreadMessages();
            }
        });

        VBox vbox = new VBox(textArea, messageField, sendButton);
        vbox.setSpacing(5.0);
        vbox.setPadding(new javafx.geometry.Insets(5.0));

        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        anchorPane.getChildren().add(vbox);

        newTab.setContent(anchorPane);
        chatGameTabPane.getTabs().add(newTab);
        chatGameTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void sendMessage(String receiver, String input) throws IOException, InterruptedException {
        client.sendMessage(receiver, input);
    }

    public void receiveMessage(Message message) {
        String whoSendTheMessage;
        if (!message.getSender().equals(client.getUsername()))
            whoSendTheMessage = message.getSender();
        else
            return;
        //I've already handled the message the client send, no need to use this function to display that message
        for (Tab tab : chatGameTabPane.getTabs()) {
            if (message.getReceiver() == null && tab.getText().equals("GlobalChat")) {
                fillChat(message, whoSendTheMessage, tab);
                break;
            }
            if (tab.getText().equals(message.getSender()) && message.getReceiver().equals(client.getUsername())) {
                fillChat(message, whoSendTheMessage, tab);
                break;
            }
        }
    }

    private void fillChat(Message message, String whoSendTheMessage, Tab tab) {
        VBox vbox = (VBox) ((AnchorPane) tab.getContent()).getChildren().getFirst();
        TextArea textArea = (TextArea) vbox.getChildren().getFirst();
        textArea.appendText(whoSendTheMessage + ": " + message.getContent() + "\n");
        if (!tab.isSelected()) {
            ((ChatTab) tab).incrementUnreadMessages();
        }
    }


    public void colorSelectionNotification() throws IOException, InterruptedException {
        List<Player> players = client.getPlayers(client.getIdGame());
        for (Player player : players) {
            if (!gameBoard.hasToken(player.getUsername()) && player.getTokenColor() != null) {
                String imagePath = Objects.requireNonNull(getClass().getResource("/Images/" + player.getTokenColor().getImageName())).toExternalForm();
                int playerIndex = players.indexOf(player);
                Tab tab = playerDeskTabPane.getTabs().get(playerIndex);

                ImageView imageView = createImageView(imagePath);
                tab.setGraphic(imageView);

                gameBoard.addToken(player.getUsername(), imagePath);
            }
        }
    }

    private ImageView createImageView(String imagePath) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(19);
        imageView.setFitWidth(39);
        imageView.setPreserveRatio(true);

        Image image = new Image(imagePath);
        imageView.setImage(image);
        return imageView;
    }

    public void handleCenterClick() {
        centerGameDesk();
    }
}

