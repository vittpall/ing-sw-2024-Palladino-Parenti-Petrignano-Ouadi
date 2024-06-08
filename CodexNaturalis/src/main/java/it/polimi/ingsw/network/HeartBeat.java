package it.polimi.ingsw.network;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;
import it.polimi.ingsw.network.rmi.Server.RMIServer;
import it.polimi.ingsw.network.socket.Server.ClientHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HeartBeat {
    private long lastHeartBeat;
    private ScheduledExecutorService executorService;
    private ClientHandler socketClient;
    private RMIServer rmiServer;
    private VirtualView rmiClient;
    private final int timeout = 1000;
    private String rmiClientUsername;
    private Integer gameId;

    public HeartBeat(VirtualView rmiClient, RMIServer rmiServer) throws RemoteException {
        this.lastHeartBeat = System.currentTimeMillis();
        this.rmiClient = rmiClient;
        this.rmiServer = rmiServer;
        gameId = rmiClient.getIdGame();
        runLogic();
    }

    public void runLogic(){
        this.executorService = Executors.newScheduledThreadPool(1);
        this.executorService.scheduleAtFixedRate(() -> {
            try {
                rmiClient.ping();
                //TODO change these ways to obtain the username and gameId
                this.setUsernameClient();
                this.setGameId();
                System.out.println("Ping sent:"+rmiClientUsername);
            } catch (RemoteException e) {
                try {
                    System.out.println(rmiClientUsername + " is being closed");
                    rmiServer.removeUsername(rmiClientUsername);
                    System.out.println(gameId);
                    if(gameId != null)
                    {
                        System.out.println("Game is being closed");
                        rmiServer.closeGame(gameId);
                    }
                } catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
                executorService.shutdown();
            }
       /*     if (System.currentTimeMillis() - lastHeartBeat > 2000) {
                    if (socketClient != null) {
                        try {
                            socketClient.closeClient();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Client is being closed");
                        rmiServer.removeUsername(rmiClientUsername);
                        try {
                            if(gameId != null)
                            {
                                System.out.println("Game is being closed");
                                rmiServer.closeGame(gameId);
                            }

                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        //TODO to move the others client inside the mainMenu state
                    }
                    executorService.shutdown();
                    System.out.println("Client closed");
            }*/
        }, 0, timeout, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public void setUsernameClient() throws RemoteException {
        //this method is used to set the username of the client
        this.rmiClientUsername = rmiClient.getUsername();
    }

    public void setGameId() throws RemoteException {
        //this method is used to set the gameId of the client
        this.gameId = this.rmiClient.getIdGame();
    }

    public void beatFromClient(long lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }
}
