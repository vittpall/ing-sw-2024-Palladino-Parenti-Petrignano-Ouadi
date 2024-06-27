package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.view.gui.controller.ObjectiveCardSelectionController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.rmi.RemoteException;

/**
 * This class manages the GUI for the private objective card selection state.
 * In this state it is shown 2 objective cards and the player has to choose the objective cards he wants to keep.
 */
public class ObjectiveCardSelectionStateGUI implements ClientState {
    private final BaseClient client;
    private final Stage stage;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage
     * @param client is a reference to the class BaseClient
     */
    public ObjectiveCardSelectionStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() throws RemoteException {
        ObjectiveCardSelectionController controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/ObjectiveCardSelection.fxml");
        controller.loadCards();
    }

    @Override
    public String toString() {
        return "ObjectiveCardSelectionStateGUI";
    }

}
