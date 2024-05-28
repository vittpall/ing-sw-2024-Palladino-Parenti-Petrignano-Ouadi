package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.GameController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.io.IOException;

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

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }

    public String toString()
    {
        return "GameState";
    }

    /**
     *
     */
    @Override
    public void refresh() {

    }
}
