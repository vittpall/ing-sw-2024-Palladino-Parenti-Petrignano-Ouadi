package it.polimi.ingsw.gui;

import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.tui.ClientState;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyMenuStateGUI implements ClientState {

    public final RMIClient client;
    public Stage stage;

    public LobbyMenuStateGUI(RMIClient client, Stage stage) {
        this.client = client;
        this.stage = stage;
    }

    @Override
    public void display() {

    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {

    }

    @Override
    public void promptForInput() {

    }
}
