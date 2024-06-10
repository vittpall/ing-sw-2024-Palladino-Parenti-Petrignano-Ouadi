package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.LobbyMenuController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class LobbyMenuStateGUI implements ClientState {

    public final BaseClient client;
    public Stage stage;
    private LobbyMenuController controller;

    public LobbyMenuStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/LobbyMenuState.fxml");
    }


    public String toString() {
        return "LobbyMenuStateGUI";
    }

        public void refresh(String msg) {

    }


}
