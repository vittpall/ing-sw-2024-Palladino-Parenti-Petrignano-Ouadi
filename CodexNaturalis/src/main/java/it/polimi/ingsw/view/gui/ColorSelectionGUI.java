package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.core.ClientState;
import it.polimi.ingsw.view.gui.controller.ColorSelectionController;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the GUI for the color selection.
 * It selects and shows the available colors so the player can choose one.
 */
public class ColorSelectionGUI implements ClientState {


    private final BaseClient client;
    private final Stage stage;
    private ColorSelectionController controller;

    /**
     * Constructor
     *
     * @param stage  is a reference to the class Stage, it's indicate the scene of the GUI
     * @param client is a reference to the class stage BaseClient
     */
    public ColorSelectionGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/ColorSelection.fxml");

        try {
            controller.updateColorList(client.getAvailableColors());
        } catch (IOException | InterruptedException e) {
            controller.handleException(e);
        }
    }


    @Override
    public String toString() {
        return "ColorSelectionGUI";
    }

    /**
     * call the method to update the available colors
     *
     * @param availableColor is the list of available colors
     */
    public void refresh(ArrayList<TokenColor> availableColor) {
        Platform.runLater(() -> controller.updateColorList(availableColor));
    }
}
