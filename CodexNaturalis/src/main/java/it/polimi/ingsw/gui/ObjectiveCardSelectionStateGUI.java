package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.ObjectiveCardSelectionController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ObjectiveCardSelectionStateGUI implements ClientState {
    public final VirtualView client;
    public Stage stage;

    public ObjectiveCardSelectionStateGUI(Stage stage, VirtualView client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ObjectiveCardSelection.fxml"));
            loader.setController(new ObjectiveCardSelectionController(stage, client));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Lobby Menu");
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
