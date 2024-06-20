package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.ObjectiveCardSelectionController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class ObjectiveCardSelectionStateGUI implements ClientState {
    private final BaseClient client;
    private final Stage stage;

    public ObjectiveCardSelectionStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() throws RemoteException {
        ObjectiveCardSelectionController controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/ObjectiveCardSelection.fxml");
        controller.loadCards();
    }

    public String toString() {
        return "ObjectiveCardSelectionStateGUI";
    }

}
