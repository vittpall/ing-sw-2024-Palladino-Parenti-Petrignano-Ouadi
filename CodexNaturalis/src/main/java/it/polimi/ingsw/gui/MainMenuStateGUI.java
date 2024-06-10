package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.MainMenuStateController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class MainMenuStateGUI implements ClientState {
    private final Stage stage;
    private final BaseClient client;
    private MainMenuStateController controller;

    public MainMenuStateGUI(Stage stage, BaseClient client) {
        this.stage = stage;
        this.client = client;
        WindowCloseHandler.setUpCloseRequestHandling(stage);
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/MainMenuState.fxml");
    }


   

    

    public String toString()
    {
        return "MainMenuStateGUI";
    }

        public void refresh(String msg) {

    }
}
