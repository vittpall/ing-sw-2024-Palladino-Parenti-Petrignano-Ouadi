package it.polimi.ingsw.view.tui;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.*;
import java.util.HashMap;

/**
 * Class used to print the cards in the TUI
 */
public class PrintContext {
    private final CardPrinter printer;

    /**
     * Constructor
     *
     * @param printer the instance of the Printer used to print the cards
     */
    public PrintContext(CardPrinter printer) {
        this.printer = printer;
    }


    /**
     * This method prints the client's desk, with every card and their position
     *
     * @param desk reference to the user's desk: his cards and the respective position
     */
    public void printDesk(HashMap<Point, GameCard> desk) {
        desk.forEach((point, gameCard) -> {
            String formattedCoordinates = String.format("Position: (%d, %d)", point.x, point.y);
            System.out.println(formattedCoordinates);
            printer.printCard(gameCard, gameCard.isPlayedFaceDown());
        });
    }

    /**
     * Prints the centered line of every StarterCard or ObjectiveCard, if they have a pattern in the middle
     *
     * @param content         String representing the value to print
     * @param backgroundColor CardPrinter.Color representing the color of the card
     */
    public void printCenteredLine(String content, CardPrinter.Color backgroundColor) {
        int contentLength = content.length();
        int start = (printer.getCardWidth() - contentLength) / 2; // Calculate the starting point for the content to be centered
        StringBuilder line = new StringBuilder();

        line.append(backgroundColor); // Start with the background color
        for (int x = 0; x < printer.getCardWidth(); x++) {
            if (x >= start && x < start + contentLength) {
                line.append(content.charAt(x - start)); // Add the content character by character
            } else {
                line.append(" "); // Fill the rest of the line with spaces
            }
        }
        line.append(CardPrinter.RESET); // Reset the color at the end of the line
        System.out.println(line);
    }

    /**
     * Repeats the space character a number of times
     *
     * @param times Integer representing the number of times to repeat the space character
     * @return String representing the space character repeated the requested number of times
     */
    public String repeat(int times) {
        return " ".repeat(Math.max(0, times));
    }

    /**
     * Prints the upper or lower border of the card with the set color and Resources or CornerObjects to the sides
     *
     * @param cornerLeft  Corner representing the left corner of the border
     * @param cornerRight Corner representing the right corner of the border
     * @param color       CardPrinter.Color representing the background color of the card
     */
    public void printColorBorder(Corner cornerLeft, Corner cornerRight, CardPrinter.Color color) {
        String border = createBorderString(cornerLeft, cornerRight, color);
        System.out.println(border);
    }

    /**
     * Creates the border string with the set color and Resources or CornerObjects to the sides
     *
     * @param cornerLeft  Corner representing the left corner of the border
     * @param cornerRight Corner representing the right corner of the border
     * @param color       CardPrinter.Color representing the background color of the card
     * @return String representing the border of the card with the set color and Resources or CornerObjects to the sides
     */
    private String createBorderString(Corner cornerLeft, Corner cornerRight, CardPrinter.Color color) {
        String leftInitial = getInitial(cornerLeft); // Get the initial for the left corner if visible
        String rightInitial = getInitial(cornerRight); // Get the initial for the right corner if visible

        if (cornerLeft.isHidden() && cornerRight.isHidden()) {
            return color + repeat(printer.getCardWidth()) + CardPrinter.RESET;
        } else if (!cornerLeft.isHidden() && cornerRight.isHidden()) {
            return CardPrinter.Color.GREY + leftInitial + repeat(printer.getCornerSize() - 1) + color + repeat(printer.getCardWidth() - printer.getCornerSize()) + CardPrinter.RESET;
        } else if (cornerLeft.isHidden() && !cornerRight.isHidden()) {
            return color + repeat(printer.getCardWidth() - printer.getCornerSize()) + CardPrinter.Color.GREY + rightInitial + repeat(printer.getCornerSize() - 1) + CardPrinter.RESET;
        } else {
            return CardPrinter.Color.GREY + leftInitial + repeat(printer.getCornerSize() - 1) + color + repeat(printer.getCardWidth() - 2 * printer.getCornerSize()) + CardPrinter.Color.GREY + rightInitial + repeat(printer.getCornerSize() - 1) + CardPrinter.RESET;
        }
    }


    /**
     * Gets the initial of the Resource or CornerObject to be printed in the corner
     *
     * @param corner Corner that has the Resource or CornerObject to get the initial from
     * @return String representing the initial of the Resource or CornerObject
     */
    private String getInitial(Corner corner) {
        if (corner.getResource() != null) {
            return String.valueOf(corner.getResource().get().charAt(0));
        } else if (corner.getObject() != null) {
            return String.valueOf(corner.getObject().get().charAt(0));
        }
        return " ";
    }

    /**
     * Set the centered string of the card with the right text inside
     *
     * @param text  is the string to print in the middle of the card
     * @param width is the width of the card
     * @return the String with the text in the middle
     */
    public String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        return repeat(padding) + text + repeat(padding + (text.length() % 2 == 1 ? 1 : 0));
    }

    /**
     * @return the width of the card
     */
    public int getCardWidth() {
        return printer.getCardWidth();
    }

    /**
     * @return the height of the card
     */
    public int getCardHeight() {
        return printer.getCardHeight();
    }

    /**
     * Return the color of the card based on its background Resource
     *
     * @param resource is a reference to the Resource of the card
     * @return the color of the card
     */
    public CardPrinter.Color chooseColor(Resource resource) {
        return printer.chooseColor(resource);
    }
}
