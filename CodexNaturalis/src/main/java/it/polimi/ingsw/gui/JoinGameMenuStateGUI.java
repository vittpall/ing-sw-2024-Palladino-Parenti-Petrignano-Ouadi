package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.JoinGameMenuController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinGameMenuStateGUI implements ClientState {


    private final BaseClient client;
    private final Stage stage;
    private JoinGameMenuController controller;

    public JoinGameMenuStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/JoinGameMenuState.fxml");
        controller.updateGamesList();
    }

    public void refresh()
    {
        controller.updateGamesList();
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }

    public String toString()
    {
        return "JoinGameMenuStateGUI";
    }

}
