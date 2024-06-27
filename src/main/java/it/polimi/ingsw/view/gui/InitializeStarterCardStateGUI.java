package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import it.polimi.ingsw.view.ClientState;
import it.polimi.ingsw.view.gui.controller.InitializeStarterCardController;
import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * This class manages the GUI for the starter card initialization.
 * It shows the player the starter card (front and back) and after that it saves the player's choice.
 */
public class InitializeStarterCardStateGUI implements ClientState {


    private final BaseClient client;
    private final Stage stage;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public InitializeStarterCardStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() throws RemoteException {
        InitializeStarterCardController controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/InitializeStarterCard.fxml");
        controller.initializeStarterCard();
    }


    @Override
    public String toString() {
        return "InitializeStarterCardStateGUI";
    }


}
