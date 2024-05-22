package it.polimi.ingsw.gui.Controller;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import javafx.stage.Stage;

public interface FXMLController {
    void setClient(VirtualView client);
    void setStage(Stage stage);
}
