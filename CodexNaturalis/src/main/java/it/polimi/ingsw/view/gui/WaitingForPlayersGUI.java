package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.view.gui.controller.WaitingForPlayersController;
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

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public WaitingForPlayersGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() throws RemoteException {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/WaitingForPlayers.fxml");
        controller.initializeWaitingForPlayers();
    }

    @Override
    public String toString() {
        return "WaitingForPlayersGUI";
    }

    /**
     * This method refreshes the players and the number of missing players
     *
     * @param players           is the list of players that are entered in the game
     * @param nOfMissingPlayers is the number of missing players
     */
    public void refresh(ArrayList<Player> players, int nOfMissingPlayers) {
        Platform.runLater(() -> controller.handleServerNotification(players, nOfMissingPlayers));
    }
}
