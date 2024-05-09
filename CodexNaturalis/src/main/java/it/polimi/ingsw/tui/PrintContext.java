package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.*;
import java.util.HashMap;


public class PrintContext {
    private final CardPrinter printer;

    public PrintContext(CardPrinter printer) {
        this.printer = printer;
    }


    public void printDesk(HashMap<Point, GameCard> desk) {
       desk.forEach((point, gameCard) -> {
           System.out.println("Posizione: " + point.toString());
           printer.printCard(gameCard, gameCard.isPlayedFaceDown());
       });
    }

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

    public String repeat(int times) {
        return " ".repeat(Math.max(0, times));
    }

    public void printColorBorder(Corner cornerLeft, Corner cornerRight, CardPrinter.Color color) {
        String border = createBorderString(cornerLeft, cornerRight, color);
        System.out.println(border);
    }

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

    private String getInitial(Corner corner) {
        if (corner.getResource() != null) {
            return String.valueOf(corner.getResource().get().charAt(0));
        } else if (corner.getObject() != null) {
            return String.valueOf(corner.getObject().get().charAt(0));
        }
        return " ";
    }

    public String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        return repeat(padding) + text + repeat(padding + (text.length() % 2 == 1 ? 1 : 0));
    }

    public int getCardWidth() {
        return printer.getCardWidth();
    }

    public int getCardHeight() {
        return printer.getCardHeight();
    }

    public CardPrinter.Color chooseColor(Resource resource) {
        return printer.chooseColor(resource);
    }
}
