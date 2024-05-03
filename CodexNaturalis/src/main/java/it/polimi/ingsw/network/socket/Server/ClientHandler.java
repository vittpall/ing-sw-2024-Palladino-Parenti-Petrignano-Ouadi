package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.Controller.LobbyController;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.ClientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.*;

public class ClientHandler {
    final SocketServer server;
    final ObjectInputStream input;
    final LobbyController controller;
    final ObjectOutputStream output;

    public ClientHandler(SocketServer server, ObjectInputStream input, ObjectOutputStream output, LobbyController controller) {
        this.server = server;
        this.input = input;
        this.controller = controller;
        this.output = output;
    }

    public void runVirtualView() throws IOException, ClassNotFoundException {
        ClientToServerMsg request;
        ServerToClientMsg response;
        // Read message type
        while ((request = (ClientToServerMsg)input.readObject()) != null) {
            response = request.getTypeofResponse();
            response.setResponse(request.functionToCall(controller));
            output.writeObject(response);
            output.flush();
            output.reset();

        }
    }
}
