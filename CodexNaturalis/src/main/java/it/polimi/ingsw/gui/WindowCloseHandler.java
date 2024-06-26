package it.polimi.ingsw.gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class WindowCloseHandler {

    /**
     * This method sets up the window close request handling
     * @param stage is a reference to the class Stage
     */
    public static void setUpCloseRequestHandling(Stage stage) {
        stage.setOnCloseRequest(event -> {
            event.consume(); // Consume the event so the window doesn't close immediately
            confirmAndClose();
        });
    }

    /**
     * This method waits for the user to confirm the exit and then closes the application
     */
    private static void confirmAndClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Choose your option.");

        // Showing the confirmation dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit(); // This closes the application
            }
        });
    }
}
