package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.view.gui.controller.GetWinnerController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * This class manages the GUI for the winner state.
 * It shows the winner of the game and the scores of the players.
 * It is also shown the tokens of the players and their scores on the board.
 */
public class GetWinnerStateGUI implements ClientState {
    private final BaseClient client;
    private final Stage stage;
    private GetWinnerController controller;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public GetWinnerStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/GetWinnerState.fxml");
    }

    @Override
    public String toString() {
        return "GetWinnerStateGUI";
    }


    /**
     * This method is used to save the winner of the game
     *
     * @param winner        is the username of the winner
     * @param scores        is a HashMap that contains the scores of the players
     * @param playersTokens is the color of the tokens of the players
     */
    public void initializeWinner(String winner, HashMap<String, Integer> scores, HashMap<String, TokenColor> playersTokens) {
        display();
        controller.initializeWinner(winner, scores, playersTokens);
    }
}
