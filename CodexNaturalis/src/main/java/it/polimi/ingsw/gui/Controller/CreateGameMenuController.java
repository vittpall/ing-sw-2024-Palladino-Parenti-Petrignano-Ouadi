package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.ColorSelectionGUI;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateGameMenuController {
    private final Stage stage;
    @FXML
    private ChoiceBox<Integer> playerChoiceBox;
    @FXML
    private Label feedbackLabel;
    private final VirtualView client;

    public CreateGameMenuController(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }


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
            client.setCurrentState(new ColorSelectionGUI(stage, client));
            client.showState();
        } catch (Exception e) {
            feedbackLabel.setText("Error creating game. Please try again: " + e.getMessage());
        }
    }
}
