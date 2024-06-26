package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ResourceCardTest {

    private Corner[] corners;

    @BeforeEach
    void setUp() {
        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

    }

    @Test
    void constructor_initializesSuperclassFieldsCorrectly() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        ResourceCard card = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);

        assertEquals(resourcesFront, card.getBackSideResources());
        assertEquals(resourceBack, card.getBackSideResource());
        assertEquals(pointType, card.getPointType());
        assertEquals(points, card.getPoints());
        assertEquals(imageFrontPath, card.getImageFrontPath());
        assertEquals(imageBackPath, card.getImageBackPath());
    }

    @Test
    void equals_returnsTrue_whenComparingACardWithItself() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        ResourceCard card = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);

        assertEquals(card, card);
    }

    @Test
    void equals_returnsFalse_whenComparingWithNull() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        ResourceCard card = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);

        assertNotEquals(card, null);
    }

    @Test
    void equals_returnsFalse_whenComparingWithDifferentObject() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        ResourceCard card = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);

        assertNotEquals(card, new Object());
    }

    @Test
    void equals_returnsTrue_whenComparingTwoCardsWithSameFields() {
        ArrayList<Resource> resourcesFront = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        Resource resourceBack = Resource.INSECT_KINGDOM;
        PointType pointType = PointType.CORNER;
        int points = 5;
        String imageFrontPath = "path/to/front/image";
        String imageBackPath = "path/to/back/image";

        ResourceCard card1 = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);
        ResourceCard card2 = new ResourceCard(resourceBack, imageFrontPath, imageBackPath, points, pointType, resourcesFront, corners);

        assertEquals(card1, card2);
    }
}