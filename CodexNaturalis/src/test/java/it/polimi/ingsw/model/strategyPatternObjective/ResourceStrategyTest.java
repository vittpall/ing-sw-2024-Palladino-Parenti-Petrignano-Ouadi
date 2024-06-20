package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.ResourceCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceStrategyTest {


    @Test
    void isSatisfied_givenDeskWithNoResources_returnZero() {
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.FUNGI_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, resourceStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithWrongResources_returnZero() throws PlaceNotAvailableException {
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.FUNGI_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();

        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        Corner[] corners;

        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card1 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        desk.addCard(card1, new Point(0, 0));
        desk.addCard(card2, new Point(1, 1));

        assertEquals(0, resourceStrategy.isSatisfied(desk));


    }

    @Test
    void isSatisfied_givenDeskWithCorrectResources_moreThanOne() {
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.INSECT_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();

        PointType pointType = PointType.CORNER;
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
            desk.addCard(card1, new Point(0, 0));
            desk.addCard(card2, new Point(1, 1));
            desk.addCard(card3, new Point(2, 2));
            desk.addCard(card4, new Point(3, 3));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, resourceStrategy.isSatisfied(desk));


    }


    @Test
    void isSatisfied_givenDeskWithCorrectResources_returnOne() {
        ResourceStrategy resourceStrategy = new ResourceStrategy(Resource.INSECT_KINGDOM, 3);
        PlayerDesk desk = new PlayerDesk();

        PointType pointType = PointType.CORNER;
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
            desk.addCard(card1, new Point(0, 0));
            desk.addCard(card2, new Point(1, 1));
            desk.addCard(card3, new Point(2, 2));
            desk.addCard(card4, new Point(3, 3));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, resourceStrategy.isSatisfied(desk));


    }


}