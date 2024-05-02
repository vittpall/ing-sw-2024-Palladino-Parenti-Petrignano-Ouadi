package it.polimi.ingsw.network.socket.Client;

import it.polimi.ingsw.network.RemoteInterfaces.VirtualServer;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.socket.ClientToServerMsg.CheckUsernameMsg;
import it.polimi.ingsw.network.socket.ClientToServerMsg.ClientToServerMsg;
import it.polimi.ingsw.network.socket.ServerToClientMsg.ServerToClientMsg;
import it.polimi.ingsw.tui.ClientState;
import it.polimi.ingsw.tui.MainMenuState;

import java.io.*;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SocketClient implements VirtualView {

//    private final BufferedReader input;
  //  private final VirtualServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    ClientState currentState;
    private int idGame;

    public SocketClient(ObjectInputStream in, ObjectOutputStream out)
    {
        this.in = in;
        this.out = out;
        currentState = new MainMenuState(this, new Scanner(System.in));
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setIdGame(int idGame)
    {
        this.idGame = idGame;
    }

    //not used (just in RMI)
    public VirtualServer getServer() {
        return null;
    }

    public void setCurrentState(ClientState state) {
        this.currentState = state;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void run() throws IOException, ClassNotFoundException {
      /*  new Thread(() -> {
            try {
                runVirtualServer();
                System.out.println("ciao");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();*/
        runCli();
    }

    //what I send to the server
    private void runCli() throws IOException, ClassNotFoundException {
        boolean correctInput;
        Scanner scan = new Scanner(System.in);
        while (true) {
            correctInput = false;
            currentState.display();
            currentState.promptForInput();
            int input = 0;
            while(!correctInput){
                try {
                    input = scan.nextInt();
                    correctInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input: Reinsert the value: ");


                }
                finally {
                    scan.nextLine();
                }
            }

            currentState.inputHandler(input);

        }
    }
/*
    //what I receive from the server
    private void runVirtualServer() throws IOException {
        String line;
        // Read message type
        while ((line = input.readLine()) != null) {
            // Read message and perform action
            switch (line) {
                //to handle the output of the server
                default -> System.err.println("[INVALID MESSAGE]");
            }
        }
    }
*/
    public boolean checkUsername(String username) throws IOException, ClassNotFoundException {
        out.writeObject(new CheckUsernameMsg(username));
        out.flush();
        out.reset();
        return Boolean.parseBoolean(unPackMsg().getResponse());
    }

    public ServerToClientMsg unPackMsg() throws IOException, ClassNotFoundException {

        ServerToClientMsg response;
        while ((response = (ServerToClientMsg) in.readObject()) != null) {
            return response;
        }


        return null;
    }


}
