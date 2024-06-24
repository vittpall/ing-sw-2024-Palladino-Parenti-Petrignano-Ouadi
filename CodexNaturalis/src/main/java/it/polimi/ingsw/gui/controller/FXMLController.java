package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.network.BaseClient;
import javafx.stage.Stage;

public interface FXMLController {
    /**
     *
     * @param client refers to the current client
     */
    void setClient(BaseClient client);

    /**
     *
     * @param stage refers to the stage of the window
     */
    void setStage(Stage stage);
}
