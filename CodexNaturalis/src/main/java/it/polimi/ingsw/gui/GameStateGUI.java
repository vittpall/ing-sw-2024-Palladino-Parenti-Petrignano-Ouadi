package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.GameController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

public class GameStateGUI implements ClientState {

    private final BaseClient client;
    private final Stage stage;
    private GameController controller;

    public GameStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/GameState.fxml");
        controller.initializeGame();
    }

   

    

    public String toString()
    {
        return "GameState";
    }

        public void refresh(String msg) {

    }

    public void cardPlayedRefresh(String username) {
        //TODO modificare il desk dell'utente con nome username e i suoi punti sulla board
    }
    public void changeTurnNotified(String usernameCurrentPlayer) {
        Platform.runLater(() -> controller.setMyTurn(client.getUsername().equals(usernameCurrentPlayer)));
    }
}
