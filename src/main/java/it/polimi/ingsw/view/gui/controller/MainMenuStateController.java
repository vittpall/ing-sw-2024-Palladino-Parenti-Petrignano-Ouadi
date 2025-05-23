package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.view.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This class is the controller for the MainMenu.fxml file
 */
public class MainMenuStateController implements FXMLController {

    public Button playButton;

    public Button exit;
    private BaseClient client;
    private Stage stage;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    /**
     * This method is used to handle the play button. It sets the username and, only if it is valid, goes to the LobbyMenu state
     *
     * @throws IOException          if there is a problem with I/O operations
     * @throws InterruptedException if the thread running is interrupted
     */
    @FXML
    private void handlePlayButton() throws IOException, InterruptedException {
        String username = usernameField.getText();
        if (!username.isEmpty() && this.client != null && client.checkUsername(username)) {
            client.setUsername(username);
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            errorLabel.setText("");
            client.getClientCurrentState().display();
        } else {
            errorLabel.setManaged(true);
            errorLabel.setText("Invalid username, please try again.");
            usernameField.setText("");
        }
    }

    /**
     * this method closes the game if the client press EXIT
     *
     * @throws RemoteException if there is a problem with the remote connection
     */
    public void handleExit() throws RemoteException {
        client.close();
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
