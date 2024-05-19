package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuStateGUI implements ClientState {
    private Stage stage;
    public Label welcomeLabel;
    public Button playButton;
    private VirtualView client;

    @FXML
    private TextField usernameField;

    @FXML
    private Label errorLabel;

    public MainMenuStateGUI(Stage stage, VirtualView client) {
        this.stage = stage;
        this.client = client;
        WindowCloseHandler.setUpCloseRequestHandling(stage);
    }

    public MainMenuStateGUI() {
    }

    @FXML
    private void handlePlayButton() throws IOException, ClassNotFoundException, InterruptedException {
        String username = usernameField.getText();
        if (client != null && client.checkUsername(username)) {
            client.setUsername(username);
            client.setCurrentState(new LobbyMenuStateGUI(stage, client));
            errorLabel.setText("");
        } else {
            errorLabel.setText("Invalid username, please try again.");
            usernameField.setText("");
        }
    }

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it.polimi.ingsw.gui/MainMenuState.fxml"));
            Parent root = loader.load();
            loader.setController(this);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }
}
