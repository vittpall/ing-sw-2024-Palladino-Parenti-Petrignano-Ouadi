package it.polimi.ingsw.gui;


import it.polimi.ingsw.model.BoardCoordinate;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.util.BoardConfig.loadCoordinates;

public class GameBoard {

    private final List<BoardCoordinate> coordinates;
    private final AnchorPane gameBoardAnchorPane;
    private final Map<Integer, TokenView> tokens;
    private final Map<BoardCoordinate, Integer> tokenPositions;

    public GameBoard(AnchorPane gameBoardAnchorPane) {
        this.gameBoardAnchorPane = gameBoardAnchorPane;
        this.coordinates = loadCoordinates();
        tokens = new HashMap<>();
        this.tokenPositions = new HashMap<>();
    }

    public void addToken(int playerId, String imagePath) {
        BoardCoordinate startCoordinate = coordinates.getFirst();
        if (startCoordinate != null) {
            double[] layouts = getTokenLayout(startCoordinate);
            TokenView token = new TokenView(imagePath, layouts[0], layouts[1]);
            token.setLayoutX(token.getOffsetX());
            token.setLayoutY(token.getOffsetY());
            gameBoardAnchorPane.getChildren().add(token);
            tokens.put(playerId, token);
            tokenPositions.put(startCoordinate, tokenPositions.getOrDefault(startCoordinate, 0) + 1);
        }
    }

    public void updateTokenPosition(int playerId, int newScore) {
        BoardCoordinate newCoordinate = coordinates.stream().filter(coord -> coord.score() == newScore).findFirst().orElse(null);
        if (newCoordinate != null) {
            double[] layouts = getTokenLayout(newCoordinate);
            TokenView playerToken = tokens.get(playerId);
            playerToken.setLayoutX(layouts[0]);
            playerToken.setLayoutY(layouts[1]);
            tokenPositions.put(newCoordinate, tokenPositions.getOrDefault(newCoordinate, 0) + 1);
        }
    }

    private double[] getTokenLayout(BoardCoordinate coordinate) {
        double baseX = Double.parseDouble(coordinate.x().replace("%", "")) / 100 * gameBoardAnchorPane.getWidth();
        double baseY = Double.parseDouble(coordinate.y().replace("%", "")) / 100 * gameBoardAnchorPane.getHeight();

        double offsetX = 0;
        double offsetY = 0;
        int positionCount = tokenPositions.getOrDefault(coordinate, 0);

        switch (positionCount) {
            case 1:
                offsetX = -30;
                break;
            case 2:
                offsetY = -30;
                break;
            case 3:
                offsetX = -30;
                offsetY = -30;
                break;
        }
        return new double[]{baseX + offsetX, baseY + offsetY};
    }

    public boolean hasToken(int playerId) {
        return tokens.containsKey(playerId);
    }

}



