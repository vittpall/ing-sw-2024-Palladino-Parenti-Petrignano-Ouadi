package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.*;
import java.util.HashMap;


public class PrintContext {
    private final CardPrinter printer;

    /**
     * Constructor
     *
     * @param printer
     */
    public PrintContext(CardPrinter printer) {
        this.printer = printer;
    }


    /**
     *this method prints the client's desk, with every card and their position
     * @param desk reference to GameCard, it is the respective card in the player's desk
     */
    public void printDesk(HashMap<Point, GameCard> desk) {
        desk.forEach((point, gameCard) -> {
            String formattedCoordinates = String.format("Position: (%d, %d)", point.x, point.y);
            System.out.println(formattedCoordinates);
            printer.printCard(gameCard, gameCard.isPlayedFaceDown());
        });
    }

    /**
     *
     * @param content
     * @param backgroundColor
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
     *
     * @param times
     * @return
     */
    public String repeat(int times) {
        return " ".repeat(Math.max(0, times));
    }

    /**
     *
     * @param cornerLeft
     * @param cornerRight
     * @param color
     */
    public void printColorBorder(Corner cornerLeft, Corner cornerRight, CardPrinter.Color color) {
        String border = createBorderString(cornerLeft, cornerRight, color);
        System.out.println(border);
    }

    /**
     *
     * @param cornerLeft
     * @param cornerRight
     * @param color
     * @return
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
     *
     * @param corner
     * @return
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
     *
     * @param text
     * @param width
     * @return
     */
    public String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        return repeat(padding) + text + repeat(padding + (text.length() % 2 == 1 ? 1 : 0));
    }

    /**
     *
     * @return the width of the card
     */
    public int getCardWidth() {
        return printer.getCardWidth();
    }

    /**
     *
     * @return the height of the card
     */
    public int getCardHeight() {
        return printer.getCardHeight();
    }

    /**
     *
     * @param resource is a reference to the Resource class
     * @return the color of the resource of the card
     */
    public CardPrinter.Color chooseColor(Resource resource) {
        return printer.chooseColor(resource);
    }
}
