package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class ClientProxy implements VirtualView {
    final PrintWriter output;

    public ClientProxy(BufferedWriter output)
    {
        //print stuff to the user
        this.output = new PrintWriter(output);
    }

    @Override
    public VirtualServer getServer() {
        return null;
    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setCurrentState(ClientState state) {

    }

    @Override
    public String getUsername() {
        return "";
    }
}
