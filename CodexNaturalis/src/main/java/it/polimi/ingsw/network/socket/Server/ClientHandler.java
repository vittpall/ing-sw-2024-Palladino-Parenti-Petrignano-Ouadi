package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.observer.Observer;
import it.polimi.ingsw.model.observer.Observable;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;
import it.polimi.ingsw.network.socket.ClientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.ClientToServerMsg.SendMessageMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

public class ClientHandler implements GameListener {
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
        try {
            while ((request = (ClientToServerMsg) input.readObject()) != null) {
                response = new ServerToClientMsg(request.getType(), false);
                response.setResponse(request.functionToCall(controller, (GameListener) this));
                output.writeObject(response);
                output.flush();
                output.reset();
                /*if (request instanceof SendMessageMsg) {
                    ReturnableObject message = request.functionToCall(controller, (GameListener) this);
                    SocketServer.broadCastWhatHappened(message, request.getType(), request.getIdGame());
                }
                if (request.getDoItNeedToBeBroadcasted()) {
                    ReturnableObject responseReturnable = request.functionToCall(controller, (GameListener) this);
                    ReturnableObject messageToBroadCast = new ReturnableObject<>();
                    //to align with what happen in the rmi server, i pass a message instead of just a string
                    messageToBroadCast.setResponseReturnable(request.getBroadCastMessage());
                    SocketServer.broadCastWhatHappened(messageToBroadCast, request.getType(), request.getIdGame());
                } else {
                    response = new ServerToClientMsg(request.getType(), false);
                    response.setResponse(request.functionToCall(controller, (GameListener) this));
                    output.writeObject(response);
                    output.flush();
                    output.reset();
                }*/


            }
        } catch (IOException | ClassNotFoundException | InterruptedException | CardNotFoundException |
                 PlaceNotAvailableException | RequirementsNotMetException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ServerToClientMsg msgToBroadCast) throws IOException {
        output.writeObject(msgToBroadCast);
        output.flush();
        output.reset();
    }


    /**
     * @throws RemoteException
     */
    @Override
    public void update(ReturnableObject messageToShow) throws IOException {
        ServerToClientMsg msg = new ServerToClientMsg(TypeServerToClientMsg.RECEIVED_MESSAGE);
        msg.setResponse(messageToShow);
        sendMessage(msg);
    }
}
