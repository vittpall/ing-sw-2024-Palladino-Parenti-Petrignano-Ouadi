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

public class CreateGameMenuController implements FXMLController {
    public Button exit;
    private Stage stage;
    @FXML
    private ChoiceBox<Integer> playerChoiceBox;
    @FXML
    private Label feedbackLabel;
    private BaseClient client;


    /**
     * this method creates a new game. the client has to select the number of players and then wait for other players
     * @throws RemoteException
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
     * this method closes the game if the client press EXIT
     * @throws RemoteException
     */
    public void handleExit() throws RemoteException {
        client.close();
    }

    /**
     * this method allows the client to turn back to the lobby menu from the CreateGame state
     * @throws RemoteException
     */
    public void handleBack() throws RemoteException {
        client.setCurrentState(new LobbyMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }


    /**
     *Constructor
     * @param client is the current client
     */
    public void setClient(BaseClient client) {
        this.client = client;
    }

    /**
     * Constructor
     * @param stage is the stage of the current state
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
