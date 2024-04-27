package it.polimi.ingsw.network.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer implements VirtualServer {
    final List<VirtualView> clients = new ArrayList<>();

    public RMIServer() throws RemoteException {
        super();  // Call the constructor of UnicastRemoteObject
    }

    @Override
    public void connect(VirtualView client) throws RemoteException {
        System.err.println("new client connected");
        this.clients.add(client);
    }

    @Override
    public void reset() throws RemoteException {
        System.err.println("reset request");

    }

    public static void main(String[] args) throws RemoteException {
        String name = "VirtualServer";

        VirtualServer engine = new RMIServer();
        VirtualServer stub =
                (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, stub);
        System.out.println("Server bound");
    }
}
