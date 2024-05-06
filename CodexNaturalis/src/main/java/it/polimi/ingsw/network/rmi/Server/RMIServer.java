package it.polimi.ingsw.network.rmi.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RMIServer implements VirtualServer {
    final List<VirtualView> clients = new ArrayList<>();

    private final LobbyController lobbyController;

    public RMIServer(LobbyController lobbyController) throws RemoteException {
        super();  // Call the constructor of UnicastRemoteObject
        this.lobbyController = lobbyController;
    }

    @Override
    public synchronized void connect(VirtualView client) throws RemoteException {
        System.err.println("new client connected");
        this.clients.add(client);
    }

    @Override
    public boolean checkUsername(String username) throws RemoteException {
        return lobbyController.checkUsername(username);
    }

    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws RemoteException {
        return lobbyController.getVisibleGames();
    }

    @Override
    public int joinGame(int id, String username) throws RemoteException, InterruptedException {
        return lobbyController.joinGame(id, username);
    }

    @Override
    public int createGame(String username, int nPlayers) throws RemoteException, InterruptedException{
        return lobbyController.createGame(username, nPlayers);
    }

    @Override
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards(int idGame, int idPlayer) throws RemoteException {
        return lobbyController.getObjectiveCards(idGame, idPlayer);
    }

    @Override
    public void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) throws RemoteException, CardNotFoundException {
        lobbyController.setObjectiveCard(idGame, idClientIntoGame, idObjCard);
    }
    @Override
    public StarterCard getStarterCard(int idGame, int idClientIntoGame) throws RemoteException{
        return lobbyController.getStarterCard(idGame, idClientIntoGame);
    }
    @Override
    public void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        lobbyController.playStarterCard(idGame, idClientIntoGame, playedFacedDown);
    }
    @Override
    public ObjectiveCard getPlayerObjectiveCard(int idGame, int idClientIntoGame) throws RemoteException{
        return lobbyController.getObjectiveCard(idGame, idClientIntoGame);
    }
    @Override
    public ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) throws RemoteException{
        return lobbyController.getPlayerHand(idGame, idClientIntoGame);
    }
    @Override
    public ObjectiveCard[] getSharedObjectiveCards(int idGame) throws RemoteException{
        return lobbyController.getSharedObjectiveCards(idGame);
    }
}
