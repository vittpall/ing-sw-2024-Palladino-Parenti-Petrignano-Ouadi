package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.CreateGameStateGUI;
import it.polimi.ingsw.gui.JoinGameMenuStateGUI;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class LobbyMenuController implements FXMLController {

    private BaseClient client;
    private Stage stage;

    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleCreateGame() {
        try {
            client.setCurrentState(new CreateGameStateGUI(stage, client));
            client.showState();
        } catch (RemoteException e) {
            showAlert("Failed to create a new game: " + e.getMessage());
        }
    }

    @FXML
    private void handleJoinGame() {
        try {
            client.setCurrentState(new JoinGameMenuStateGUI(stage, client));
            client.showState();
        } catch (RemoteException e) {
            showAlert("Failed to join a game: " + e.getMessage());
        }
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
