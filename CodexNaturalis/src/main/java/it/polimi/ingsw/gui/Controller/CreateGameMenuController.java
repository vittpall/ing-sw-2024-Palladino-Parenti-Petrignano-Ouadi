package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.ColorSelectionGUI;
import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.gui.WaitingForPlayersGUI;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateGameMenuController  implements FXMLController {
    private Stage stage;
    @FXML
    private ChoiceBox<Integer> playerChoiceBox;
    @FXML
    private Label feedbackLabel;
    private BaseClient client;


    @FXML
    private void handleCreateGame() {
        Integer nPlayers = playerChoiceBox.getValue();
        if (nPlayers == null) {
            feedbackLabel.setText("Please select the number of players.");
            return;
        }
        try {
            feedbackLabel.setText("Creating game and waiting for the players...");
            client.createGame(client.getUsername(), nPlayers);
            client.setCurrentState(new WaitingForPlayersGUI(stage, client));
            client.showState();
        } catch (Exception e) {
            feedbackLabel.setText("Error creating game. Please try again: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void handleBack() {
        try {
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            client.showState();
            //TODO to remove the username from the game
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
