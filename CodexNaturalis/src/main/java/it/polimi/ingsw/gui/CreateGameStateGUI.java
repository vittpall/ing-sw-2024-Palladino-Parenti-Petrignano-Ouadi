package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class CreateGameStateGUI implements ClientState {
    private final BaseClient client;
    private final Stage stage;

    public CreateGameStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        FXMLLoaderUtility.loadView(stage, client, "/fxml/CreateGameState.fxml");
    }


    public String toString() {
        return "CreateGameStateGUI";
    }

}
