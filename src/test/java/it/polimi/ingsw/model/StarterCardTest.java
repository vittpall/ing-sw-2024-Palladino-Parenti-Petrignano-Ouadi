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
        Corner[] corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

        StarterCard card = new StarterCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);


        assertEquals(resourcesFront, card.getBackSideResources());
        assertEquals(resourceBack, card.getBackSideResource());
        assertEquals(pointType, card.getPointType());
        assertEquals(points, card.getPoints());
        assertEquals(imageFrontPath, card.getImageFrontPath());
        assertEquals(imageBackPath, card.getImageBackPath());
    }
}
