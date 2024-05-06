package it.polimi.ingsw.network.rmi.Client;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.*;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.tui.GlobalChatState;
import it.polimi.ingsw.tui.MainMenuState;
import it.polimi.ingsw.tui.PrivateChatState;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView {
    public final VirtualServer server;
    ClientState currentState;
    private String username;
    private final Scanner scan;
    private int idGame;
    private int idClientIntoGame;

    public RMIClient(VirtualServer server) throws RemoteException {
        this.scan = new Scanner(System.in);
        this.server = server;
        currentState = new MainMenuState(this, scan);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public int getIdGame() {
        return idGame;
    }
    public int getIdClientIntoGame() {
        return idClientIntoGame;
    }

    @Override
    public void receiveMessage(Message msg) throws RemoteException {
        if(currentState instanceof GlobalChatState || currentState instanceof PrivateChatState){
            if(msg.getSender().equals(this.username))
                System.out.println("You: " + msg.getContent());
            else
                System.out.println(msg.getSender() + ": " + msg.getContent());
        }
        else
        {
            if(msg.getReceiver() == null)
                System.out.println("You have received a message");
            else
                System.out.println("You have received a from " + msg.getSender());
        }
    }

    @Override
    public boolean checkUsername(String username) throws IOException {
        return server.checkUsername(username);
    }
    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws RemoteException{
        return server.getNotStartedGames();
    }

    public ArrayList<Player> getAllPlayers(int gameId) throws RemoteException{
        return server.getAllPlayers(gameId);
    }
    @Override
    public void joinGame(int input, String username) throws RemoteException, InterruptedException{
        idClientIntoGame=server.joinGame(input, username);
        idGame=input;

    }

    @Override
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws RemoteException {
        return server.getPlayerObjectiveCards(idGame, idClientIntoGame);
    }
    @Override
    public void setObjectiveCard(int idCard) throws RemoteException, CardNotFoundException {
        server.setObjectiveCard(idGame, idClientIntoGame, idCard);
    }
    @Override
    public void createGame(String username, int nPlayers) throws RemoteException, InterruptedException{
        idGame=server.createGame(username, nPlayers);
        idClientIntoGame=0;
    }
    @Override
    public StarterCard getStarterCard() throws RemoteException{
        return server.getStarterCard(idGame, idClientIntoGame);
    }
    @Override
    public void playStarterCard(boolean playedFacedDown)
            throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        server.playStarterCard(idGame, idClientIntoGame, playedFacedDown);
    }
    @Override
    public ObjectiveCard getPlayerObjectiveCard() throws RemoteException {
        return server.getPlayerObjectiveCard(idGame, idClientIntoGame);
    }
    @Override
    public ArrayList<GameCard> getPlayerHand() throws RemoteException {
        return server.getPlayerHand(idGame, idClientIntoGame);
    }
    @Override
    public ObjectiveCard[] getSharedObjectiveCards() throws RemoteException {
        return server.getSharedObjectiveCards(idGame);
    }
    @Override
    public String getNextState() throws RemoteException {
        if(currentState instanceof InitializeStarterCardState){
            if(server.getCurrentPlayer(idGame)==idClientIntoGame)
                return "PlayCardState";
            else
                return "WaitForYourTurnState";
        }else if( currentState instanceof DrawCardState){
            if(server.getIsLastRoundStarted(idGame))
                return "LastRoundState";
            else
                return "WaitForYourTurnState";
        }
        return "Error";
    }
    @Override
    public void playCard(int chosenCard, boolean faceDown, Point chosenPosition)
            throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException{
        server.playCard(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
    }

    public ArrayList<Message> getMessages(String receiver) throws RemoteException{
           return server.getMessages(receiver, this.idGame, this.username);
    }

    public void sendMessage(String receiver, String message) throws RemoteException{
        Message msg = new Message(this.username, receiver, message, this.idGame);
        server.sendMessage(msg);
    }

    @Override
    public ClientState getCurrentState() throws RemoteException {
        return currentState;
    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        this.server.connect(this);
        runStateLoop();
    }
    @Override
    public void drawCard(int input, int inVisible) throws RemoteException, CardNotFoundException{
        server.drawCard(idGame, idClientIntoGame, input, inVisible);
    }
    @Override
    public void waitForYourTurn() throws RemoteException, InterruptedException {
        server.waitForYourTurn(idGame, idClientIntoGame);
    }
    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }

    private void runStateLoop() throws IOException, ClassNotFoundException, InterruptedException {
        boolean correctInput;
        boolean chatState = false;
        int chatStateContator;
        while (true) {
            chatStateContator = 0;
            correctInput = false;
            chatState = false;
            currentState.display();
            currentState.promptForInput();
            int input = 0;
            do{
                if(currentState instanceof PrivateChatState || currentState instanceof GlobalChatState)
                {
                    chatState = true;
                    chatStateContator++;
                }
                else
                {
//TODO why does it give me error?
                    if(chatStateContator > 0)
                    {
                        chatStateContator = 0;
                        currentState.display();
                        currentState.promptForInput();
                    }
                    chatState = false;
                }
            }while(chatState);
            while (!correctInput) {
                try {
                    input = scan.nextInt();
                    correctInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input: Reinsert the value: ");
                } finally {
                    scan.nextLine();
                }
            }
            currentState.inputHandler(input);
        }
    }

}
