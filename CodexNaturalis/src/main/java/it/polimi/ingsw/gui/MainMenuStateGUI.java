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

            //to initialize all the pages of the GUI. We could create an array of pages (which are an enum with all pages and associated to each of them their respetively FXML file) and then initialize them all in a loop
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
