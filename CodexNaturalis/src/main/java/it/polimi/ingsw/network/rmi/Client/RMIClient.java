package it.polimi.ingsw.network.rmi.Client;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.tui.MainMenuState;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView {
    public final VirtualServer server;
    ClientState currentState;
    private String username;
    private final Scanner scan;
    private int idGame;

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

    @Override
    public boolean checkUsername(String username) throws IOException {
        return server.checkUsername(username);
    }
    @Override
    public HashMap<Integer, Game> getNotStartedGames() throws RemoteException{
        return server.getNotStartedGames();
    }
    @Override
    public void joinGame(int input, String username) throws RemoteException, InterruptedException{
        server.joinGame(input, username);
    }
    @Override
    public void createGame(String username, int nPlayers) throws RemoteException, InterruptedException{
        server.createGame(username, nPlayers);
    }

    public void run() throws IOException, ClassNotFoundException, InterruptedException {
        this.server.connect(this);
        runStateLoop();
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }

    private void runStateLoop() throws IOException, ClassNotFoundException, InterruptedException {
        boolean correctInput;
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

}
