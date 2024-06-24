package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.enumeration.TokenColor;
import it.polimi.ingsw.network.BaseClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This client state is used when the user needs to choose the color of his token
 * In this class the available colors are shown and the user can decide one of them
 */
public class ColorSelection implements ClientStateTUI {
    private final BaseClient client;
    private final Scanner scanner;

    /**
     * Constructor
     *
     * @param client  is a reference to the client class that can call the methods in the server
     * @param scanner is a reference to the class that handles and returns the input of the user
     */
    public ColorSelection(BaseClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    @Override
    public void promptForInput() {
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
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("------------------------------------\n");
    }

    @Override
    public void inputHandler(int input) throws IOException, InterruptedException {
        TokenColor selectedColor = getColorFromInput(input);

        if (selectedColor != null && client.getAvailableColors().contains(selectedColor)) {
            client.setTokenColor(selectedColor);
            client.setCurrentState(new InitializeObjectiveCardState(client));
        } else {
            System.out.println("Color not available, please select another color.");
            display();
            promptForInput();
            inputHandler(scanner.nextInt());
        }
    }

    /**
     * Private method used to transform the user input in the corresponding enum's color
     *
     * @param input is the client input
     * @return the enumeration of the color chosen
     */
    private TokenColor getColorFromInput(int input) {
        return switch (input) {
            case 1 -> TokenColor.RED;
            case 2 -> TokenColor.BLUE;
            case 3 -> TokenColor.YELLOW;
            case 4 -> TokenColor.GREEN;
            default -> null; // Return null if input is invalid
        };
    }

    /**
     * Private method used to transform the enum's color in the value to write in the display
     *
     * @param color is the enum's color to transform
     * @return the value to write in the display
     */
    private String formatColorOutput(TokenColor color) {
        return switch (color) {
            case RED -> "1. RED";
            case BLUE -> "2. BLUE";
            case YELLOW -> "3. YELLOW";
            case GREEN -> "4. GREEN";
        };
    }

    @Override
    public String toString() {
        return "ColorSelection";
    }

    /**
     * Notification method
     * It prints an updated copy of the available colors.
     *
     * @param availableColors the updated list of available colors
     */
    public void refresh(ArrayList<TokenColor> availableColors) {
        System.out.println("The available colors are now:");
        for (TokenColor tokenColor : availableColors) {
            String colorOutput = formatColorOutput(tokenColor);
            System.out.println(colorOutput);
        }
        System.out.println("------------------------------------\n");
    }


}