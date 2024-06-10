package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.GameController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class GameState implements ClientState {

    private final BaseClient client;
    private final Stage stage;
    private GameController controller;

    public GameState(Stage stage, BaseClient client) {
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
}
