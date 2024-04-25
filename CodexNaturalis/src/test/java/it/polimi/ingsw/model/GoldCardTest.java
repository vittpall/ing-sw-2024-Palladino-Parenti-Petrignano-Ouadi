package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GoldCardTest {

    @Test
    void getRequirements_returnsCorrectRequirements() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        requirements.put(Resource.ANIMAL_KINGDOM, 1);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        GoldCard card = new GoldCard(null, null, null, 0, null, null, null, requirements);
        assertEquals(requirements, card.getRequirements());
    }

    @Test
    void getRequirements_returnsEmptyForNoRequirements() {
        EnumMap<Resource, Integer> requirements = new EnumMap<>(Resource.class);
        GoldCard card = new GoldCard(null, null, null, 0, null, null, null, requirements);
        assertEquals(requirements, card.getRequirements());
    }

    @Test
    void getRequirements_returnsNotNullForNullRequirements() {
        GoldCard card = new GoldCard(null, null, null, 0, null, null, null, new EnumMap<>(Resource.class));

        assertNotNull(card.getRequirements());
    }
}