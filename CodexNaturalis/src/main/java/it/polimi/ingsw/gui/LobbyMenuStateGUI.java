package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class LobbyMenuStateGUI implements ClientState {

    public final VirtualView client;
    public Stage stage;
    public Button createGameButton;
    public Button joinGameButton;

    public LobbyMenuStateGUI(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }

    @FXML
    private void handleCreateGame(ActionEvent event) {
        try {
            client.setCurrentState(new CreateGameStateGUI(client, stage)); // Aggiornare con corretti parametri
        } catch (RemoteException e) {
            showAlert("Failed to create a new game: " + e.getMessage());
        }
    }

    @FXML
    private void handleJoinGame(ActionEvent event) {
       /* try {
            client.setCurrentState(new JoinGameMenuStateGUI(client, stage)); // Aggiornare con corretti parametri
        } catch (RemoteException e) {
            showAlert("Failed to join a game: " + e.getMessage());
        }*/
    }

    @Override
    public void display() {
        // Qui potresti mostrare una finestra o aggiornare l'UI se necessario
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
