package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.GameBoard;
import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class GetWinnerController implements FXMLController {
    public Label usernameWinner;
    public Button returnToLobbyButton;
    public AnchorPane gameBoardAnchorPane;
    BaseClient client;
    Stage stage;

    @Override
    public void setClient(BaseClient client) {
        this.client = client;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * this method get the winner at the end of the game and updates the score on the gameBoard
     * @param winner is a string with the name of the winner player
     * @param scores is the score of the players that will bel represented on the board
     * @param playersTokens is the Token that represent the score of the player on the board
     */
    public void initializeWinner(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        usernameWinner.setText(winner);
        client.setIdGameNull();
        GameBoard gameBoard = new GameBoard(gameBoardAnchorPane);
        for (String username : scores.keySet()) {
            String imagePath = "/Images/" + playersTokens.get(username).getImageName();
            gameBoard.addToken(username, Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
            gameBoard.updateTokenPosition(username, scores.get(username));
        }
    }


    /**
     * this method returns the client to the lobby menu state if the client press the button return to lobby at the end of the game
     */
    public void handleReturnToLobby() {
        try {
            client.returnToLobby();
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
