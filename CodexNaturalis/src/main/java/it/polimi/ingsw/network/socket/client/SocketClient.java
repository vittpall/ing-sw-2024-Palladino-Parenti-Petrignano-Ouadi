package it.polimi.ingsw.network.socket.client;

import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.*;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.notifications.ServerNotification;
import it.polimi.ingsw.network.socket.clientToServerMsg.*;
import it.polimi.ingsw.network.socket.serverToClientMsg.ServerToClientMsg;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class represents the client side of a socket connection.
 * It implements the VirtualView interface, which provides methods for interacting with the game model.
 */
public class SocketClient extends BaseClient {

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final ConcurrentMap<TypeServerToClientMsg, BlockingQueue<ServerToClientMsg>> responseQueues = new ConcurrentHashMap<>();
    private int idClientIntoGame;


    /**
     * Constructor for the SocketClient class.
     *
     * @param in   ObjectInputStream for receiving data from the server.
     * @param out  ObjectOutputStream for sending data to the server.
     * @param mode The mode of the client, either "GUI" or "TUI".
     */
    public SocketClient(ObjectInputStream in, ObjectOutputStream out, String mode, Stage stage) {
        super(mode, stage);
        this.in = in;
        this.out = out;
    }


    @Override
    public int getIdClientIntoGame() {
        return this.idClientIntoGame;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Player> getAllPlayers() throws IOException, InterruptedException {
        GetAllPlayersMsg request = new GetAllPlayersMsg(getIdGame());
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Player>) response.getResponse().getResponseReturnable();
    }


    private ServerToClientMsg sendRequest(ClientToServerMsg request) throws InterruptedException {
        TypeServerToClientMsg expectedResponse = request.getType();
        try {
            out.writeObject(request);
            out.flush();
            out.reset();
        } catch (IOException e) {
            System.out.println("\nThe server has crashed, thanks for playing");
            System.exit(0);
        }
        //wait for the response
        BlockingQueue<ServerToClientMsg> queue = responseQueues.computeIfAbsent(expectedResponse, k -> new LinkedBlockingQueue<>());
        return queue.take();  // This will block until the expected type of response is received
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Integer> getNotStartedGames() throws InterruptedException {
        GetNotStartedGamesMsg request = new GetNotStartedGamesMsg();
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Integer>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void setObjectiveCard(int idCard) throws IOException, InterruptedException {
        SetObjectiveCardMsg request = new SetObjectiveCardMsg(getIdGame(), idClientIntoGame, idCard);
        sendRequest(request);
    }

    @Override
    public void createGame(String username, int nPlayers) throws InterruptedException {
        CreateGameMsg request = new CreateGameMsg(username, nPlayers);
        ServerToClientMsg response = sendRequest(request);
        idClientIntoGame = 0;
        setIdGame((Integer) response.getResponse().getResponseReturnable());
    }

    @Override
    public void joinGame(int input, String username) throws IOException, InterruptedException {
        JoinGameMsg request = new JoinGameMsg(username, input);
        ServerToClientMsg response = sendRequest(request);
        setIdGame(input);
        idClientIntoGame = (int) response.getResponse().getResponseReturnable();

    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<ObjectiveCard> getPlayerObjectiveCards() throws InterruptedException {
        GetPlayerObjectiveCardsMsg request = new GetPlayerObjectiveCardsMsg(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<ObjectiveCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Message> getMessages(String receiver) throws IOException, InterruptedException {
        GetMessageMsg request = new GetMessageMsg(receiver, getIdGame(), getUsername());
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Message>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void sendMessage(String receiver, String message) throws IOException, InterruptedException {
        Message msg = new Message(getUsername(), receiver, message, getIdGame(), userTokenColor);
        SendMessageMsg request = new SendMessageMsg(msg);
        sendRequest(request);
    }

    @Override
    @SuppressWarnings("unchecked")
    public HashSet<Point> getAvailablePlaces() throws IOException, InterruptedException {
        GetAvailablePlacesMsg request = new GetAvailablePlacesMsg(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (HashSet<Point>) response.getResponse().getResponseReturnable();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws IOException, InterruptedException {
        GetVisibleCardsDeckMsg request = new GetVisibleCardsDeckMsg(getIdGame(), deck);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void close() {
        try {
            returnToLobby();
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Something went wrong, restart the game...");
        }
        System.exit(0);
    }

    @Override
    public void returnToLobby() throws InterruptedException {
        if (getIdGame() != null) {
            ClosedConnectionMsg request = new ClosedConnectionMsg(getUsername(), getIdGame());
            sendRequest(request);
        }
        setIdGameNull();
    }

    @Override
    public GameCard getLastFromUsableCards(int deck) throws InterruptedException {
        GetLastFromUsableCards request = new GetLastFromUsableCards(getIdGame(), deck);
        ServerToClientMsg response = sendRequest(request);
        return (GameCard) response.getResponse().getResponseReturnable();
    }


    @Override
    public String getWinner() throws InterruptedException {
        GetWinnerMsg request = new GetWinnerMsg(getIdGame());
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    @Override
    public void closeGame() throws IOException, InterruptedException {
        CloseGameMsg request = new CloseGameMsg(getIdGame());
        sendRequest(request);
    }


    @Override
    public int getnPlayer(int idGame) throws IOException, InterruptedException {
        GetNPlayer request = new GetNPlayer(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (int) response.getResponse().getResponseReturnable();
    }


    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Player> getPlayers(int idGame) throws IOException, InterruptedException {
        GetAllPlayersMsg request = new GetAllPlayersMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Player>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void ping() throws RemoteException {

    }

    @Override
    public HashMap<Point, GameCard> getPlayerDesk() throws IOException, InterruptedException {
        GetPlayerDesk request = new GetPlayerDesk(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (HashMap<Point, GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public StarterCard getStarterCard() throws IOException, InterruptedException {
        GetStarterCard request = new GetStarterCard(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (StarterCard) response.getResponse().getResponseReturnable();
    }

    @Override
    public void playStarterCard(boolean playedFacedDown) throws IOException, RequirementsNotMetException, PlaceNotAvailableException, InterruptedException {
        PlayStarterCard request = new PlayStarterCard(getIdGame(), idClientIntoGame, playedFacedDown);
        ServerToClientMsg response = sendRequest(request);
        if (!response.getResponse().isSuccess()) {
            if (response.getResponse().getErrorCode() == ErrorCodes.REQUIREMENTS_NOT_MET) {
                throw new RequirementsNotMetException(response.getResponse().getErrorMessage());
            }
        }
    }

    @Override
    public ObjectiveCard getPlayerObjectiveCard() throws InterruptedException {
        GetObjectiveCardMsg request = new GetObjectiveCardMsg(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (ObjectiveCard) response.getResponse().getResponseReturnable();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<GameCard> getPlayerHand() throws IOException, InterruptedException {
        GetPlayerHandMsg request = new GetPlayerHandMsg(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public ObjectiveCard[] getSharedObjectiveCards() throws IOException, InterruptedException {
        GetSharedObjectiveCardsMsg request = new GetSharedObjectiveCardsMsg(getIdGame());
        ServerToClientMsg response = sendRequest(request);
        return (ObjectiveCard[]) response.getResponse().getResponseReturnable();
    }

    @Override
    public int playCard(int chosenCard, boolean faceDown, Point chosenPosition) throws IOException, PlaceNotAvailableException, RequirementsNotMetException, InterruptedException {
        PlayCardMsg request = new PlayCardMsg(getIdGame(), idClientIntoGame, chosenCard, faceDown, chosenPosition);
        ServerToClientMsg response = sendRequest(request);
        if (!response.getResponse().isSuccess()) {
            if (response.getResponse().getErrorCode() == ErrorCodes.REQUIREMENTS_NOT_MET) {
                throw new RequirementsNotMetException(response.getResponse().getErrorMessage());
            }
        }
        return (int) response.getResponse().getResponseReturnable();
    }


    @Override
    public void drawCard(int input, int inVisible) throws IOException, InterruptedException {
        DrawCardMsg request = new DrawCardMsg(getIdGame(), input, inVisible, getUsername() + " has drawn a card");
        sendRequest(request);
    }


    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<TokenColor> getAvailableColors() throws IOException, InterruptedException {
        AvailableColorMsg request = new AvailableColorMsg(getUsername(), getIdGame());
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<TokenColor>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void setTokenColor(TokenColor tokenColor) throws IOException, InterruptedException {
        SelectTokenColorMsg request = new SelectTokenColorMsg(getIdGame(), idClientIntoGame, tokenColor);
        ServerToClientMsg response = sendRequest(request);
        setUserTokenColor((TokenColor) response.getResponse().getResponseReturnable());

    }

    @Override
    public boolean isGameStarted() throws InterruptedException {
        GetCurrentGameStateMsg request = new GetCurrentGameStateMsg(getIdGame());
        ServerToClientMsg response = sendRequest(request);
        return !(response.getResponse().getResponseReturnable().equals(GameState.WAITING_FOR_PLAYERS.toString()));
    }

    @Override
    public String getGameState() throws InterruptedException {
        GetCurrentGameStateMsg request = new GetCurrentGameStateMsg(getIdGame());
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    public int getPoints() throws IOException, InterruptedException {
        GetPoints request = new GetPoints(getUsername(), getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (int) response.getResponse().getResponseReturnable();
    }

    @Override
    public void run() throws IOException {

        new Thread(this::runVirtualServer).start();

        if (isGUIMode()) {
            getClientCurrentState().display();
        } else {
            inputHandler();
        }
    }


    private void runVirtualServer() {
        try {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof ServerNotification notification) {
                    update(notification);
                } else if (obj instanceof ServerToClientMsg msg) {
                    TypeServerToClientMsg responseType = msg.getType();
                    responseQueues.computeIfAbsent(responseType, k -> new LinkedBlockingQueue<>()).put(msg);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nThe server has crashed, thanks for playing");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getServerCurrentState() throws InterruptedException {
        GetCurrentStateMsg request = new GetCurrentStateMsg(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    @Override
    public PlayerState getCurrentPlayerState() throws IOException, InterruptedException {
        GetCurrentPlayerState request = new GetCurrentPlayerState(getIdGame(), idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (PlayerState) response.getResponse().getResponseReturnable();
    }


    public boolean checkState(RequestedActions requestedActions) throws InterruptedException {
        CheckStateMsg request = new CheckStateMsg(getIdGame(), idClientIntoGame, requestedActions);
        ServerToClientMsg response = sendRequest(request);
        return (boolean) response.getResponse().getResponseReturnable();
    }


    public boolean checkUsername(String username) throws InterruptedException {
        CheckUsernameMsg request = new CheckUsernameMsg(username);
        ServerToClientMsg response = sendRequest(request);
        return (boolean) response.getResponse().getResponseReturnable();
    }

}
