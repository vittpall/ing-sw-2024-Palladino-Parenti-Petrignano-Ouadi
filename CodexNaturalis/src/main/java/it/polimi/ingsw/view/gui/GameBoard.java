package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.BoardCoordinate;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.util.BoardConfig.loadCoordinates;

public class GameBoard {

    private final List<BoardCoordinate> coordinates;
    private final AnchorPane gameBoardAnchorPane;
    private final Map<String, TokenView> tokens;
    private final Map<BoardCoordinate, Integer> tokenPositions;
    private final Map<String, BoardCoordinate> currentPlayerPositions;

    /**
     * Constructor
     *
     * @param gameBoardAnchorPane is the place where there is the scoreboard
     */
    public GameBoard(AnchorPane gameBoardAnchorPane) {
        this.gameBoardAnchorPane = gameBoardAnchorPane;
        this.coordinates = loadCoordinates();
        this.tokens = new HashMap<>();
        this.tokenPositions = new HashMap<>();
        this.currentPlayerPositions = new HashMap<>();
    }

    /**
     * this method add the tokens of the clients to the board. Every client has their chosen color
     *
     * @param username  is the personal username associated to the client
     * @param imagePath is teh image of the token
     */
    public void addToken(String username, String imagePath) {
        BoardCoordinate startCoordinate = coordinates.getFirst();
        if (startCoordinate != null) {
            double[] layouts = getTokenLayout(startCoordinate);
            TokenView token = new TokenView(imagePath, layouts[0], layouts[1]);
            token.setLayoutX(token.getOffsetX());
            token.setLayoutY(token.getOffsetY());
            gameBoardAnchorPane.getChildren().add(token);
            tokens.put(username, token);
            tokenPositions.put(startCoordinate, tokenPositions.getOrDefault(startCoordinate, 0) + 1);
            currentPlayerPositions.put(username, startCoordinate);
        }
    }

    /**
     * this method updates the position of the token on the image of the board right after the player has reached some points
     *
     * @param username is the personal username associated to the client
     * @param newScore is the updated client's score
     */
    public void updateTokenPosition(String username, int newScore) {
        BoardCoordinate oldCoordinate = currentPlayerPositions.get(username);
        BoardCoordinate newCoordinate = coordinates.stream().filter(coord -> coord.score() == newScore).findFirst().orElse(null);
        if (!oldCoordinate.equals(newCoordinate) && newCoordinate != null) {
            tokenPositions.put(oldCoordinate, tokenPositions.get(oldCoordinate) - 1);
            double[] layouts = getTokenLayout(newCoordinate);
            TokenView playerToken = tokens.get(username);
            playerToken.setLayoutX(layouts[0]);
            playerToken.setLayoutY(layouts[1]);
            tokenPositions.put(newCoordinate, tokenPositions.getOrDefault(newCoordinate, 0) + 1);
            currentPlayerPositions.put(username, newCoordinate);
        }
    }

    /**
     * this method gets the position of the token on the board and changes it
     *
     * @param coordinate is the position of the token
     * @return the new coordinates of the token
     */
    private double[] getTokenLayout(BoardCoordinate coordinate) {
        double baseX = Double.parseDouble(coordinate.x().replace("%", "")) / 100 * gameBoardAnchorPane.getWidth();
        double baseY = Double.parseDouble(coordinate.y().replace("%", "")) / 100 * gameBoardAnchorPane.getHeight();

        double offsetX = 0;
        double offsetY = 0;
        int positionCount = tokenPositions.getOrDefault(coordinate, 0);

        switch (positionCount) {
            case 1:
                offsetX = -20;
                break;
            case 2:
                offsetY = -20;
                break;
            case 3:
                offsetX = -20;
                offsetY = -20;
                break;
        }
        return new double[]{baseX + offsetX, baseY + offsetY};
    }

    /**
     * this method checks if the token is already on the board
     *
     * @param username is the personal username associated to the client
     * @return true if the token is on the board, false otherwise
     */
    public boolean hasToken(String username) {
        return tokens.containsKey(username);
    }

}



