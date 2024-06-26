package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.controller.JoinGameMenuController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class JoinGameMenuStateGUI implements ClientState {


    private final BaseClient client;
    private final Stage stage;
    private JoinGameMenuController controller;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public JoinGameMenuStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/JoinGameMenuState.fxml");
        try {
            HashMap<Integer, Integer[]> availableGames = new HashMap<>();
            for (int idGame : client.getNotStartedGames()) {
                availableGames.put(idGame, new Integer[]{client.getnPlayer(idGame), (client.getnPlayer(idGame) - client.getPlayers(idGame).size())});
            }
            controller.updateGamesList(availableGames);
        } catch (IOException | InterruptedException e) {
            controller.handleException(e);
        }
    }

    /**
     * This method refreshes the list of available games so other players can join
     *
     * @param availableGames is the list of available games
     */
    public void refresh(HashMap<Integer, Integer[]> availableGames) {
        Platform.runLater(() -> controller.updateGamesList(availableGames));
    }


    @Override
    public String toString() {
        return "JoinGameMenuStateGUI";
    }

}
