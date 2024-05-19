package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.Client.SocketClient;
import it.polimi.ingsw.network.socket.ClientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.ClientToServerMsg.SendMessageMsg;
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


    public void runVirtualView() throws IOException, ClassNotFoundException, InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ClientToServerMsg request;
        ServerToClientMsg response;
        // Read message type
        while ((request = (ClientToServerMsg)input.readObject()) != null) {
            if(request instanceof SendMessageMsg){
               ReturnableObject message = request.functionToCall(controller);
               SocketServer.broadCastWhatHappened(message, request.getType(), request.getIdGame());
            }
            if(request.getDoItNeedToBeBroadcasted()){
                ReturnableObject responseReturnable = request.functionToCall(controller);
                ReturnableObject messageToBroadCast = new ReturnableObject<>();
                messageToBroadCast.setResponseReturnable(request.getBroadCastMessage());
                SocketServer.broadCastWhatHappened(messageToBroadCast, request.getType(), request.getIdGame());
            }
            else
            {
                response = new ServerToClientMsg(request.getType(), false);
                response.setResponse(request.functionToCall(controller));
                output.writeObject(response);
                output.flush();
                output.reset();
            }


        }
    }

    public void sendMessage(ServerToClientMsg msgToBroadCast) throws IOException {
        output.writeObject(msgToBroadCast);
        output.flush();
        output.reset();
    }
}
