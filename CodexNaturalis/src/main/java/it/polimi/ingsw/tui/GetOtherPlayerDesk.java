package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.enumeration.PlayerState;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GetOtherPlayerDesk implements ClientState{
    VirtualView client;
    private final Scanner scanner;
    @Override
    public void promptForInput() {
    }

    public GetOtherPlayerDesk(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }
    @Override
    public void display(){
        System.out.println("\nSelect a Player: ");
        try {
            ArrayList<Player> allPlayers = client.getAllPlayers();
            for (int i=0;i<allPlayers.size();i++) {
                System.out.println((i+1)+". Player: " + allPlayers.get(i).getUsername());
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        Player selectedPlayer = getPlayerFromInput(input-1);
        if ( selectedPlayer != null) {
            CardPrinter printer = new CardPrinter();
            PlayerDesk playerDesk= selectedPlayer.getPlayerDesk();
            printer.printDesk(playerDesk.getDesk());
            client.setCurrentState(null);
        }
        else {
            System.out.println("Invalid input, please use a valid number:");
            promptForInput();
            inputHandler(scanner.nextInt());
        }
    }

    private Player getPlayerFromInput(int input) {
        try{
            if(input<client.getAllPlayers().size())
            {
                return client.getAllPlayers().get(input);
            }
            else
                return null;
        }
        catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String toString() {
        return "GetOtherPlayerDesk";
    }

    /**
     *
     */
    @Override
    public void refresh() {

    }
}
