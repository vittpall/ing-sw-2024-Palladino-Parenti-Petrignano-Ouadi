package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.controller.WaitingForPlayersController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class WaitingForPlayersGUI implements ClientState {
    public final BaseClient client;
    public Stage stage;
    private WaitingForPlayersController controller;

    public WaitingForPlayersGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() throws RemoteException {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/WaitingForPlayers.fxml");
        controller.initializeWaitingForPlayers();
    }

    public String toString() {
        return "WaitingForPlayersGUI";
    }

    public void refresh(ArrayList<Player> players, int nOfMissingPlayers) {
        Platform.runLater(() -> controller.handleServerNotification(players, nOfMissingPlayers));
    }
}
