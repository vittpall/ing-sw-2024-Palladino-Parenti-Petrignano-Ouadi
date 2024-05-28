package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Chat;
import it.polimi.ingsw.network.BaseClient;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import it.polimi.ingsw.network.rmi.Client.RMIClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class PrivateChatSelectingReceiverState implements ClientState {
    private BaseClient client;
    private final Scanner scanner;
    private int finalOption;
    ArrayList<Player> availablePlayers;
    private ChatState returnState;

    public PrivateChatSelectingReceiverState(BaseClient client, Scanner scanner, ChatState returnState){
        this.client = client;
        this.scanner = scanner;
        this.finalOption = 0;
        this.returnState = returnState;
        this.availablePlayers = new ArrayList<>();
    }

    @Override
    public void display() {
        try {
            System.out.println("Private chat-------------------");
            System.out.println("Players to chat with: ---------");

            if(client.getAllPlayers() == null){
                System.out.println("No players available");
            }
            else
            {
                this.finalOption ++;
                availablePlayers = new ArrayList<>(client.getAllPlayers());
                removePlayerFromList(availablePlayers);

                for(int i = 0; i < availablePlayers.size(); i++){
                    this.finalOption += i;
                    Player player = availablePlayers.get(i);
                    System.out.println(this.finalOption + ") " + player.getUsername());
                }
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void removePlayerFromList(ArrayList<Player> availablePlayers) throws RemoteException {
        for(int i = 0; i < availablePlayers.size(); i++){
            if(availablePlayers.get(i).getUsername().equals(client.getUsername())){
                availablePlayers.remove(i);
            }
        }
    }

    @Override
    public void inputHandler(int input) {

        try {

            if(input == this.finalOption+1){
                client.setCurrentState(returnState);
            }else
            {
                if(client.getAllPlayers() != null) {
                    if (input > client.getAllPlayers().size()) {
                        System.out.println("Invalid input");
                        inputHandler(scanner.nextInt());
                    }
                    else
                    {
                        client.setCurrentState(new PrivateChatState(client, scanner, availablePlayers.get(input-1).getUsername(), this.returnState));
                    }
                }
                else
                {
                    System.out.println("Invalid input");
                    inputHandler(scanner.nextInt());
                }
            }


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void promptForInput() {
        System.out.println(this.finalOption+1 +".Exit chat");
    }

    public String toString() {
        return "PrivateChatSelectingReceiverState";
    }

    /**
     *
     */
    @Override
    public void refresh() {

    }
}
