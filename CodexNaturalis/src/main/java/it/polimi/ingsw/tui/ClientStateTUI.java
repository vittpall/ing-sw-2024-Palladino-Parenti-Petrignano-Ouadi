package it.polimi.ingsw.tui;

import it.polimi.ingsw.core.ClientState;

import java.io.IOException;

public interface ClientStateTUI extends ClientState {
    void inputHandler(int input) throws IOException, InterruptedException;

    void promptForInput();
}
