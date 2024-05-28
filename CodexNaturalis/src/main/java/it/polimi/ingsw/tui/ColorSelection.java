package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class ColorSelection implements ClientState {
    BaseClient client;
    private final Scanner scanner;

    @Override
    public void promptForInput() {
    }

    public ColorSelection(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("\n---------- Choose a color ----------");
        System.out.println("Available colors:");
        try {
            ArrayList<TokenColor> colorList = client.getAvailableColors();
            for (TokenColor tokenColor : colorList) {
                String colorOutput = formatColorOutput(tokenColor);
                System.out.println(colorOutput);
            }
        } catch (RemoteException ex) {
            System.out.println("Remote exception: " + ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------------------------\n");
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        ArrayList<TokenColor> currentColorList = client.getAvailableColors();
        TokenColor selectedColor = getColorFromInput(input);

        if (selectedColor != null && currentColorList.contains(selectedColor)) {
            client.setTokenColor(selectedColor);
            client.setCurrentState(new InitializeObjectiveCardState(client, scanner));
        } else if (selectedColor != null) {
            System.out.println("Color not available, please select another color.");
            display();
            promptForInput();
            inputHandler(scanner.nextInt());
        } else {
            System.out.println("Invalid input, please use a valid number (1-4):");
            promptForInput();
            inputHandler(scanner.nextInt());
        }
    }

    private TokenColor getColorFromInput(int input) {
        return switch (input) {
            case 1 -> TokenColor.RED;
            case 2 -> TokenColor.BLUE;
            case 3 -> TokenColor.YELLOW;
            case 4 -> TokenColor.GREEN;
            default -> null; // Return null if input is invalid
        };
    }

    private String formatColorOutput(TokenColor color) {
        return switch (color) {
            case RED -> "1. RED";
            case BLUE -> "2. BLUE";
            case YELLOW -> "3. YELLOW";
            case GREEN -> "4. GREEN";
        };
    }

    @Override
    public String toString(){
        return "ColorSelection";
    }

    /**
     *
     */
    @Override
    public void refresh(String msg) {

    }
}