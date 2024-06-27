package it.polimi.ingsw.util;

import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.view.gui.controller.FXMLController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class FXMLLoaderUtility {

    /**
     * this method loads the view of the FXML file.
     * It sets the client and the new stage of the controller.
     *
     * @param stage    is the new stage of the controller
     * @param client   is the client that communicates with the server
     * @param fxmlPath is the path of the FXML file
     * @param <T>      is the type of the controller
     * @return the controller of the FXML file
     */
    public static <T extends FXMLController> T loadView(Stage stage, BaseClient client, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(FXMLLoaderUtility.class.getResource(fxmlPath));
            Parent root = loader.load();

            T controller = loader.getController();
            controller.setClient(client);
            controller.setStage(stage);

            Scene scene = new Scene(root);
            stage.setTitle("Codex Naturalis");

            Image icon = new Image(Objects.requireNonNull(FXMLLoaderUtility.class.getResourceAsStream("/Images/CodexLogo.jpg")));
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();

            return controller;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load FXML", e);
        }
    }
}
