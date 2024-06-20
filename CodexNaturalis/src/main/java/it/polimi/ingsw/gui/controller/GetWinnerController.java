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
