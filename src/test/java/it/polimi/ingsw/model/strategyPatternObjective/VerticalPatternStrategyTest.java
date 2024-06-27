package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.ResourceCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.controller.exceptions.PlaceNotAvailableException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerticalPatternStrategyTest {



    @Test
    void fromCoordinates_givenCoordinates_returnOffset() {
        assertEquals(VerticalPatternStrategy.Offset.BOTTOM_LEFT, VerticalPatternStrategy.Offset.fromCoordinates(-1, -1));
        assertEquals(VerticalPatternStrategy.Offset.TOP_RIGHT, VerticalPatternStrategy.Offset.fromCoordinates(1, 3));
        assertEquals(VerticalPatternStrategy.Offset.TOP_LEFT, VerticalPatternStrategy.Offset.fromCoordinates(-1, 3));
        assertEquals(VerticalPatternStrategy.Offset.BOTTOM_RIGHT, VerticalPatternStrategy.Offset.fromCoordinates(1, -1));
    }

    @Test
    void isSatisfied_givenDeskWithNoResources_returnZero() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.BOTTOM_RIGHT);
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithWrongResources_returnZero() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.TOP_RIGHT);
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithCorrectResourcesRightBottom_returnOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.BOTTOM_RIGHT);
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new StarterCard(null, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 1));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 3));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 4));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithCorrectResourcesRightUp_returnOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.TOP_RIGHT);
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new StarterCard(null, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
            desk.addCard(new StarterCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 3));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 4));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 5));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithCorrectResourcesLeftUp_returnOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.TOP_LEFT);
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new StarterCard(null, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 3));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 4));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 5));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithCorrectResourcesLeftBottom_returnOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.BOTTOM_LEFT);
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new StarterCard(null, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 3));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 4));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenADeterminedStartingPoint_returnOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.BOTTOM_LEFT);
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new StarterCard(null, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 1));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 3));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 4));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        assertTrue(verticalPatternStrategy.CheckCorner(desk.getDesk(), new Point(0, 2)));
    }

    @Test
    void isSatisfied_givenDeskTopLeft_returnMoreThanOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, VerticalPatternStrategy.Offset.TOP_RIGHT);
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new StarterCard(null, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 3));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 3));
            desk.addCard(new ResourceCard(Resource.ANIMAL_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 4));
            desk.addCard(new ResourceCard(Resource.ANIMAL_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 5));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 6));
            desk.addCard(new ResourceCard(Resource.ANIMAL_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 7));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 8));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, 9));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }


        assertEquals(1, verticalPatternStrategy.isSatisfied(desk));
    }


}