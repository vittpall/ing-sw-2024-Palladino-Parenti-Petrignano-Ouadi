package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.controller.InitializeStarterCardController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class InitializeStarterCardStateGUI implements ClientState {


    private final BaseClient client;
    private final Stage stage;

    public InitializeStarterCardStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        InitializeStarterCardController controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/InitializeStarterCard.fxml");
        controller.initializeStarterCard();
    }


    public String toString() {
        return "InitializeStarterCardStateGUI";
    }


}
