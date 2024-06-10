package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.gui.Controller.ColorSelectionController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

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


    public String toString() {
        return "ColorSelectionGUI";
    }

        public void refresh(String msg) {
        Platform.runLater(() -> controller.updateColorList());
    }
}
