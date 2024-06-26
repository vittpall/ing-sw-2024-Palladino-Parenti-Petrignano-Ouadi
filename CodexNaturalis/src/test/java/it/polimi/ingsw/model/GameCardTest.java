package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.view.tui.CardPrinter;
import it.polimi.ingsw.view.tui.PrintContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameCardTest {

    private Corner[] corners;

    @BeforeEach
    void setUp() {
        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

    }

    @Test
    void flipCard_changesCardOrientation() {
        GameCard card = new GameCard(null, null, null, 0, null, new ArrayList<>(), corners) {
            @Override
            protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

            }
            // Empty implementation for abstract class
        };
        assertFalse(card.isPlayedFaceDown());
        card.flipCard();
        assertTrue(card.isPlayedFaceDown());
    }

    @Test
    void getCardResourcesFront_returnsCorrectResources() {
        ArrayList<Resource> resources = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        GameCard card = new GameCard(Resource.ANIMAL_KINGDOM, null, null, 0, PointType.CORNER, resources, corners) {
            @Override
            protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

            }
            // Empty implementation for abstract class
        };
        assertEquals(resources, card.getBackSideResources());
    }

    @Test
    void getCardResourceBack_returnsCorrectResource() {
        Resource resource = Resource.INSECT_KINGDOM;
        GameCard card = new GameCard(resource, null, null, 0, null, new ArrayList<>(), corners) {
            @Override
            protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

            }
            // Empty implementation for abstract class
        };
        assertEquals(resource, card.getBackSideResource());
    }

    @Test
    void getPointType_returnsCorrectPointType() {
        PointType pointType = PointType.MANUSCRIPT;
        GameCard card = new GameCard(null, null, null, 0, pointType, new ArrayList<>(), corners) {
            @Override
            protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

            }
            // Empty implementation for abstract class
        };

        assertEquals(pointType, card.getPointType());
    }

    @Test
    void getImageFrontPath_returnsCorrectPath() {
        String path = "path/to/front/image";
        GameCard card = new GameCard(null, path, null, 0, null, new ArrayList<>(), corners) {
            @Override
            protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

            }
            // Empty implementation for abstract class
        };

        assertEquals(path, card.getImageFrontPath());
    }

    @Test
    void getImageBackPath_returnsCorrectPath() {
        String path = "path/to/back/image";
        GameCard card = new GameCard(null, null, path, 0, null, new ArrayList<>(), corners) {
            @Override
            protected void printCardDetails(PrintContext context, CardPrinter.Color colorBackground, boolean faceDown) {

            }
            // Empty implementation for abstract class
        };
        assertEquals(path, card.getImageBackPath());
    }
}