package it.polimi.ingsw.gui;

import it.polimi.ingsw.gui.Controller.LobbyMenuController;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.util.FXMLLoaderUtility;
import javafx.stage.Stage;

public class LobbyMenuStateGUI implements ClientState {

    public final BaseClient client;
    public Stage stage;
    private LobbyMenuController controller;

    public LobbyMenuStateGUI(Stage stage, BaseClient client) {
        this.client = client;
        this.stage = stage;
    }


    @Override
    public void display() {
        controller = FXMLLoaderUtility.loadView(stage, client, "/fxml/LobbyMenuState.fxml");
    }

    @Override
    public void inputHandler(int input) {
        // Implementazione vuota o gestione input specifici se necessario
    }

    @Override
    public void promptForInput() {
        // Mostra prompt all'utente se applicabile in un contesto GUI
    }

    public String toString()
    {
        return "LobbyMenuStateGUI";
    }

    /**
     *
     */
    @Override
    public void refresh() {

    }


}
