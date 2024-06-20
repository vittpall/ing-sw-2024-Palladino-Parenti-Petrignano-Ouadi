package it.polimi.ingsw.network;

import it.polimi.ingsw.network.remoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.server.RMIServer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HeartBeat {
    private ScheduledExecutorService executorService;
    private final RMIServer rmiServer;
    private final VirtualView rmiClient;
    private String rmiClientUsername;
    private Integer gameId;

    public HeartBeat(VirtualView rmiClient, RMIServer rmiServer) {
        this.rmiClient = rmiClient;
        this.rmiServer = rmiServer;
        runLogic();
    }

    private void runLogic() {
        this.executorService = Executors.newScheduledThreadPool(1);
        int timeout = 1000;
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


    public void setUsername(String username) {
        this.rmiClientUsername = username;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

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
