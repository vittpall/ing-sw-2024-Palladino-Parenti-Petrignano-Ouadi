package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.ClientState;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

/**
 * This class manages the GUI for the createGame state.
 * It allows the player to create a new game and choose the number of players.
 */
public class CreateGameStateGUI implements ClientState {
    private final BaseClient client;
    private final Stage stage;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public CreateGameStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        FXMLLoaderUtility.loadView(stage, client, "/fxml/CreateGameState.fxml");
    }

    @Override
    public String toString() {
        return "CreateGameStateGUI";
    }

}
