package it.polimi.ingsw.tui;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.GameCard;
import it.polimi.ingsw.model.GoldCard;
import it.polimi.ingsw.model.enumeration.Resource;

public class CardPrinter {
    private static final String RESET = "\u001B[0m";

    private enum Color {
        GREEN("\u001B[42m"), BLUE("\u001B[44m"), RED("\u001B[41m"),
        PURPLE("\u001B[45m"), GREY("\u001B[100m");

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

    public void printCard(GameCard card) {
        if (card == null || card.getCorners() == null) {
            throw new IllegalArgumentException("Invalid card or card corners configuration.");
        }
        Corner[] corners = card.getCorners();
        Color colorBackground = chooseColor(card.getBackSideResource());
        printColorBorder(corners[0], corners[1], colorBackground);
        for (int i = 0; i < cardHeight - 2; i++) {
            if (i == cardHeight / 2 - 1) {
                printCardContent(card, colorBackground);
            } else {
                System.out.println(colorBackground + repeat(cardWidth) + RESET);
            }
        }
        printColorBorder(corners[2], corners[3], colorBackground);
        System.out.println();
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

    private void printCardContent(GameCard card, Color colorBackground) {
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

        } else {
            String pointsDetail = "Pts: " + card.getPoints();
            System.out.println(colorBackground + " " + centerString(pointsDetail, cardWidth - 2) + " " + RESET);
        }
    }

    private Color chooseColor(Resource backSideResource) {
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
