package it.polimi.ingsw.network.rmi.Server;

import it.polimi.ingsw.controller.LobbyController;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.GameState;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.observer.GameListener;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.HeartBeat;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.Client.ReturnableObject;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RMIServer implements VirtualServer {
    final HashMap<VirtualView, HeartBeat> clients = new HashMap<>();


    private final LobbyController lobbyController;

    public RMIServer(LobbyController lobbyController) throws RemoteException {
        super();
        this.lobbyController = lobbyController;
    }

    @Override
    public synchronized void connect(VirtualView client) throws RemoteException {
        System.err.println("New client connected");
        clients.put(client, new HeartBeat(client, this));
    }


    @Override
    public boolean checkUsername(String username, GameListener playerListener) throws RemoteException {
        boolean usernameValid = lobbyController.checkUsername(username, playerListener);
        if (usernameValid) {
            VirtualView client = (VirtualView) playerListener;
            HeartBeat hb = clients.get(client);
            if (hb != null) {
                hb.setUsername(username);
            }
        }
        return usernameValid;

    }

    public void removeUsername(String username) {
        lobbyController.removeUsername(username);
    }

    @Override
    public ArrayList<Integer> getNotStartedGames(GameListener lobbyListener) throws RemoteException {
        return lobbyController.getVisibleGames(lobbyListener);
    }

    @Override
    public ArrayList<Player> getAllPlayers(int gameId) throws RemoteException {
        return lobbyController.getAllPlayers(gameId);
    }

    @Override
    public ArrayList<Message> getMessages(String receiver, int gameId, String sender) throws RemoteException {
        return lobbyController.getMessages(receiver, gameId, sender);
    }

    @Override
    public void sendMessage(int idGame, Message msg) throws RemoteException {
        lobbyController.sendMessage(idGame, msg);
        //TODO alert all the clients that a new message has been sent
        //TODO implement an observer pattern
        /*
        if (msg.getReceiver() == null) {
            for (VirtualView client : clients) {
                if (client.getIdGame() == msg.getGameId())
                    client.receiveMessage(msg);
            }

        } else {
            for (VirtualView client : clients)
                if (client.getIdGame() == msg.getGameId())
                    client.receiveMessage(msg);
        }
*/
    }


    @Override
    public int joinGame(int gameId, String username, GameListener playerListener) throws IOException, InterruptedException {
        int playerId = lobbyController.joinGame(gameId, username, playerListener);
        HeartBeat hb = clients.get((VirtualView) playerListener);
        if (hb != null) {
            hb.setGameId(gameId);
        }
        return playerId;
    }

    @Override
    public int createGame(String username, int nPlayers, GameListener playerListener) throws IOException, InterruptedException {
        int createdGameId = lobbyController.createGame(username, nPlayers, playerListener);
        HeartBeat hb = clients.get((VirtualView) playerListener);
        if (hb != null) {
            hb.setGameId(createdGameId);
        }
        return createdGameId;
    }


    @Override
    public boolean isGameStarted(int idGame) throws RemoteException {
        return !lobbyController.getCurrentGameState(idGame).equals(GameState.WAITING_FOR_PLAYERS.toString());
    }

    @Override
    public PlayerState getCurrentPlayerState(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getCurrentPlayerState(idGame, idClientIntoGame);
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
    public StarterCard getStarterCard(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getStarterCard(idGame, idClientIntoGame);
    }

    @Override
    public void playStarterCard(int idGame, int idClientIntoGame, boolean playedFacedDown, GameListener playerListener)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        lobbyController.playStarterCard(idGame, idClientIntoGame, playedFacedDown, playerListener);
    }

    @Override
    public ObjectiveCard getPlayerObjectiveCard(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getObjectiveCard(idGame, idClientIntoGame);
    }

    @Override
    public ArrayList<GameCard> getPlayerHand(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getPlayerHand(idGame, idClientIntoGame);
    }

    @Override
    public ObjectiveCard[] getSharedObjectiveCards(int idGame) throws RemoteException {
        return lobbyController.getSharedObjectiveCards(idGame);
    }


    @Override
    public void playCard(int idGame, int idClientIntoGame, int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {
        lobbyController.playCard(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    @Override
    public void drawCard(int idGame, int idClientIntoGame, int deckToChoose, int inVisible) throws IOException, CardNotFoundException, InterruptedException {
        lobbyController.drawCard(idGame, deckToChoose, inVisible);
        String content;
        content = "\n----------------------------------\n" +
                "Player " + lobbyController.getPlayers(idGame).get(idClientIntoGame).getUsername() + " drew a card\n" +
                "Now is " + lobbyController.getPlayers(idGame).get(lobbyController.getCurrentPlayer(idGame)).getUsername() + " turn.";
        if (lobbyController.getCurrentGameState(idGame).equals(GameState.FINISHING_ROUND_BEFORE_LAST.toString()) ||
                lobbyController.getCurrentGameState(idGame).equals(GameState.LAST_ROUND.toString())) {
            content = "\nThe game is ending. Player " + lobbyController.getUsernamePlayerThatStoppedTheGame(idGame) +
                    " has reached 20 points or more\n " + content;
        }
        ReturnableObject response = new ReturnableObject();
        response.setResponseReturnable(content);
        /*  this.broadcastWhatHappened(idGame, response);*/
    }


    @Override
    public boolean getIsLastRoundStarted(int idGame) throws RemoteException {
        return lobbyController.getIsLastRoundStarted(idGame);
    }

    @Override
    public HashSet<Point> getAvailablePlaces(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getAvailablePlaces(idGame, idClientIntoGame);
    }

    @Override
    public ArrayList<GameCard> getVisibleCardsDeck(int idGame, int deck) throws RemoteException {
        return lobbyController.getVisibleCardsDeck(idGame, deck);
    }

    @Override
    public Card getLastCardOfUsableCards(int idGame, int deck) throws RemoteException {
        return lobbyController.getLastCardOfUsableCards(idGame, deck);
    }

    @Override
    public String getUsernamePlayerThatStoppedTheGame(int idGame) throws RemoteException {
        return lobbyController.getUsernamePlayerThatStoppedTheGame(idGame);
    }


    @Override
    public HashMap<Point, GameCard> getPlayerDesk(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getPlayerDesk(idGame, idClientIntoGame);
    }


    @Override
    public ArrayList<TokenColor> getAvailableColors(int idGame, GameListener playerListener) throws RemoteException {
        return lobbyController.getAvailableColors(idGame, playerListener);
    }

    @Override
    public void setTokenColor(int idGame, int idClientIntoGame, TokenColor tokenColor, GameListener playerListener) throws IOException {
        lobbyController.setTokenColor(idGame, idClientIntoGame, tokenColor, playerListener);
    }

    public int getPoints(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getPoints(idGame, idClientIntoGame);
    }

    @Override
    public String getWinner(int idGame) throws RemoteException {
        return lobbyController.getWinner(idGame);
    }

    @Override
    public void closeGame(int idGame, String userThatLeft) throws IOException {
        lobbyController.closeGame(idGame, userThatLeft);
    }

    @Override
    public int getnPlayer(int idGame) throws RemoteException {
        return lobbyController.getnPlayer(idGame);
    }

    @Override
    public ArrayList<Player> getPlayers(int idGame) throws RemoteException {
        return lobbyController.getPlayers(idGame);
    }

    @Override
    public String getServerCurrentState(int idGame, int idClientIntoGame) throws RemoteException {
        return lobbyController.getCurrentState(idGame, idClientIntoGame);
    }

    @Override
    public boolean checkState(int idGame, int idClientIntoGame, RequestedActions requestedActions) throws RemoteException {
        return lobbyController.checkState(idGame, idClientIntoGame, requestedActions);
    }

    public synchronized void removeClient(VirtualView client) {
        if (clients.remove(client) != null) {
            System.out.println("Client successfully removed.");
        } else {
            System.err.println("Client not found, cannot remove.");
        }
    }


}
