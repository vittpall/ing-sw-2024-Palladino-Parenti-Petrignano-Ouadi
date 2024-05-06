package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class DrawCardState implements ClientState{
    VirtualView client;
    private final Scanner scanner;

    public DrawCardState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void display() {
        System.out.println("Draw card state");
        //mostrare i deck e mostrare le carte da poter scegliere
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        //l'input mi darà 1 se è resource e 2 se è gold per esempio
        switch (input){
            case 1-2:
                //scelgo tra le carte visibili o meno. magari 1 è la prima visible, 2 la seconda e 3 draw a caso
                int inVisible= chooseWhichCardToDraw();
                try{
                    client.drawCard(input, inVisible);
                    String nextState = client.getNextState();
                    if(nextState.equals("WaitForYourTurnState")){
                        client.setCurrentState(new WaitForYourTurnState(client, scanner));
                    }else if(nextState.equals("LastRoundState")){
                        //creare gli stati per l'ultimo round e poi lo stato per la vincita del giocatore
                    }

                }catch(RemoteException | CardNotFoundException ex){
                    //TODO
                }
                break;
            case 3:
                System.exit(0);
                break;
            case 4:
                client.setCurrentState(new ChatState(client, scanner, this));
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private int chooseWhichCardToDraw() {
        return 1;
    }

    @Override
    public void promptForInput() {
        System.out.println("1. Draw resource card");
        System.out.println("2. Draw gold card");
        System.out.println("3. Exit");
        System.out.println("4. Chat");

    }
}
