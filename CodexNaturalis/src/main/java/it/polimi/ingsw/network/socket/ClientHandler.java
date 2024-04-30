package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.rmi.VirtualView;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ClientHandler {
    final SocketServer server;
    final BufferedReader input;
   // final VirtualView view;

    public ClientHandler(SocketServer server, BufferedReader input, BufferedWriter output) {
        this.server = server;
        this.input = input;
     //   this.view = new ClientProxy(output);
    }
}
