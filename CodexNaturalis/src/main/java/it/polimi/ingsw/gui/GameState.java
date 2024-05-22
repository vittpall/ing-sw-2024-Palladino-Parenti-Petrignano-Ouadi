package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.GameController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.io.IOException;

public class GameState implements ClientState {

    private final VirtualView client;
    private final Stage stage;
    private GameController controller;

    public GameState(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/GameState.fxml", "/styles.css");
        controller.initializeGame();
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }
}
