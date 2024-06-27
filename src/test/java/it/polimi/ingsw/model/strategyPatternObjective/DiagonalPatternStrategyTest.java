package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.controller.exceptions.PlaceNotAvailableException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiagonalPatternStrategyTest {

    @Test
    void isSatisfied() {
        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.ANIMAL_KINGDOM, new Point(1, 1));
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, diagonalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void checkDiagonal_givenDeskMockUp_returnOne() {

        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card1 = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);


        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(-1, 1));
            desk.addCard(card2, new Point(-2, 2));
            desk.addCard(card3, new Point(-3, 3));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, -1));

        assertEquals(1, diagonalPatternStrategy.isSatisfied(desk));

    }


    @Test
    void checkDiagonal_givenDiagonal_returnMoreThanOne() {
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card5 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card6 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(-1, 1));
            desk.addCard(card2, new Point(-2, 2));
            desk.addCard(card3, new Point(-3, 3));
            desk.addCard(card4, new Point(-4, 4));
            desk.addCard(card5, new Point(-5, 5));
            desk.addCard(card6, new Point(-6, 6));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, -1));

        assertEquals(2, diagonalPatternStrategy.isSatisfied(desk));

    }

    @Test
    void checkDiagonal_givenDiagonalCardBetween_returnMoreThanOne() {
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.FUNGI_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card5 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card6 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card7 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(-1, 1));
            desk.addCard(card2, new Point(-2, 2));
            desk.addCard(card3, new Point(-3, 3));
            desk.addCard(card4, new Point(-4, 4));
            desk.addCard(card5, new Point(-5, 5));
            desk.addCard(card6, new Point(-6, 6));
            desk.addCard(card7, new Point(-7, 7));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, -1));

        assertEquals(2, diagonalPatternStrategy.isSatisfied(desk));

    }

    @Test
    void checkDiagonal_givenRightDiagonalCardBetween_returnMoreThanOne() {
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.FUNGI_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card5 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card6 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card7 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(1, 1));
            desk.addCard(card2, new Point(2, 2));
            desk.addCard(card3, new Point(3, 3));
            desk.addCard(card4, new Point(4, 4));
            desk.addCard(card5, new Point(5, 5));
            desk.addCard(card6, new Point(6, 6));
            desk.addCard(card7, new Point(7, 7));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, 1));

        assertEquals(2, diagonalPatternStrategy.isSatisfied(desk));

    }

    @Test
    void checkDiagonal_givenRightDiagonalOddNumberCard_returnOne() {
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card5 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);


        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(1, 1));
            desk.addCard(card2, new Point(2, 2));
            desk.addCard(card3, new Point(3, 3));
            desk.addCard(card4, new Point(4, 4));
            desk.addCard(card5, new Point(5, 5));

        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, 1));

        assertEquals(1, diagonalPatternStrategy.isSatisfied(desk));

    }

    @Test
    void checkDiagonal_givenRightDiagonalCardBetween_returnOne() {
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }


        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card5 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card6 = new ResourceCard(Resource.ANIMAL_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(1, 1));
            desk.addCard(card2, new Point(2, 2));
            desk.addCard(card6, new Point(3, 3));
            desk.addCard(card3, new Point(4, 4));
            desk.addCard(card4, new Point(5, 5));
            desk.addCard(card5, new Point(6, 6));

        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, 1));

        assertEquals(1, diagonalPatternStrategy.isSatisfied(desk));

    }

    @Test
    void checkDiagonal_twoOverlappedDiagon_returnMoreThanOne() {
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card0 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card4 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card5 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card6 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card7 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card8 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        GameCard card9 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        PlayerDesk desk = new PlayerDesk();

        try {
            desk.addCard(card0, new Point(0, 0));
            desk.addCard(card1, new Point(-1, 1));
            desk.addCard(card2, new Point(-2, 2));
            desk.addCard(card3, new Point(-3, 3));
            desk.addCard(card4, new Point(-4, 4));
            desk.addCard(card5, new Point(0, 2));
            desk.addCard(card6, new Point(1, 1));
            desk.addCard(card7, new Point(-1, 3));
            desk.addCard(card8, new Point(-2, 4));
            desk.addCard(card9, new Point(-3, 5));

        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }

        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.INSECT_KINGDOM, new Point(1, -1));

        assertEquals(2, diagonalPatternStrategy.isSatisfied(desk));

    }

    @Test
    void primarySource() {
        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.ANIMAL_KINGDOM, new Point(1, 1));
        assertEquals(Resource.ANIMAL_KINGDOM, diagonalPatternStrategy.primarySource());
    }

    @Test
    void diagonalOffset() {
        DiagonalPatternStrategy diagonalPatternStrategy = new DiagonalPatternStrategy(Resource.ANIMAL_KINGDOM, new Point(1, 1));
        assertEquals(new Point(1, 1), diagonalPatternStrategy.diagonalOffset());
    }
}