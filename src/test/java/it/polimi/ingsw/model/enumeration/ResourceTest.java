package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void get_returnsCorrectTextForPlantKingdom() {
        assertEquals("Plant Kingdom", Resource.PLANT_KINGDOM.get());
    }

    @Test
    void get_returnsCorrectTextForAnimalKingdom() {
        assertEquals("Animal Kingdom", Resource.ANIMAL_KINGDOM.get());
    }

    @Test
    void get_returnsCorrectTextForFungiKingdom() {
        assertEquals("Fungi Kingdom", Resource.FUNGI_KINGDOM.get());
    }

    @Test
    void get_returnsCorrectTextForInsectKingdom() {
        assertEquals("Insect Kingdom", Resource.INSECT_KINGDOM.get());
    }

    @Test
    void toString_returnsCorrectTextForPlantKingdom() {
        assertEquals("Plant Kingdom", Resource.PLANT_KINGDOM.toString());
    }

    @Test
    void toString_returnsCorrectTextForAnimalKingdom() {
        assertEquals("Animal Kingdom", Resource.ANIMAL_KINGDOM.toString());
    }

    @Test
    void toString_returnsCorrectTextForFungiKingdom() {
        assertEquals("Fungi Kingdom", Resource.FUNGI_KINGDOM.toString());
    }

    @Test
    void toString_returnsCorrectTextForInsectKingdom() {
        assertEquals("Insect Kingdom", Resource.INSECT_KINGDOM.toString());
    }
}