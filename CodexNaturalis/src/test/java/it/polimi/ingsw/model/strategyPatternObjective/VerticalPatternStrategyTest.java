package it.polimi.ingsw.model.strategyPatternObjective;

import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.ResourceCard;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.awt.*;

class VerticalPatternStrategyTest {

    // Test for the method isSatisfied in VerticalPatternStrategy
    @Test
    void isSatisfied_givenNullDesk_returnNullPointer() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, new Point(1, -1));
        assertThrows(NullPointerException.class, () -> verticalPatternStrategy.isSatisfied(null));
    }

    @Test
    void isSatisfied_givenDeskWithNoResources_returnZero() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, new Point(1, -1));
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithWrongResources_returnZero() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, new Point(1, -1));
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, verticalPatternStrategy.isSatisfied(desk));
    }

    @Test
    void isSatified_givenDeskWithCorrectResources_returnOne() {
        VerticalPatternStrategy verticalPatternStrategy = new VerticalPatternStrategy(Resource.FUNGI_KINGDOM, Resource.PLANT_KINGDOM, new Point(1, -1));
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }
        try {
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 0));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(-1, 1));
            desk.addCard(new ResourceCard(Resource.FUNGI_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(0, 2));
            desk.addCard(new ResourceCard(Resource.PLANT_KINGDOM, "path/to/front/image", "path/to/back/image", 0, PointType.CORNER, null, corners), new Point(1, -1));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        assertEquals(1, verticalPatternStrategy.isSatisfied(desk));
    }



}