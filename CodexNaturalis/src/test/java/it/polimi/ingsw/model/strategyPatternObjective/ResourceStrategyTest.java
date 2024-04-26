package it.polimi.ingsw.model.strategyPatternObjective;
import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.ResourceCard;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ResourceStrategyTest {

    // Test for the method isSatisfied in ResourceStrategy
    @Test
    void isSatisfied_givenNullDesk_returnNullPointer() {
        //it's always three
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.FUNGI_KINGDOM, 3);
        assertThrows(NullPointerException.class, () -> resourceStrategy.isSatisfied(null));
    }

    @Test
    void isSatisfied_givenDeskWithNoResources_returnZero() {
        //it's always three
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.FUNGI_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, resourceStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithWrongResources_returnZero()
    {
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.FUNGI_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();

        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        try {
            desk.addCard(card1, new Point(0,0));
            desk.addCard(card2, new Point(1,1));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }

        assertEquals(0, resourceStrategy.isSatisfied(desk));


    }

    @Test
    void isSatisfied_givenDeskWithCorrectResources_returnOne()
    {
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.INSECT_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();

        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(Resource.INSECT_KINGDOM);
        }

        ResourceCard card1 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.ANIMAL_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        try {
            desk.addCard(card1, new Point(0,0));
            desk.addCard(card2, new Point(1,1));
            desk.addCard(card3, new Point(2,2));
            desk.addCard(card4, new Point(3,3));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }

        assertEquals(3, resourceStrategy.isSatisfied(desk));


    }

}