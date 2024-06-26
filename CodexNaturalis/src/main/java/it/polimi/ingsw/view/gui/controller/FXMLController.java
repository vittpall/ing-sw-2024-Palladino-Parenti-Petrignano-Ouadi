package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.network.BaseClient;
import javafx.stage.Stage;

/**
 * Interface that defines the methods that every controller of every FXML file must implement
 */
public interface FXMLController {
    /**
     * This method is used to set the BaseClient
     *
     * @param client refers to the current client
     */
    void setClient(BaseClient client);

    /**
     * This method is used to set the Stage
     *
     * @param stage refers to the stage of the window
     */
    void setStage(Stage stage);
}
