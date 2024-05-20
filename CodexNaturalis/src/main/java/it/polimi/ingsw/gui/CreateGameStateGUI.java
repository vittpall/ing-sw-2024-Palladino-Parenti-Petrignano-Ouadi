package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.CreateGameMenuController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CreateGameStateGUI implements ClientState {


    private final VirtualView client;
    private final Stage stage;

    public CreateGameStateGUI(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateGameState.fxml"));
            loader.setController(new CreateGameMenuController(stage, client));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }

    @Override
    public void inputHandler(int input) {
        // GUI doesn't typically use this method; interactions are handled by event handlers
    }

    @Override
    public void promptForInput() {
        // GUI interactions are direct, so this method might not be necessary
    }

}
