package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StarterCardTest {

    @Test
    void constructor_initializesSuperclassFieldsCorrectly() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        StarterCard card = new StarterCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, null);


        assertEquals(resourcesFront, card.getFrontSideResources());
        assertEquals(resourceBack, card.getbackSideResource());
        assertEquals(pointType, card.getPointType());
        assertEquals(points, card.getPoints());
        assertEquals(imageFrontPath, card.getImageFrontPath());
        assertEquals(imageBackPath, card.getImageBackPath());
    }
}
