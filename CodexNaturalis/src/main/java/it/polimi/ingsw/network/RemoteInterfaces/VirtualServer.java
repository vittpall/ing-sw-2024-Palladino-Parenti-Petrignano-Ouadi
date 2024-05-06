package it.polimi.ingsw.network.RemoteInterfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    void connect(VirtualView client) throws RemoteException;


    boolean checkUsername(String username) throws RemoteException;
    
    HashMap<Integer, Game> getNotStartedGames() throws RemoteException;

    int joinGame(int id, String username) throws RemoteException, InterruptedException;

    int createGame(String username, int nPlayers) throws RemoteException, InterruptedException;


    ArrayList<ObjectiveCard> getPlayerObjectiveCards(int idGame, int idPlayer) throws RemoteException;

    void setObjectiveCard(int idGame, int idClientIntoGame, int idObjCard) throws RemoteException, CardNotFoundException;

    StarterCard getStarterCard(int idGame, int idClientIntoGame) throws RemoteException;

    void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException;

    ObjectiveCard getPlayerObjectiveCard(int idGame, int idClientIntoGame) throws RemoteException;

    ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) throws RemoteException;

    ObjectiveCard[] getSharedObjectiveCards(int idGame) throws RemoteException;
}
