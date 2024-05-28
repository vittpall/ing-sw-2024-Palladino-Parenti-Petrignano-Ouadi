package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.CreateGameMenuController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class CreateGameStateGUI implements ClientState {
    private final VirtualView client;
    private final Stage stage;
    private CreateGameMenuController controller;

    public CreateGameStateGUI(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/CreateGameState.fxml");
    }

    @Override
    public void inputHandler(int input) {
        // GUI doesn't typically use this method; interactions are handled by event handlers
    }

    @Override
    public void promptForInput() {
        // GUI interactions are direct, so this method might not be necessary
    }

    public String toString()
    {
        return "CreateGameStateGUI";
    }

    /**
     *
     */
    @Override
    public void refresh() {

    }

}
