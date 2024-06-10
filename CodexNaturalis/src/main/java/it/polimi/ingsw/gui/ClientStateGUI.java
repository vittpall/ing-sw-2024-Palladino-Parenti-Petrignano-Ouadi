package it.polimi.ingsw.gui;

import it.polimi.ingsw.core.ClientState;

public interface ClientStateGUI extends ClientState {
    void refresh(String msg);
}
