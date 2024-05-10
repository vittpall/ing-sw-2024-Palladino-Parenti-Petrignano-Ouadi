package it.polimi.ingsw.network.socket.Client;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.ClientToServerMsg.CheckUsernameMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.tui.MainMenuState;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketClient implements VirtualView {

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    ClientState currentState;
    private String username;
    private final ConcurrentMap<Class<? extends ServerToClientMsg>, BlockingQueue<ServerToClientMsg>> responseQueues = new ConcurrentHashMap<>();
    private int idGame;

    public SocketClient(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
        currentState = new MainMenuState(this, new Scanner(System.in));
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public int getIdGame() {
        return 0;
    }

    @Override
    public int getIdClientIntoGame() {
        return 0;
    }

    @Override
    public ArrayList<Player> getAllPlayers(int gameId) throws RemoteException {
        return null;
    }

    @Override
    public void receiveMessage(Message msg) throws RemoteException {
        //TODO
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws RemoteException {
        return null;
    }

    @Override
    public void setObjectiveCard(int idCard) throws RemoteException, CardNotFoundException {
        //TODO
    }

    @Override
    public void createGame(String username, int nPlayers) throws RemoteException, InterruptedException {
        //TODO
    }

    @Override
    public void joinGame(int input, String username) throws RemoteException, InterruptedException {
        //TODO
    }

    @Override
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public ClientState getCurrentState() {
        return null;
    }

    @Override
    public ArrayList<Message> getMessages(String receiver) throws RemoteException {
        return null;
    }

    @Override
    public void sendMessage(String receiver, String input) throws RemoteException {

    }

    @Override
    public HashSet<Point> getAvailablePlaces() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws RemoteException {
        return null;
    }

    @Override
    public void close() throws RemoteException {

    }

    @Override
    public String getWinner() throws RemoteException, InterruptedException {
        return null;
    }

    @Override
    public void closeGame() throws RemoteException {

    }

    @Override
    public String getUsernamePlayerThatStoppedTheGame() throws RemoteException {
        return null;
    }

    @Override
    public HashMap<Point, GameCard> getPlayerDesk() throws RemoteException {
        return null;
    }

    @Override
    public StarterCard getStarterCard() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public void playStarterCard(boolean playedFacedDown) throws RemoteException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException {
        //TODO
    }

    @Override
    public ObjectiveCard getPlayerObjectiveCard() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public ArrayList<GameCard> getPlayerHand() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public ObjectiveCard[] getSharedObjectiveCards() throws RemoteException {
        //TODO
        return new ObjectiveCard[0];
    }

    @Override
    public String getNextState() throws RemoteException {
        return "";
    }

    @Override
    public void playCard(int chosenCard, boolean faceDown, Point chosenPosition) throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {

    }

    @Override
    public void playLastTurn(int chosenCard, boolean faceDown, Point chosenPosition) throws RemoteException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException {

    }

    @Override
    public void drawCard(int input, int inVisible) throws RemoteException, CardNotFoundException {

    }

    @Override
    public void waitForYourTurn() throws RemoteException, InterruptedException {

    }

    @Override
    public ArrayList<TokenColor> getAvailableColors() throws RemoteException {
        //TODO
        return null;
    }

    @Override
    public void setTokenColor(TokenColor tokenColor) throws RemoteException {
        //TODO

    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        runCli();
    }


    private void runCli() throws IOException, ClassNotFoundException, InterruptedException {
        boolean correctInput;
        Scanner scan = new Scanner(System.in);
        while (true) {
            correctInput = false;
            currentState.display();
            currentState.promptForInput();
            int input = 0;
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

    //what I receive from the server
    private void runVirtualServer() throws IOException, ClassNotFoundException {
        try {
            while (true) {
                ServerToClientMsg msg = (ServerToClientMsg) in.readObject();
                Class<? extends ServerToClientMsg> responseType = msg.getClass();
                responseQueues.computeIfAbsent(responseType, k -> new LinkedBlockingQueue<>()).put(msg);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public boolean checkUsername(String username) throws IOException, ClassNotFoundException, InterruptedException {
        CheckUsernameMsg request = new CheckUsernameMsg(username);
        ServerToClientMsg expectedResponse = request.getTypeofResponse();
        out.writeObject(request);
        out.flush();
        out.reset();
        BlockingQueue<ServerToClientMsg> queue = responseQueues.computeIfAbsent(expectedResponse.getClass(), k -> new LinkedBlockingQueue<>());
        ServerToClientMsg response = queue.take();  // This will block until the expected type of response is received

        return Boolean.parseBoolean(response.getResponse());
    }

}
