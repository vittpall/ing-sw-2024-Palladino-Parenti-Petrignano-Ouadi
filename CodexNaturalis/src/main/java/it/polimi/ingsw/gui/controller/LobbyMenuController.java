package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.gui.CreateGameStateGUI;
import it.polimi.ingsw.gui.JoinGameMenuStateGUI;
import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This class is the controller for the LobbyMenu.fxml file
 */
public class LobbyMenuController implements FXMLController {

    public BorderPane popup;
    public Label popupLabel;

    public Button exit;
    private BaseClient client;
    private Stage stage;

    @Override
    public void setClient(BaseClient client) {
        this.client = client;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * this method sets the popup message invisible. it will set visible in case of error
     */
    public void initializeLobbyMenu() {
        popup.setVisible(false);
    }

    /**
     * this method handle the case where the client press "Create Game" button and go to the CreateGame state
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    @FXML
    private void handleCreateGame() throws RemoteException {
        client.setCurrentState(new CreateGameStateGUI(stage, client));
        client.getClientCurrentState().display();
    }

    /**
     * this method handle the case where the client press "Join Game" button and go to the JoinGameMenu state
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    @FXML
    private void handleJoinGame() throws RemoteException {
        client.setCurrentState(new JoinGameMenuStateGUI(stage, client));
        client.getClientCurrentState().display();
    }


    /**
     * this method closes the game if the client press EXIT
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleExit() throws RemoteException {
        client.close();
    }

    /**
     * set the error message
     *
     * @param content content of the message error
     */
    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * this method set the PopUp invisible after the client close it
     */
    public void handleClosePopUp() {
        popup.setVisible(false);
    }

    /**
     * this method set the PopUp visible in case of error or a message
     *
     * @param msg message of error
     */
    public void showGameClosedPopup(String msg) {
        popupLabel.setText(msg);
        popup.setVisible(true);
    }
}
