package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.CreateGameStateGUI;
import it.polimi.ingsw.gui.JoinGameMenuStateGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class LobbyMenuController implements FXMLController {

    public BorderPane popup;
    public Label popupLabel;
    private BaseClient client;
    private Stage stage;

    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initializeLobbyMenu() {
        popup.setVisible(false);
    }

    @FXML
    private void handleCreateGame() throws RemoteException {
        client.setCurrentState(new CreateGameStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    @FXML
    private void handleJoinGame() throws RemoteException {
        client.setCurrentState(new JoinGameMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleClosePopUp() {
        popup.setVisible(false);
    }

    public void showGameClosedPopup(String msg) {
        popupLabel.setText(msg);
        popup.setVisible(true);
    }
}
