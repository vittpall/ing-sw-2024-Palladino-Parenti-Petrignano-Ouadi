package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.ClientState;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

/**
 * This class manages the GUI for the main menu state.
 * In this state the client has to choose a username before starting a game.
 */
public class MainMenuStateGUI implements ClientState {
    private final Stage stage;
    private final BaseClient client;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public MainMenuStateGUI(Stage stage, BaseClient client) {
        this.stage = stage;
        this.client = client;
        WindowCloseHandler.setUpCloseRequestHandling(stage);
    }

    @Override
    public void display() {
        FXMLLoaderUtility.loadView(stage, client, "/fxml/MainMenuState.fxml");
    }


    @Override
    public String toString() {
        return "MainMenuStateGUI";
    }

}
