package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GoldCardTest {

    @Test
    void getRequirements_returnsCorrectRequirements() {
        ArrayList<Resource> requirements = new ArrayList<>(Arrays.asList(Resource.ANIMAL_KINGDOM, Resource.FUNGI_KINGDOM));
        GoldCard card = new GoldCard(new ArrayList<>(), null, null, 0, "", "", requirements);
        assertEquals(requirements, card.getRequirements());
    }

    @Test
    void getRequirements_returnsEmptyForNoRequirements() {
        ArrayList<Resource> requirements = new ArrayList<>();
        GoldCard card = new GoldCard(new ArrayList<>(), null, null, 0, "", "", requirements);
        assertEquals(requirements, card.getRequirements());
    }

    @Test
    void getRequirements_returnsNotNullForNullRequirements() {
        GoldCard card = new GoldCard(new ArrayList<>(), null, null, 0, "", "", null);
        assertNotNull(card.getRequirements());
    }
}