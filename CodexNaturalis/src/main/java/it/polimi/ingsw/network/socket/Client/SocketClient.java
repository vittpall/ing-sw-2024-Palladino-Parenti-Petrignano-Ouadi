package it.polimi.ingsw.network.socket.Client;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.ClientToServerMsg.CheckUsernameMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.tui.MainMenuState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
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

    public void setUsername(String username) {
        this.username = username;
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
