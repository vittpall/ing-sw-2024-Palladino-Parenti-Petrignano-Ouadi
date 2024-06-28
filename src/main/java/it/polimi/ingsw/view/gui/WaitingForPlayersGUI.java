package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import it.polimi.ingsw.view.ClientState;
import it.polimi.ingsw.view.gui.controller.WaitingForPlayersController;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * This class manages the GUI for the waiting for players state.
 * In this state the player has to wait for other players to join the game. Meanwhile, the player can see the list
 * of players that are entered in the game and the number of missing players.
 */
public class WaitingForPlayersGUI implements ClientState {
    private final BaseClient client;
    private final Stage stage;
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
