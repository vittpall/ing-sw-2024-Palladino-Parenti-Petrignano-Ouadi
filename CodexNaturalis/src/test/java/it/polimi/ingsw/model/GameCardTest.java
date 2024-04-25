package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameCardTest {

    @Test
    void flipCard_changesCardOrientation() {
        GameCard card = new GameCard(null, null, null, 0, null, new ArrayList<>(), null) {
            // Empty implementation for abstract class
        };
        assertFalse(card.isPlayedFaceDown());
        card.flipCard();
        assertTrue(card.isPlayedFaceDown());
    }

    @Test
    void getCardResourcesFront_returnsCorrectResources() {
        ArrayList<Resource> resources = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        GameCard card = new GameCard(Resource.ANIMAL_KINGDOM, null, null, 0, PointType.CORNER, resources, null) {
            // Empty implementation for abstract class
        };
        assertEquals(resources, card.getFrontSideResources());
    }

    @Test
    void getCardResourceBack_returnsCorrectResource() {
        Resource resource = Resource.INSECT_KINGDOM;
        GameCard card = new GameCard(resource, null, null, 0, null, new ArrayList<>(), null) {
            // Empty implementation for abstract class
        };
        assertEquals(resource, card.getbackSideResource());
    }

    @Test
    void getPointType_returnsCorrectPointType() {
        PointType pointType = PointType.MANUSCRIPT;
        GameCard card = new GameCard(null, null, null, 0, pointType, new ArrayList<>(), null) {
            // Empty implementation for abstract class
        };

        assertEquals(pointType, card.getPointType());
    }

    @Test
    void getImageFrontPath_returnsCorrectPath() {
        String path = "path/to/front/image";
        GameCard card = new GameCard(null, path, null, 0, null, new ArrayList<>(), null) {
            // Empty implementation for abstract class
        };

        assertEquals(path, card.getImageFrontPath());
    }

    @Test
    void getImageBackPath_returnsCorrectPath() {
        String path = "path/to/back/image";
        GameCard card = new GameCard(null, null, path, 0, null, new ArrayList<>(), null) {
            // Empty implementation for abstract class
        };
        assertEquals(path, card.getImageBackPath());
    }
}