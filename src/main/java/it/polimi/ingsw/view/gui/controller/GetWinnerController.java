package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.view.gui.GameBoard;
import it.polimi.ingsw.view.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is the controller for the GetWinner.fxml file
 */
public class GetWinnerController implements FXMLController {
    public Label usernameWinner;
    public Button returnToLobbyButton;
    public AnchorPane gameBoardAnchorPane;
    public Label playerYellow;
    public Label playerBlue;
    public Label playerGreen;
    public Label playerRed;
    public HBox hBoxYellow;
    public HBox hBoxBlue;
    public HBox hBoxGreen;
    public HBox hBoxRed;
    public VBox tokenColorBox;
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
     * This method get the winner at the end of the game and updates the score on the gameBoard
     *
     * @param winner        is a String with the name of the winner player
     * @param scores        is an HaspMap representing the score of the players that will bel shown on the board
     * @param playersTokens is an HaspMap representing the Token of the player
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
        boolean present;
        for(TokenColor color : TokenColor.values()){
            present=false;
            switch (color){
                case YELLOW:
                    for(String username : playersTokens.keySet()){
                        if(playersTokens.get(username).equals(TokenColor.YELLOW)){
                            playerYellow.setText(username);
                            present=true;
                        }
                    }
                    if(!present){
                        hBoxYellow.getChildren().clear();
                        tokenColorBox.getChildren().remove(hBoxYellow);
                    }
                    break;
                case BLUE:
                    for(String username : playersTokens.keySet()){
                        if(playersTokens.get(username).equals(TokenColor.BLUE)){
                            playerBlue.setText(username);
                            present=true;
                        }
                    }
                    if(!present){
                        hBoxBlue.getChildren().clear();
                        tokenColorBox.getChildren().remove(hBoxBlue);
                    }
                    break;
                case GREEN:
                    for(String username : playersTokens.keySet()){
                        if(playersTokens.get(username).equals(TokenColor.GREEN)){
                            playerGreen.setText(username);
                            present=true;
                        }
                    }
                    if(!present){
                        hBoxGreen.getChildren().clear();
                        tokenColorBox.getChildren().remove(hBoxGreen);
                    }
                    break;
                case RED:
                    for(String username : playersTokens.keySet()){
                        if(playersTokens.get(username).equals(TokenColor.RED)){
                            playerRed.setText(username);
                            present=true;
                        }
                    }
                    if(!present){
                        hBoxRed.getChildren().clear();
                        tokenColorBox.getChildren().remove(hBoxRed);
                    }
                    break;
            }
        }
    }


    /**
     * This method returns the client to the lobby menu state if the client press the button return to lobby at the end of the game
     */
    public void handleReturnToLobby() throws RemoteException {
        try {
            client.returnToLobby();
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (IOException | InterruptedException e) {
            throw new RemoteException();
        }
    }
}
