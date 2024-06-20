package it.polimi.ingsw.gui.controller;

import it.polimi.ingsw.network.BaseClient;
import javafx.stage.Stage;

public interface FXMLController {
    void setClient(BaseClient client);

    void setStage(Stage stage);
}
