package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.gui.WaitingForPlayersGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This class is the controller for the CreateGameMenu.fxml file
 */
public class CreateGameMenuController implements FXMLController {
    public Button exit;
    private Stage stage;
    @FXML
    private ChoiceBox<Integer> playerChoiceBox;
    @FXML
    private Label feedbackLabel;
    private BaseClient client;


    /**
     * This method creates a new game. It changes the state of the client to WaitingForPlayersGUI
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    @FXML
    private void handleCreateGame() throws RemoteException {
        Integer nPlayers = playerChoiceBox.getValue();
        if (nPlayers == null) {
            feedbackLabel.setText("Please select the number of players.");
            return;
        }
        try {
            feedbackLabel.setText("Creating game and waiting for the players...");
            client.createGame(client.getUsername(), nPlayers);
            client.setCurrentState(new WaitingForPlayersGUI(stage, client));
            client.getClientCurrentState().display();
        } catch (RemoteException e) {
            throw new RemoteException();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error in creating game");
        }
    }

    /**
     * This method closes the game if the client press EXIT
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleExit() throws RemoteException {
        client.close();
    }

    /**
     * This method allows the client to turn back to the lobby menu from the CreateGame state
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleBack() throws RemoteException {
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    @Override
    public void setClient(BaseClient client) {
        this.client = client;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
