package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.WaitingForPlayersController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

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

   

    

    public String toString() {
        return "WaitingForPlayersGUI";
    }

        public void refresh(String msg) {
        Platform.runLater(() -> {
            controller.handleServerNotification(msg);
        });
    }
}
