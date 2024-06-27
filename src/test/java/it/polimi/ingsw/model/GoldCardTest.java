package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {
    private Corner[] corners;
    @BeforeEach
    void setUp() {
        corners = new Corner[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Corner(false);
        }

    }

    @Test
    void getRequirements_returnsCorrectRequirements() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.ANIMAL_KINGDOM, 1);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        GoldCard card = new GoldCard(null, null, null, 0, null, null, corners, requirements);
        assertEquals(requirements, card.getRequirements());
    }

    @Test
    void getRequirements_returnsEmptyForNoRequirements() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        GoldCard card = new GoldCard(null, null, null, 0, null, null, corners, requirements);
        assertEquals(requirements, card.getRequirements());
    }

    @Test
    void getRequirements_returnsNotNullForNullRequirements() {
        GoldCard card = new GoldCard(null, null, null, 0, null, null, corners, null);

        assertNotNull(card.getRequirements());
    }

    @Test
    void getEquals_returnSimilarGoldCard() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.ANIMAL_KINGDOM, 1);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        GoldCard card = new GoldCard(null, null, null, 0, null, null, corners, requirements);
        GoldCard card2 = new GoldCard(null, null, null, 0, null, null, corners, requirements);
        assertEquals(card, card2);
    }

    @Test
    void equals_whenComparingCornerToAnotherCornerWithDifferentAttributes() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.ANIMAL_KINGDOM, 1);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        GoldCard card = new GoldCard(Resource.FUNGI_KINGDOM, "image/path", "image/path", 0, PointType.GENERAL, new ArrayList<>(), corners, requirements);
        GoldCard card2 = new GoldCard(Resource.FUNGI_KINGDOM, "image/path", "image/path", 0, PointType.GENERAL, new ArrayList<>(), corners, new EnumMap<>(Resource.class));
        assertNotEquals(card, card2);
    }

    @Test
    void notEquals_whenComparingCornerToAnotherObject() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.ANIMAL_KINGDOM, 1);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        GoldCard card = new GoldCard(null, null, null, 0, null, null, corners, requirements);
        assertNotEquals(card, new Object());
    }
}