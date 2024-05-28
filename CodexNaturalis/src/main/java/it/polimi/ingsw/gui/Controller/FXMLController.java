package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.network.BaseClient;
import javafx.stage.Stage;

public interface FXMLController {
    void setClient(BaseClient client);

    void setStage(Stage stage);
}
