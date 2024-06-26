package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.controller.LobbyMenuController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

public class LobbyMenuStateGUI implements ClientState {

    public final BaseClient client;
    public Stage stage;
    private LobbyMenuController controller;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public LobbyMenuStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/LobbyMenuState.fxml");
        controller.initializeLobbyMenu();
    }


    @Override
    public String toString() {
        return "LobbyMenuStateGUI";
    }

    /**
     * This method sends a message that the game has been closed
     *
     * @param msg is the message to be sent
     */
    public void gameClosedNotification(String msg) {
        Platform.runLater(() -> controller.showGameClosedPopup(msg));

    }


}
