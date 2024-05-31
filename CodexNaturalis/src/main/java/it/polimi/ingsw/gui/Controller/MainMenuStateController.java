package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.gui.LobbyMenuStateGUI;
import it.polimi.ingsw.network.BaseClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuStateController implements FXMLController {

    public Button playButton;
    private BaseClient client;
    private Stage stage;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handlePlayButton() throws IOException, ClassNotFoundException, InterruptedException {
        String username = usernameField.getText();
        if (!username.isEmpty() && this.client != null && client.checkUsername(username)) {
            client.setUsername(username);
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            errorLabel.setText("");
            client.showState();
        } else {
            errorLabel.setManaged(true);
            errorLabel.setText("Invalid username, please try again.");
            usernameField.setText("");
        }
    }

    public void setClient(BaseClient client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
