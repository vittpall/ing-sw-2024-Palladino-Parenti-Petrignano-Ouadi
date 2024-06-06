package it.polimi.ingsw.network.socket.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.network.HeartBeat;
import it.polimi.ingsw.network.notifications.ServerNotification;
import it.polimi.ingsw.network.socket.ClientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.ClientToServerMsg.HeartBeatMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ClientHandler implements GameListener {
    final SocketServer server;
    final transient ObjectInputStream input;
    final LobbyController controller;
    final transient ObjectOutputStream output;
    final HeartBeat heartBeat;

    public ClientHandler(SocketServer server, ObjectInputStream input, ObjectOutputStream output, LobbyController controller) {
        this.server = server;
        this.input = input;
        this.controller = controller;
        this.output = output;
        this.heartBeat = new HeartBeat(this);
    }

    public void runVirtualView() throws IOException, ClassNotFoundException, InterruptedException, CardNotFoundException, PlaceNotAvailableException, RequirementsNotMetException {
        ClientToServerMsg request;
        ServerToClientMsg response;
        try {
            while ((request = (ClientToServerMsg) input.readObject()) != null) {
                if (request instanceof HeartBeatMsg) {
                    request.functionToCall(null, this);
                    continue;
                }
                response = new ServerToClientMsg(request.getType());
                response.setResponse(request.functionToCall(controller, this));
                output.writeObject(response);
                output.flush();
                output.reset();
            }
        } catch (EOFException e) {
            System.out.println("Client disconnected");
        } catch (IOException | ClassNotFoundException | InterruptedException | CardNotFoundException |
                 PlaceNotAvailableException | RequirementsNotMetException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() throws IOException {
            input.close();
            output.close();
            System.out.println("Client disconnected");
            Thread.currentThread().interrupt();
    }


    public void sendHeartBeat(long timestamp)
    {
        heartBeat.beatFromClient(timestamp);
    }

    public void sendMessage(ServerToClientMsg msgToBroadCast) throws IOException {
        output.writeObject(msgToBroadCast);
        output.flush();
        output.reset();
    }

    /**
     * @param notification the notification to send to the client
     * @throws RemoteException if the client is not reachable
     */
    @Override
    public void update(ServerNotification notification) throws RemoteException {
        try {
            output.writeObject(notification);
            output.flush();
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
