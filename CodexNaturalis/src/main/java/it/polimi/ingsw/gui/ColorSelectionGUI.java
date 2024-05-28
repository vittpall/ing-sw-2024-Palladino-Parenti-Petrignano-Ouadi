package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.ColorSelectionController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

import java.io.IOException;

public class ColorSelectionGUI implements ClientState {


    private final BaseClient client;
    private final Stage stage;
    private ColorSelectionController controller;

    public ColorSelectionGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/ColorSelection.fxml");

        controller.updateColorList();
    }


    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }

    public String toString()
    {
        return "ColorSelectionGUI";
    }

    /**
     *
     */
    @Override
    public void refresh() {
        controller.updateColorList();

    }
}
