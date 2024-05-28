package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.WaitingForPlayersController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitingForPlayersGUI implements ClientState {
    public final BaseClient client;
    public Stage stage;
    private WaitingForPlayersController controller;

    public WaitingForPlayersGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/WaitingForPlayers.fxml");
        controller.initializeWaitingForPlayers();
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }

    public String toString() {
        return "WaitingForPlayersGUI";
    }

    /**
     *
     */
    @Override
    public void refresh(String msg) {
        Platform.runLater(() -> {
            controller.handleServerNotification(msg);
        });
    }
}
