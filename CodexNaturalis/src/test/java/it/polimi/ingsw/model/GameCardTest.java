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
        GameCard card = new GameCard(new ArrayList<>(), null, null, 0, "", "") {
            // Empty implementation for abstract class
        };
        assertFalse(card.isPlayedFaceDown());
        card.flipCard();
        assertTrue(card.isPlayedFaceDown());
    }

    @Test
    void getCardResourcesFront_returnsCorrectResources() {
        ArrayList<Resource> resources = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        GameCard card = new GameCard(resources, null, null, 0, "", "") {
            // Empty implementation for abstract class
        };
        assertEquals(resources, card.getCardResourcesFront());
    }

    @Test
    void getCardResourceBack_returnsCorrectResource() {
        Resource resource = Resource.INSECT_KINGDOM;
        GameCard card = new GameCard(new ArrayList<>(), resource, null, 0, "", "") {
            // Empty implementation for abstract class
        };
        assertEquals(resource, card.getCardResourceBack());
    }

    @Test
    void getPointType_returnsCorrectPointType() {
        PointType pointType = PointType.MANUSCRIPT;
        GameCard card = new GameCard(new ArrayList<>(), null, pointType, 0, "", "") {
            // Empty implementation for abstract class
        };
        assertEquals(pointType, card.getPointType());
    }

    @Test
    void getImageFrontPath_returnsCorrectPath() {
        String path = "path/to/front/image";
        GameCard card = new GameCard(new ArrayList<>(), null, null, 0, path, "") {
            // Empty implementation for abstract class
        };
        assertEquals(path, card.getImageFrontPath());
    }

    @Test
    void getImageBackPath_returnsCorrectPath() {
        String path = "path/to/back/image";
        GameCard card = new GameCard(new ArrayList<>(), null, null, 0, "", path) {
            // Empty implementation for abstract class
        };
        assertEquals(path, card.getImageBackPath());
    }
}