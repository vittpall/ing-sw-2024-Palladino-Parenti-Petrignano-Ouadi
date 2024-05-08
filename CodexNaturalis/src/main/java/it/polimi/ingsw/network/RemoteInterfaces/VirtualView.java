package it.polimi.ingsw.network.RemoteInterfaces;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.tui.ClientState;

import java.awt.*;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface VirtualView extends Remote {

    void setUsername(String username) throws RemoteException;

    HashMap<Integer, Game> getNotStartedGames() throws RemoteException;

    void setObjectiveCard(int idCard) throws RemoteException, CardNotFoundException;

    void createGame(String username, int nPlayers) throws RemoteException, InterruptedException;

    void setCurrentState(ClientState state) throws RemoteException;

    String getUsername() throws RemoteException;

    int getIdGame() throws RemoteException;

    int getIdClientIntoGame() throws RemoteException;

    ArrayList<Player> getAllPlayers(int gameId) throws RemoteException;

    void receiveMessage(Message msg) throws RemoteException;

    public boolean checkUsername(String username) throws IOException, RemoteException, ClassNotFoundException, InterruptedException;

    void joinGame(int input, String username) throws RemoteException, InterruptedException;

    ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws RemoteException;

    StarterCard getStarterCard() throws RemoteException;

    void playStarterCard(boolean playedFacedDown)throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException;

    ObjectiveCard getPlayerObjectiveCard() throws RemoteException;

    ArrayList<GameCard> getPlayerHand() throws RemoteException;

    ObjectiveCard[] getSharedObjectiveCards() throws RemoteException;

    String getNextState() throws RemoteException;

    void playCard(int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException;

    void drawCard(int input, int inVisible) throws RemoteException, CardNotFoundException;

    void waitForYourTurn() throws RemoteException, InterruptedException;

    ClientState getCurrentState() throws RemoteException;

    ArrayList<Message> getMessages(String receiver) throws RemoteException;

    void sendMessage(String receiver, String input) throws RemoteException;

    HashSet<Point> getAvailablePlaces()throws RemoteException;

    ArrayList<GameCard> getVisibleCardsDeck(int deck) throws RemoteException;

    void close() throws RemoteException;

}
