package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.GameController;
import it.polimi.ingsw.gui.Controller.GetWinnerController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.HashMap;

public class GetWinnerStateGUI implements ClientStateGUI {
    private final BaseClient client;
    private final Stage stage;
    private GetWinnerController controller;

    public GetWinnerStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }
    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/GetWinnerState.fxml");
    }

    public String toString() {
        return "GetWinnerStateGUI";
    }
    @Override
    public void refresh(String msg) {

    }

    public void initializeWinner(String winner, HashMap<String, Integer> scores) {
        display();
        controller.initializeWinner(winner, scores);
    }
}
