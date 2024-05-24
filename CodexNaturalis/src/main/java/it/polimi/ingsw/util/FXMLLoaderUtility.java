package it.polimi.ingsw.util;

import it.polimi.ingsw.gui.Controller.FXMLController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLLoaderUtility {
    public static <T extends FXMLController> T loadView(Stage stage, VirtualView client, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLLoaderUtility.class.getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();
            controller.setClient(client);
            controller.setStage(stage);

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }
}
