package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.view.gui.controller.GameController;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * This class manages the GUI for the game state.
 * It shows the game board, the chat, the cards and the players' points.
 * There is also a notification to indicates the turns and another one for the end of the game and the winner.
 */
public class GameStateGUI implements ClientState {

    private final BaseClient client;
    private final Stage stage;
    private GameController controller;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public GameStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/GameState.fxml");
        controller.initializeGame();
    }

    @Override
    public String toString() {
        return "GameState";
    }


    /**
     * This method is used to update the chat when it receives a message
     *
     * @param msg is the message received
     */
    public void updateChat(Message msg) {
        Platform.runLater(() -> controller.receiveMessage(msg));
    }

    /**
     * this method is used to update the number of points when a player has played a card
     *
     * @param username      is the username of the player that has played the card
     * @param playersPoints is a HashMap that contains the points of the players
     */
    public void cardPlayedRefresh(String username, HashMap<String, Integer> playersPoints) {
        Platform.runLater(() -> controller.cardPlayedNotification(username, playersPoints));
    }

    /**
     * This method is used to notified that the turn has changed
     *
     * @param usernameCurrentPlayer is the username of the current player that will be replaced by the next one
     */
    public void changeTurnNotified(String usernameCurrentPlayer) {
        Platform.runLater(() -> controller.setMyTurn(usernameCurrentPlayer));
    }

    /**
     * This method is used to notify that one player has reached 20 points and that the game is ending
     *
     * @param username  is the username of the player that has reached 20 points
     * @param gameState is the state of the game that caused the beginning of the last turn
     */
    public void lastTurnSetNotification(String username, GameState gameState) {
        Platform.runLater(() -> controller.lastTurnSetNotification(username, gameState));
    }

    /**
     * This method is used to notify that the game is ended and to show the winner. it also shows the scores of the players on the board
     *
     * @param winner        is the username of the winner
     * @param scores        is a HashMap that contains the scores of the players
     * @param playersTokens are the tokens of the players
     */
    public void endGameNotification(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        Platform.runLater(() -> controller.endGameNotification(winner, scores, playersTokens));
    }

    /**
     * This method sends a notification to the controller to update the board
     */
    public void colorSelectionNotification() {
        Platform.runLater(() -> {
            try {
                controller.colorSelectionNotification();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
