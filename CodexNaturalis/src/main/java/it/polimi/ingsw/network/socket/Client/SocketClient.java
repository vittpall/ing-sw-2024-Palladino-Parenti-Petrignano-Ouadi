package it.polimi.ingsw.network.socket.Client;

import it.polimi.ingsw.gui.MainMenuStateGUI;
import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.chat.Message;
import it.polimi.ingsw.model.enumeration.RequestedActions;
import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.model.enumeration.TypeServerToClientMsg;
import it.polimi.ingsw.model.observer.Observer;
import it.polimi.ingsw.model.strategyPatternObjective.ObjectiveCard;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.ClientToServerMsg.*;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.tui.*;
import javafx.stage.Stage;

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
public class SocketClient implements VirtualView, Observer {

    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    ClientState currentState;
    private String username;
    private final ConcurrentMap<TypeServerToClientMsg, BlockingQueue<ServerToClientMsg>> responseQueues = new ConcurrentHashMap<>();
    private int idGame;
    private int idClientIntoGame;
    private Scanner scan;
    private final boolean isGUIMode;

    /**
     * Constructor for the SocketClient class.
     *
     * @param in   ObjectInputStream for receiving data from the server.
     * @param out  ObjectOutputStream for sending data to the server.
     * @param mode The mode of the client, either "GUI" or "TUI".
     */
    public SocketClient(ObjectInputStream in, ObjectOutputStream out, String mode, Stage stage) {
        this.in = in;
        this.out = out;
        switch (mode) {
            case "GUI":
                isGUIMode = true;
                currentState = new MainMenuStateGUI(stage, this);
                break;
            case "TUI":
                isGUIMode = false;
                this.scan = new Scanner(System.in);
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
    @SuppressWarnings("unchecked")
    public ArrayList<Player> getAllPlayers() throws IOException, InterruptedException {
        GetAllPlayersMsg request = new GetAllPlayersMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Player>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void receiveMessage(Message msg) throws RemoteException {
        if (currentState instanceof GlobalChatState || currentState instanceof PrivateChatState) {
            if (msg.getSender().equals(this.username)) System.out.println("You: " + msg.getContent());
            else System.out.println(msg.getSender() + ": " + msg.getContent());
        } else {
            if (msg.getReceiver() == null) System.out.println("You have received a message");
            else System.out.println("You have received a from " + msg.getSender());
        }
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }


    private ServerToClientMsg sendRequest(ClientToServerMsg request) throws IOException, InterruptedException {
        TypeServerToClientMsg expectedResponse = request.getType();
        System.out.println("Sending request: " + request.getType() + " to the server");
        out.writeObject(request);
        out.flush();
        out.reset();
        //wait for the response
        BlockingQueue<ServerToClientMsg> queue = responseQueues.computeIfAbsent(expectedResponse, k -> new LinkedBlockingQueue<>());
        return queue.take();  // This will block until the expected type of response is received
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Integer> getNotStartedGames() throws IOException, InterruptedException {
        GetNotStartedGamesMsg request = new GetNotStartedGamesMsg();
        ServerToClientMsg response = sendRequest(request);
        System.out.println("Received response: " + response.getResponse().getResponseReturnable());
        return (ArrayList<Integer>) response.getResponse().getResponseReturnable();
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public HashSet<Point> getAvailablePlaces() throws IOException, InterruptedException {
        GetAvailablePlacesMsg request = new GetAvailablePlacesMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (HashSet<Point>) response.getResponse().getResponseReturnable();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<GameCard> getVisibleCardsDeck(int deck) throws IOException, InterruptedException {
        GetVisibleCardsDeckMsg request = new GetVisibleCardsDeckMsg(idGame, deck);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<GameCard>) response.getResponse().getResponseReturnable();
    }

    @Override
    public GameCard getLastFromUsableCards(int deck) throws RemoteException {
        return null;
    }

    @Override
    public void close() throws IOException, InterruptedException {
        ClosedConnectionMsg request = new ClosedConnectionMsg(username, idGame);
        System.out.println("Closing connection!!!");
        ServerToClientMsg response = sendRequest(request);
        response.getResponse();

        System.exit(0);
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void removeUsername() throws IOException, InterruptedException {

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

    /**
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void notifyYourTurn() throws IOException, InterruptedException {
        //TODO
    }

    /**
     * @param idGame
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public int getnPlayer(int idGame) throws IOException, InterruptedException {
        GetNPlayer request = new GetNPlayer(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (int) response.getResponse().getResponseReturnable();
    }

    /**
     * @param idGame
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Player> getPlayers(int idGame) throws IOException, InterruptedException {
        GetAllPlayersMsg request = new GetAllPlayersMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (ArrayList<Player>) response.getResponse().getResponseReturnable();
    }

    @Override
    public void receiveNotification(Message msg) throws IOException, InterruptedException {
        String message = msg.getContent();
        System.out.println(message);
    }

    @Override
    public String getUsernamePlayerThatStoppedTheGame() throws IOException, InterruptedException {
        GetUsernamePlayerThatStoppedTheGameMsg request = new GetUsernamePlayerThatStoppedTheGameMsg(idGame);
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
            if (this.getCurrentPlayer(idGame) == idClientIntoGame) return "PlayCardState";
            else return "WaitForYourTurnState";
        } else if (currentState instanceof DrawCardState) {
            if (this.getIsLastRoundStarted(idGame)) return "LastRoundState";
            else return "WaitForYourTurnState";
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
        DrawCardMsg request = new DrawCardMsg(idGame, idClientIntoGame, input, inVisible, this.username + " has drawn a card");
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    public void waitForYourTurn() throws IOException, InterruptedException {
        WaitForYourTurnMsg request = new WaitForYourTurnMsg(idClientIntoGame, idGame);
        ServerToClientMsg response = sendRequest(request);
    }

    @Override
    @SuppressWarnings("unchecked")
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
        GetPoints request = new GetPoints(username, idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (int) response.getResponse().getResponseReturnable();
    }

    @Override
    public void run() throws IOException, ClassNotFoundException, InterruptedException {

        if (isGUIMode) {
            showState();
        } else {
            new Thread(() -> {
                try {
                    runVirtualServer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
            inputHandler();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void inputHandler() throws IOException, ClassNotFoundException, InterruptedException {
       /* boolean correctInput;
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
        */
        Scanner scan = new Scanner(System.in);
        boolean correctInput = false;
        String input = "";
        do {
            showState();
            correctInput = false;
            while (!correctInput) {
                try {
                    System.out.print("Type your command or 'exit' to quit: ");
                    input = scan.nextLine().trim().toLowerCase();
                    correctInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input: Reinsert the value: ");
                }
            }

            if (!handleCommonInput(input)) {
                try {
                    currentState.inputHandler(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                }
            }
        } while (!(currentState instanceof PlayCardState));
        while (true) {
            System.out.println("You are in the game: " + idGame + " as player: " + idClientIntoGame);
            display();
            correctInput = false;
            while (!correctInput) {
                try {
                    System.out.print("Type your command or 'exit' to quit: ");
                    input = scan.nextLine().trim().toLowerCase();
                    correctInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input: Reinsert the value: ");
                }
            }

            if (!handleCommonInput(input)) {
                try {
                    boolean checkState = gameLogicInputHandler(Integer.parseInt(input));
                    if (checkState) {
                        showState();
                        boolean correctInput2 = false;
                        while (!correctInput2) {
                            try {
                                System.out.print("Type your command or 'exit' to quit: ");
                                input = scan.nextLine().trim().toLowerCase();
                                correctInput2 = true;
                            } catch (InputMismatchException e) {
                                System.out.println("\nInvalid input: Reinsert the value: ");
                            }
                        }
                        if (!handleCommonInput(input)) {
                            try {
                                //TODO: vedere se cambiare qui il current state oppure nel gameLogicInputHandler
                                currentState.inputHandler(Integer.parseInt(input));
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input: Please enter a number.");
                            }
                        }
                    } else {
                        System.out.println("The input was not valid. You can " + getCurrentState(idGame, idClientIntoGame));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: Please enter a number.");
                }
            }
        }

    }


    private boolean handleCommonInput(String input) {
        if ("exit".equals(input)) {
            try {
                System.out.println("Exiting game...");
                close();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    private boolean gameLogicInputHandler(int i) {
        try {
            boolean checkState = false;
            switch (i) {
                //probabilmente è meglio mandare l'input al server e poi è il GameController che gestisce lo stato di richiesta
                //per questo ho commentato la chiamata al metodo checkState
                case 1:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.DRAW);
                    if (checkState) currentState = new DrawCardState(this, scan);
                    return checkState;
                case 2:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.PLAY_CARD);
                    if (checkState) currentState = new PlayCardState(this, scan);
                    return checkState;
                case 3:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.SHOW_DESKS);
                    //  if (checkState) currentState = new ShowDeskState(this, scan);
                    return checkState;
                case 4:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.SHOW_OBJ_CARDS);
                    if (checkState) currentState = new ShowObjectiveCardsState(this, scan);
                    return checkState;
                case 5:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.SHOW_POINTS);
                    if (checkState) currentState = new ShowPointsState(this, scan);
                    return checkState;
                case 6:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.CHAT);
                    if (checkState) currentState = new ChatState(this, scan);
                    return checkState;
                case 7:
                    checkState = checkState(idGame, idClientIntoGame, RequestedActions.SHOW_WINNER);
                    if (checkState) currentState = new GetWinnerState(this, scan);
                    return checkState;
                default:
                    return false;
            }
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    private void display() {
        System.out.println("1- Draw a card");
        System.out.println("2- Play a card");
        System.out.println("3- Show your desk and others' desks");
        System.out.println("4- Show the shared objective cards and your objective card");
        System.out.println("5- Show players' points");
        System.out.println("6- Chat");
        System.out.println("8- Set your token color");
        System.out.println("9- Set your objective card");
        System.out.println("10- Set your starter card");
    }

    @Override
    public void showState() {
        currentState.display();
        currentState.promptForInput();
    }

    //what I receive from the server
    @SuppressWarnings("InfiniteLoopStatement")
    private void runVirtualServer() throws IOException, ClassNotFoundException {
        try {
            while (true) {
                ServerToClientMsg msg = (ServerToClientMsg) in.readObject();
                TypeServerToClientMsg responseType = msg.getType();
                responseQueues.computeIfAbsent(responseType, k -> new LinkedBlockingQueue<>()).put(msg);
                if (responseType == TypeServerToClientMsg.RECEIVED_MESSAGE) {
                    //  System.out.println(msg.getResponse().getMessageResponse().getContent());
                    this.receiveMessage((Message) msg.getResponse().getResponseReturnable());
                }
                if (msg.doItNeedToBeBroadCasted() && (msg.getIdGame() == idGame || msg.getIdGame() == -1)) {
                    this.receiveNotification((Message) msg.getResponse().getResponseReturnable());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getCurrentState(int idGame, int idClientIntoGame) throws IOException, InterruptedException {
        GetCurrentStateMsg request = new GetCurrentStateMsg(idGame, idClientIntoGame);
        ServerToClientMsg response = sendRequest(request);
        return (String) response.getResponse().getResponseReturnable();
    }

    public boolean checkState(int idGame, int idClientIntoGame, RequestedActions requestedActions) throws IOException, ClassNotFoundException, InterruptedException {
        CheckStateMsg request = new CheckStateMsg(idGame, idClientIntoGame, requestedActions);
        ServerToClientMsg response = sendRequest(request);
        return (boolean) response.getResponse().getResponseReturnable();
    }


    public boolean checkUsername(String username) throws IOException, InterruptedException {
        CheckUsernameMsg request = new CheckUsernameMsg(username);
        ServerToClientMsg response = sendRequest(request);
        return (boolean) response.getResponse().getResponseReturnable();
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
