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
import it.polimi.ingsw.network.socket.ClientToServerMsg.*;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ReceivedMessage;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.tui.*;

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

/**
 * This class represents the client side of a socket connection.
 * It implements the VirtualView interface, which provides methods for interacting with the game model.
 */
public class SocketClient implements VirtualView {

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    ClientState currentState;
    private String username;
    private final ConcurrentMap<Class<? extends ServerToClientMsg>, BlockingQueue<ServerToClientMsg>> responseQueues = new ConcurrentHashMap<>();
    private int idGame;
    private int idClientIntoGame;

    /**
     * Constructor for the SocketClient class.
     * @param in ObjectInputStream for receiving data from the server.
     * @param out ObjectOutputStream for sending data to the server.
     * @param mode The mode of the client, either "GUI" or "TUI".
     */
    public SocketClient(ObjectInputStream in, ObjectOutputStream out, String mode) {
        this.in = in;
        this.out = out;
        switch (mode) {
            case "GUI":
                break;
            case "TUI":
                currentState = new MainMenuState(this, new Scanner(System.in));
                break;
            default:
                throw new IllegalArgumentException("Unsupported mode");
        }
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
        return this.idGame;
    }

    @Override
    public int getIdClientIntoGame() {
        return this.idClientIntoGame;
    }

    @Override
    public ArrayList<Player> getAllPlayers(int gameId) throws IOException, InterruptedException {
        GetAllPlayersMsg request = new GetAllPlayersMsg(gameId);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Player>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void receiveMessage(Message msg) throws RemoteException {
        if (currentState instanceof GlobalChatState || currentState instanceof PrivateChatState) {
            if (msg.getSender().equals(this.username))
                System.out.println("You: " + msg.getContent());
            else
                System.out.println(msg.getSender() + ": " + msg.getContent());
        } else {
            if (msg.getReceiver() == null)
                System.out.println("You have received a message");
            else
                System.out.println("You have received a from " + msg.getSender());
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }


    private ServerToClientMsg sendRequest(ClientToServerMsg request) throws IOException, InterruptedException {
        ServerToClientMsg expectedResponse = request.getTypeofResponse();
        out.writeObject(request);
        out.flush();
        out.reset();
        //wait for the response
        BlockingQueue<ServerToClientMsg> queue = responseQueues.computeIfAbsent(expectedResponse.getClass(), k -> new LinkedBlockingQueue<>());
        return queue.take();  // This will block until the expected type of response is received
    }

    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws IOException, InterruptedException {
        GetNotStartedGamesMsg request = new GetNotStartedGamesMsg();
        ServerToClientMsg response = sendRequest(request);

        return (HashMap<Integer, Game>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void setObjectiveCard(int idCard) throws IOException, CardNotFoundException, InterruptedException {
        SetObjectiveCardMsg request = new SetObjectiveCardMsg(idGame, idClientIntoGame, idCard);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public void createGame(String username, int nPlayers) throws IOException, InterruptedException {
        CreateGameMsg request = new CreateGameMsg(username, nPlayers);
        ServerToClientMsg response = sendRequest(request);
        idClientIntoGame = 0;
        idGame = (int) response.getResponse().getResponseReturnable();
    }

    @Override
    public void joinGame(int input, String username) throws IOException, InterruptedException {
        JoinGameMsg request = new JoinGameMsg(username, input);
        ServerToClientMsg response = sendRequest(request);
        idGame = input;
        idClientIntoGame = (int) response.getResponse().getResponseReturnable();

    }

    @Override
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws IOException, InterruptedException {
        GetPlayerObjectiveCardsMsg request = new GetPlayerObjectiveCardsMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<ObjectiveCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public ClientState getCurrentState() {
        return this.currentState;
    }

    @Override
    public ArrayList<Message> getMessages(String receiver) throws IOException, InterruptedException {
        GetMessageMsg request = new GetMessageMsg(receiver, this.idGame, this.username);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Message>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void sendMessage(String receiver, String message) throws IOException, InterruptedException {
        Message msg = new Message(this.username, receiver, message, this.idGame);
        SendMessageMsg request = new SendMessageMsg(msg);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public HashSet<Point> getAvailablePlaces() throws IOException, InterruptedException {
        GetAvailablePlacesMsg request = new GetAvailablePlacesMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (HashSet<Point>) response.getResponse().getResponseReturnable();
    }

    @Override
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws IOException, InterruptedException {
        GetVisibleCardsDeckMsg request = new GetVisibleCardsDeckMsg(idGame, deck);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void close() throws IOException, InterruptedException {
        ClosedConnectionMsg request = new ClosedConnectionMsg(username);
        ServerToClientMsg response = sendRequest(request);
        response.getResponse();

        System.exit(0);
    }

    @Override
    public String getWinner() throws IOException, InterruptedException {
        GetWinnerMsg request = new GetWinnerMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    @Override
    public void closeGame() throws IOException, InterruptedException {
        CloseGameMsg request = new CloseGameMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public String getUsernamePlayerThatStoppedTheGame() throws IOException, InterruptedException {
        GetUsernamePlayerThatStoppedTheGameMsg request = new GetUsernamePlayerThatStoppedTheGameMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    @Override
    public HashMap<Point, GameCard> getPlayerDesk() throws IOException, InterruptedException {
        GetPlayerDesk request = new GetPlayerDesk(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (HashMap<Point, GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public StarterCard getStarterCard() throws IOException, InterruptedException {
        GetStarterCard request = new GetStarterCard(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (StarterCard) response.getResponse().getResponseReturnable();
    }

    @Override
    public void playStarterCard(boolean playedFacedDown) throws IOException, CardNotFoundException, RequirementsNotMetException, PlaceNotAvailableException, InterruptedException {
        PlayStarterCard request = new PlayStarterCard(idGame, idClientIntoGame, playedFacedDown);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public ObjectiveCard getPlayerObjectiveCard() throws IOException, InterruptedException {
        GetObjectiveCardMsg request = new GetObjectiveCardMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (ObjectiveCard) response.getResponse().getResponseReturnable();
    }

    @Override
    public ArrayList<GameCard> getPlayerHand() throws IOException, InterruptedException {
        GetPlayerHandMsg request = new GetPlayerHandMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public ObjectiveCard[] getSharedObjectiveCards() throws IOException, InterruptedException {
        GetSharedObjectiveCardsMsg request = new GetSharedObjectiveCardsMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (ObjectiveCard[]) response.getResponse().getResponseReturnable();
    }

    @Override
    public String getNextState() throws IOException, InterruptedException {
        if (currentState instanceof InitializeStarterCardState) {
            if (this.getCurrentPlayer(idGame) == idClientIntoGame)
                return "PlayCardState";
            else
                return "WaitForYourTurnState";
        } else if (currentState instanceof DrawCardState) {
            if (this.getIsLastRoundStarted(idGame))
                return "LastRoundState";
            else
                return "WaitForYourTurnState";
        }
        return "Error";
    }

    public boolean getIsLastRoundStarted(int idGame) throws IOException, InterruptedException {
        IsLastRoundStartedMsg request = new IsLastRoundStartedMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (boolean) response.getResponse().getResponseReturnable();
    }

    public int getCurrentPlayer(int idGame) throws IOException, InterruptedException {
        GetCurrentPlayerMsg request = new GetCurrentPlayerMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (int) response.getResponse().getResponseReturnable();
    }

    @Override
    public void playCard(int chosenCard, boolean faceDown, Point chosenPosition) throws IOException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException, InterruptedException {
        PlayCardMsg request = new PlayCardMsg(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public void playLastTurn(int chosenCard, boolean faceDown, Point chosenPosition) throws IOException, PlaceNotAvailableException, RequirementsNotMetException, CardNotFoundException, InterruptedException {
        PlayLastTurnMsg request = new PlayLastTurnMsg(idGame, idClientIntoGame, chosenCard, faceDown, chosenPosition);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public void drawCard(int input, int inVisible) throws IOException, CardNotFoundException, InterruptedException {
        DrawCardMsg request = new DrawCardMsg(idGame, idClientIntoGame, input, inVisible);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public void waitForYourTurn() throws IOException, InterruptedException {
        WaitForYourTurnMsg request = new WaitForYourTurnMsg(idClientIntoGame, idGame);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public ArrayList<TokenColor> getAvailableColors() throws IOException, InterruptedException {
        AvailableColorMsg request = new AvailableColorMsg(username, idGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<TokenColor>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void setTokenColor(TokenColor tokenColor) throws IOException, InterruptedException {
        SelectTokenColorMsg request = new SelectTokenColorMsg(idGame, idClientIntoGame, tokenColor);
        ServerToClientMsg response = sendRequest(request);

    }

    public int getPoints() throws IOException, InterruptedException {
        GetPoint request = new GetPoint(username, idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (int) response.getResponse().getResponseReturnable();
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
                if(msg instanceof ReceivedMessage)
                {
                  //  System.out.println(msg.getResponse().getMessageResponse().getContent());
                    this.receiveMessage((Message) msg.getResponse().getResponseReturnable());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public boolean checkUsername(String username) throws IOException, ClassNotFoundException, InterruptedException {
        CheckUsernameMsg request = new CheckUsernameMsg(username);
        ServerToClientMsg response = sendRequest(request);
        return (boolean) response.getResponse().getResponseReturnable();
    }

}
