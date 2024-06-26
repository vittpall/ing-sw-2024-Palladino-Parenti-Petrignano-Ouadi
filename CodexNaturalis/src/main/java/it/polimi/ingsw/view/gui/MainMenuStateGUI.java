package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

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
