package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.MainMenuStateController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuStateGUI implements ClientState {
    private final Stage stage;
    private final VirtualView client;
    private MainMenuStateController controller;

    public MainMenuStateGUI(Stage stage, VirtualView client) {
        this.stage = stage;
        this.client = client;
        WindowCloseHandler.setUpCloseRequestHandling(stage);
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/MainMenuState.fxml", "/styles.css");
    }


    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }
}
