package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class LobbyMenuStateGUI implements ClientState {

    public final VirtualView client;
    public Stage stage;

    public LobbyMenuStateGUI(Stage stage, VirtualView client) {
        this.client = client;
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

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LobbyMenuState.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Lobby Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void inputHandler(int input) {
        // Implementazione vuota o gestione input specifici se necessario
    }

    @Override
    public void promptForInput() {
        // Mostra prompt all'utente se applicabile in un contesto GUI
    }

    private void showAlert(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
