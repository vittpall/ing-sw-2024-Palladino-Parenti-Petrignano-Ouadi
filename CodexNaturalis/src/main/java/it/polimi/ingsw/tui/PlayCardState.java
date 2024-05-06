package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Exceptions.CardNotFoundException;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.awt.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class PlayCardState implements ClientState{
    VirtualView client;
    private final Scanner scanner;

    public PlayCardState(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("Play card state");
        //mostrare il proprio desk e mostrare la mano in cui far scegliere quale carta giocare
    }

    @Override
    public void inputHandler(int input) throws IOException, ClassNotFoundException, InterruptedException {
        switch (input) {
            case 1-2-3:
                //scelgo se giocare la carta 1,2,3
                Point pointChosen= choosePosition();
                boolean faceDown= chooseIfFaceDown();
                try{
                    client.playCard(input-1, faceDown, pointChosen);
                }catch(RemoteException ex){
                    System.out.println(ex.getMessage());
                }catch(PlaceNotAvailableException ex){
                    System.out.println("Place not available");
                }catch(CardNotFoundException ex){
                    System.out.println("Card not found");
                }catch(RequirementsNotMetException ex){
                    System.out.println("Requirements not met. Please choose another card");
                    //rimandare dopo tutte le eccezioni in questo stato
                }
                client.setCurrentState(new DrawCardState(client, scanner));
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private boolean chooseIfFaceDown() {
        //metodo in cui si chiede al giocatore se vuole giocare la carta coperta o scoperta
        return false;
    }

    private Point choosePosition() {
        //metodo in cui si chiede al giocatore in che posizione vuole giocare la carta
        //se non è disponibile la posizione si richiede fino a quando non ce n'è una disponibile
        return null;
    }

    @Override
    public void promptForInput() {

    }
}
