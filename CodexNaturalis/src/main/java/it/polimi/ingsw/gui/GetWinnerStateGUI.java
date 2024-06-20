package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.controller.GetWinnerController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.util.HashMap;

public class GetWinnerStateGUI implements ClientState {
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


    public void initializeWinner(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        display();
        controller.initializeWinner(winner, scores, playersTokens);
    }
}
