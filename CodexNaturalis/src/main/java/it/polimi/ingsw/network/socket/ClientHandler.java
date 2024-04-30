package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.network.rmi.VirtualView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientHandler {
    final SocketServer server;
    final BufferedReader input;
    final LobbyController controller;
    final VirtualView view;

    public ClientHandler(SocketServer server, BufferedReader input, BufferedWriter output, LobbyController controller) {
        this.server = server;
        this.input = input;
        this.controller = controller;
        this.view = new ClientProxy(output);
    }

    public void runVirtualView() throws IOException {
        String line;
        // Read message type
        while ((line = input.readLine()) != null) {
            // Read message and perform action
            switch (line) {

                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }
}
