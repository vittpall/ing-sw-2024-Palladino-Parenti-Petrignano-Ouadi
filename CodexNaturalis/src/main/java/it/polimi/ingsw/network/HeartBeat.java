package it.polimi.ingsw.network;

import it.polimi.ingsw.network.remoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.server.RMIServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The HeartBeat class is responsible for maintaining the connection between the server and the client.
 * It periodically sends a "ping" to the client to check if the connection is still active.
 */
public class HeartBeat {
    private ScheduledExecutorService executorService;
    private final RMIServer rmiServer;
    private final VirtualView rmiClient;
    private String rmiClientUsername;
    private Integer gameId;

    /**
     * Constructor for the HeartBeat class.
     *
     * @param rmiClient The client to which the heartbeat will be sent.
     * @param rmiServer The server from which the heartbeat is sent.
     */
    public HeartBeat(VirtualView rmiClient, RMIServer rmiServer) {
        this.rmiClient = rmiClient;
        this.rmiServer = rmiServer;
        runLogic();
    }

    /**
     * This method sets up the logic for sending the heartbeat to the client.
     */
    private void runLogic() {
        this.executorService = Executors.newScheduledThreadPool(1);
        int timeout = 10000;
        this.executorService.scheduleAtFixedRate(() -> {
            try {
                rmiClient.ping();
                System.out.println("Ping sent to client:" + (rmiClientUsername != null ? rmiClientUsername : "unknown"));
            } catch (RemoteException e) {
                System.out.println("Connection lost with " + (rmiClientUsername != null ? rmiClientUsername : "unknown client"));
                handleDisconnection();
            }
        }, 0, timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /**
     * This method sets the username of the client.
     *
     * @param username The username of the client.
     */
    public void setUsername(String username) {
        this.rmiClientUsername = username;
    }

    /**
     * This method sets the game ID.
     *
     * @param gameId The ID of the game.
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    /**
     * This method handles the disconnection of a client.
     * It removes the client from the server and shuts down the executor service.
     */
    private void handleDisconnection() {
        if (rmiClientUsername != null) {
            System.out.println(rmiClientUsername + " is being closed(RMIClient)");
            rmiServer.removeUsername(rmiClientUsername);
            if (gameId != null) {
                System.out.println("Game " + gameId + " is being closed");
                try {
                    rmiServer.closeGame(gameId, rmiClientUsername);
                } catch (RemoteException e) {
                    System.err.println("Error closing game: " + gameId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        rmiServer.removeClient(rmiClient);
        executorService.shutdown();
    }

}
