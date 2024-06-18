package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class GetWinnerController implements FXMLController{
    public Label usernameWinner;
    public Button returnToLobbyButton;
    public Button exitButton;
    BaseClient client;
    Stage stage;
    @Override
    public void setClient(BaseClient client) {
        this.client=client;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage=stage;
    }

    public void initializeWinner(String winner, HashMap<String, Integer>scores) {
        usernameWinner.setText(winner);
        //TODO set scores into the board
    }

    public void handleReturnToLobby() {
        try {
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
