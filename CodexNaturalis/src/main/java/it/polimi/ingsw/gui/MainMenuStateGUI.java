package it.polimi.ingsw.gui;

import it.polimi.ingsw.tui.ClientState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuStateGUI implements ClientState {
    private final Stage stage;

    public MainMenuStateGUI(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainMenuState.fxml"));
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
