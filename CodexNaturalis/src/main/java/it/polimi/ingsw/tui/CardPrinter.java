package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.strategyPatternObjective.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

public class CardPrinter {
    private static final String RESET = "\u001B[0m";

    private enum Color {
        GREEN("\u001B[42m"), BLUE("\u001B[44m"), RED("\u001B[41m"),
        PURPLE("\u001B[45m"), GREY("\u001B[100m"), WHITE("\u001B[47m");

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    private final int cardWidth;
    private final int cardHeight;
    private final int cornerSize;

    public CardPrinter() {
        this.cardWidth = 20;
        this.cardHeight = 5;
        this.cornerSize = 3;
    }

    public void printCard(Card card) {
        if (card instanceof GameCard gameCard) {
            printGameCard(gameCard);
        } else if (card instanceof ObjectiveCard objectiveCard) {
            printObjectiveCardDetails(objectiveCard);
        }
    }

    private void printGameCard(GameCard card) {
        Corner[] corners = card.getCorners();
        Color colorBackground = chooseColor(card.getBackSideResource());
        printColorBorder(corners[0], corners[1], colorBackground);
        if (card instanceof StarterCard starterCard) {
            ArrayList<Resource> frontSideResources = starterCard.getFrontSideResources();
            Color backgroundColor = Color.GREY; // Card background color

            int y = 1;
            for (Resource resource : frontSideResources) {
                if (y >= cardHeight) break; // Prevents writing outside the card bounds
                String lineContent = resource.toString(); // Prepare line content
                y++;
                printCenteredLine(lineContent, backgroundColor);
            }
            while (y <= cardHeight) {
                y++;
                printCenteredLine("", backgroundColor);
            }


        } else {
            //printColorBorder(corners[0], corners[1], colorBackground);
            for (int i = 0; i < cardHeight - 2; i++) {
                if (i == cardHeight / 2 - 1) {
                    printCardContent(card, colorBackground);
                } else {
                    System.out.println(colorBackground + repeat(cardWidth) + RESET);
                }
            }
            //printColorBorder(corners[2], corners[3], colorBackground);

        }
        printColorBorder(corners[2], corners[3], colorBackground);
        System.out.println(); // For spacing between cards

    }

    private void printColorBorder(Corner cornerLeft, Corner cornerRight, Color color) {
        String border = createBorderString(cornerLeft, cornerRight, color);
        System.out.println(border);
    }

    private String createBorderString(Corner cornerLeft, Corner cornerRight, Color color) {
        String leftInitial = getInitial(cornerLeft); // Get the initial for the left corner if visible
        String rightInitial = getInitial(cornerRight); // Get the initial for the right corner if visible

        if (cornerLeft.isHidden() && cornerRight.isHidden()) {
            return color + repeat(cardWidth) + RESET;
        } else if (!cornerLeft.isHidden() && cornerRight.isHidden()) {
            return Color.GREY + leftInitial + repeat(cornerSize - 1) + color + repeat(cardWidth - cornerSize) + RESET;
        } else if (cornerLeft.isHidden() && !cornerRight.isHidden()) {
            return color + repeat(cardWidth - cornerSize) + Color.GREY + rightInitial + repeat(cornerSize - 1) + RESET;
        } else {
            return Color.GREY + leftInitial + repeat(cornerSize - 1) + color + repeat(cardWidth - 2 * cornerSize) + Color.GREY + rightInitial + repeat(cornerSize - 1) + RESET;
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

    private void printCardContent(Card card, Color colorBackground) {
        if (card instanceof GoldCard goldCard) {
            String pointsDetail = "Pts: " + card.getPoints() + " " + goldCard.getPointType();
            System.out.println(colorBackground + " " + centerString(pointsDetail, cardWidth - 2) + " " + RESET);
            // Handling requirements, format them to show only the first character of each resource name
            if (!goldCard.getRequirements().isEmpty()) {
                StringBuilder reqDetails = new StringBuilder("Req: ");
                goldCard.getRequirements().forEach((resource, quantity) -> {
                    String resourceInitial = resource.name().substring(0, 1); // Get first character of the resource name
                    reqDetails.append(resourceInitial).append("=").append(quantity).append(", ");
                });
                // Remove the last comma and space
                reqDetails.setLength(reqDetails.length() - 2);
                System.out.println(colorBackground + " " + centerString(reqDetails.toString(), cardWidth - 2) + " " + RESET);
            }

        } else if (card instanceof ResourceCard resourceCard) {
            String pointsDetail = "Pts: " + resourceCard.getPoints();
            System.out.println(colorBackground + " " + centerString(pointsDetail, cardWidth - 2) + " " + RESET);
        }
    }

    private void printObjectiveCardDetails(ObjectiveCard card) {
        ObjectiveStrategy strategy = card.getStrategy();
        // Assuming strategies have a method to return their details in String format
        if (strategy instanceof DiagonalPatternStrategy diagonalPatternStrategy) {
            printDiagonalPatternStrategy(diagonalPatternStrategy);
        } else if (strategy instanceof VerticalPatternStrategy verticalPatternStrategy) {
            printVerticalPatternStrategy(verticalPatternStrategy);
        } else if (strategy instanceof ObjectStrategy objectStrategy) {
            printObjectStrategy(objectStrategy);
        } else if (strategy instanceof ResourceStrategy resourceStrategy) {
            printResourceStrategy(resourceStrategy);
        }
        String pointsDetail = "Pts: " + card.getPoints() + " ";
        System.out.println(Color.GREY + " " + centerString(pointsDetail, cardWidth - 2) + " " + RESET);
        System.out.println();
    }

    private void printDiagonalPatternStrategy(DiagonalPatternStrategy strategy) {
        Point offset = strategy.getDiagonalOffset();
        Resource primarySource = strategy.getPrimarySource();
        Color resourceColor = chooseColor(primarySource);
        Color backgroundColor = Color.GREY; // Default background color for the card

        // Calculate center positions correctly
        int centerX = (cardWidth / 2) - 1; // Center square starts at cardWidth/2 - 1
        int centerY = cardHeight / 2;

        // Adjust the x positions for the diagonal based on center X and the offset
        Point centerPoint = new Point(centerX / 2, centerY); // Divide by 2 because each x represents 2 spaces
        Point firstOffsetPoint = new Point((centerX / 2) + offset.x, centerY - offset.y);
        Point secondOffsetPoint = new Point((centerX / 2) - offset.x, centerY + offset.y);

        // Print the card with the diagonal
        for (int y = 0; y < cardHeight - 1; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < cardWidth / 2; x++) { // Iterate over half the cardWidth because each square takes 2 character spaces
                if ((x == centerPoint.x && y == centerPoint.y) ||
                        (x == firstOffsetPoint.x && y == firstOffsetPoint.y) ||
                        (x == secondOffsetPoint.x && y == secondOffsetPoint.y)) {
                    line.append(resourceColor);  // Apply resource color for diagonal squares
                } else {
                    line.append(backgroundColor); // Apply grey color for the rest of the card
                }
                line.append("  ");  // Append two spaces for each square
            }
            line.append(RESET);
            System.out.println(line);
        }
    }

    private void printVerticalPatternStrategy(VerticalPatternStrategy strategy) {
        Resource primarySource = strategy.getPrimarySource();
        Resource secondarySource = strategy.getSecondarySource();
        Point whichCorner = strategy.getWhichCorner();
        Color primaryColor = chooseColor(primarySource);
        Color secondaryColor = chooseColor(secondarySource);

        // Set base colors
        Color backgroundColor = Color.GREY;

        // Calculate the central x position for the primary squares
        int centerOfCardX = cardWidth / 2;  // Center of the card width
        int primaryX = centerOfCardX - 2;  // Center primary squares a bit left from the middle (each square is two characters wide)
        int primaryY1 = whichCorner.y;  // Y position for the first primary square
        int primaryY2 = whichCorner.y + 1;  // Y position for the second primary square, directly below the first

        // Calculate position for the secondary square using the offset
        int secondaryX = primaryX + whichCorner.x * 2;  // X position offset from the primaryX
        int secondaryY = primaryY2 + whichCorner.y;  // Y position offset from the second primary square

        // Print the card with the specified pattern
        for (int y = 0; y < cardHeight; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < cardWidth; x += 2) { // Increment by 2 since each square occupies two spaces
                if ((x == primaryX) && (y == primaryY1 || y == primaryY2)) {
                    line.append(primaryColor).append("  "); // Append two spaces of primary color for primary squares
                } else if (x == secondaryX && y == secondaryY) {
                    line.append(secondaryColor).append("  "); // Append two spaces of secondary color for the secondary square
                } else {
                    line.append(backgroundColor).append("  "); // Append two spaces of background color
                }
            }
            line.append(RESET);
            System.out.println(line);
        }
    }


    private void printResourceStrategy(ResourceStrategy strategy) {
        Resource resource = strategy.getResourceStrategyToCheck();
        int numResource = strategy.getNumOfResourceToCheck();
        Color resourceColor = chooseColor(resource);

        // Calculate the center position for the number of resources
        int centerTextX = (cardWidth - String.valueOf(numResource).length()) / 2;
        int centerTextY = cardHeight / 2;

        // Print the card with resource color and centered text
        for (int y = 0; y < cardHeight - 1; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < cardWidth; x++) {
                line.append(resourceColor); // Apply resource color to the entire line
                if (y == centerTextY && x >= centerTextX && x < centerTextX + String.valueOf(numResource).length()) {
                    line.append(numResource); // Append the number at the center
                    x += String.valueOf(numResource).length() - 1; // Adjust x to account for the length of numResource
                } else {
                    line.append(" "); // Fill with space
                }
            }
            line.append(RESET);
            System.out.println(line);
        }
    }

    private void printObjectStrategy(ObjectStrategy strategy) {
        EnumMap<CornerObject, Integer> objectsToCheck = strategy.getObjectToCheck();
        Color backgroundColor = Color.GREY; // Card background color

        // Print the card with a gray background and objects listed with their counts
        int y = 1; // Start printing from the second line to give a top margin
        for (Map.Entry<CornerObject, Integer> entry : objectsToCheck.entrySet()) {
            if (y >= cardHeight) break; // Prevents writing outside the card bounds
            String lineContent = entry.getKey().toString() + ": " + entry.getValue(); // Prepare line content
            y++;
            printCenteredLine(lineContent, backgroundColor);
        }

        // Fill the rest of the card with blank lines if needed
        while (y < cardHeight) {
            y++;
            printCenteredLine("", backgroundColor);
        }
    }

    private void printCenteredLine(String content, Color backgroundColor) {
        int contentLength = content.length();
        int start = (cardWidth - contentLength) / 2; // Calculate the starting point for the content to be centered
        StringBuilder line = new StringBuilder();

        line.append(backgroundColor); // Start with the background color
        for (int x = 0; x < cardWidth; x++) {
            if (x >= start && x < start + contentLength) {
                line.append(content.charAt(x - start)); // Add the content character by character
            } else {
                line.append(" "); // Fill the rest of the line with spaces
            }
        }
        line.append(RESET); // Reset the color at the end of the line
        System.out.println(line);
    }

    private Color chooseColor(Resource backSideResource) {
        if(backSideResource == null) return Color.WHITE;
        return switch (backSideResource) {
            case PLANT_KINGDOM -> Color.GREEN;
            case ANIMAL_KINGDOM -> Color.BLUE;
            case FUNGI_KINGDOM -> Color.RED;
            case INSECT_KINGDOM -> Color.PURPLE;
        };
    }

    private String repeat(int times) {
        return " ".repeat(Math.max(0, times));
    }

    private String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        return repeat(padding) + text + repeat(padding + (text.length() % 2 == 1 ? 1 : 0));
    }
}
