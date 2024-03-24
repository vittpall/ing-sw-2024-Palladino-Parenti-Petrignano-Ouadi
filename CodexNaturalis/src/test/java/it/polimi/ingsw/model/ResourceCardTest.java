package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceCardTest {

    @Test
    void constructor_initializesSuperclassFieldsCorrectly() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        ResourceCard card = new ResourceCard(resourcesFront, resourceBack, pointType, points, imageFrontPath, imageBackPath);

        assertEquals(resourcesFront, card.getCardResourcesFront());
        assertEquals(resourceBack, card.getCardResourceBack());
        assertEquals(pointType, card.getPointType());
        assertEquals(points, card.getPoints());
        assertEquals(imageFrontPath, card.getImageFrontPath());
        assertEquals(imageBackPath, card.getImageBackPath());
    }
}