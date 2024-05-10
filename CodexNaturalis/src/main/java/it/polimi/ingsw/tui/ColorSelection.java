package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.RemoteInterfaces.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class ColorSelection implements ClientState {
    VirtualView client;
    private final Scanner scanner;

    @Override
    public void promptForInput() {
        System.out.print("Enter your choice: Select an available color:\n 1. RED\n 2. BLUE\n 3. YELLOW\n 4. GREEN\n: ");
    }

    public ColorSelection(VirtualView client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        int i;
        System.out.println("\n ----------Choose a color----------");
        System.out.println("Available colors: \n");
        try {
            ArrayList<TokenColor> ColorList = client.getAvailableColors();
            for (i = 0; i < ColorList.size(); i++) {
                System.out.println(ColorList.get(i));
            }
        } catch (RemoteException ex) {
            System.out.println("Remote exception\n" + ex.getMessage());
        }
        System.out.println("--------------------------------\n");
    }

    @Override
    public void inputHandler(int input) throws RemoteException {
        ArrayList<TokenColor> ColorList = client.getAvailableColors();
        switch (input) {
            case 1:
                if (ColorList.contains(TokenColor.RED)) {
                    client.setTokenColor(TokenColor.RED);
                    client.setCurrentState(new InitializeObjectiveCardState(client, scanner));
                } else
                    System.out.println("Color not available\n");
                break;
            case 2:
                if (ColorList.contains(TokenColor.BLUE)) {
                    client.setTokenColor(TokenColor.BLUE);
                    client.setCurrentState(new InitializeObjectiveCardState(client, scanner));
                } else
                    System.out.println("Color not available\n");
                break;
            case 3:
                if (ColorList.contains(TokenColor.YELLOW)) {
                    client.setTokenColor(TokenColor.YELLOW);
                    client.setCurrentState(new InitializeObjectiveCardState(client, scanner));
                } else
                    System.out.println("Color not available\n");
                break;
            case 4:
                if (ColorList.contains(TokenColor.GREEN)) {
                    client.setTokenColor(TokenColor.GREEN);
                    client.setCurrentState(new InitializeObjectiveCardState(client, scanner));
                } else
                    System.out.println("Color not available\n");
                break;

            case 5:
                try {
                    client.close();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Invalid input");
                display();
                inputHandler(scanner.nextInt());
        }
    }
}