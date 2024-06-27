package it.polimi.ingsw.model.strategyPatternObjective;

import it.polimi.ingsw.model.Corner;
import it.polimi.ingsw.model.PlayerDesk;
import it.polimi.ingsw.model.ResourceCard;
import it.polimi.ingsw.model.StarterCard;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.controller.exceptions.PlaceNotAvailableException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectStrategyTest {


    @Test
    void isSatisfied_givenDeskWithNoResources_returnZero() {
        ObjectStrategy objectStrategy = new ObjectStrategy(new PlayerDesk().getTotalObjects());
        PlayerDesk desk = new PlayerDesk();
        assertEquals(0, objectStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenDeskWithWrongResources_returnZero() {
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";
        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(CornerObject.MANUSCRIPT);
        }


        StarterCard card1 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        try {
            desk.addCard(card1, new Point(0, 0));
            desk.addCard(card2, new Point(1, 1));
            desk.addCard(card3, new Point(2, 2));
            desk.addCard(card4, new Point(3, 3));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        EnumMap<CornerObject, Integer> test = new EnumMap<>(CornerObject.class);
        test.put(CornerObject.MANUSCRIPT, 2);
        ObjectStrategy objectStrategy = new ObjectStrategy(test);
        assertEquals(6, objectStrategy.isSatisfied(desk));
    }

    @Test
    void isSatisfied_givenThreeCardsWithDifferentCornerObject_returnsOne() {
        PlayerDesk desk = new PlayerDesk();

        Corner[] corners;
        PointType pointType = PointType.CORNER;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";
        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(CornerObject.MANUSCRIPT);
        }

        corners[1] = new Corner(CornerObject.INKWELL);
        corners[2] = new Corner(CornerObject.QUILL);


        StarterCard card1 = new StarterCard(null, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card2 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card3 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);
        ResourceCard card4 = new ResourceCard(Resource.INSECT_KINGDOM, imageFrontPath, imageBackPath, 0, pointType, null, corners);

        try {
            desk.addCard(card1, new Point(0, 0));
            desk.addCard(card2, new Point(1, 1));
            desk.addCard(card3, new Point(2, 2));
            desk.addCard(card4, new Point(3, 3));
        } catch (PlaceNotAvailableException e) {
            throw new RuntimeException(e);
        }
        EnumMap<CornerObject, Integer> test = new EnumMap<>(CornerObject.class);
        test.put(CornerObject.MANUSCRIPT, 2);
        test.put(CornerObject.INKWELL, 1);
        test.put(CornerObject.QUILL, 1);
        ObjectStrategy objectStrategy = new ObjectStrategy(test);
        assertEquals(1, objectStrategy.isSatisfied(desk));

    }
}