package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerTest {

    @Test
    void isHidden_whenCreatedWithHiddenParameter() {
        Corner corner = new Corner(true);
        assertTrue(corner.isHidden());
    }

    @Test
    void isHidden_whenCreatedWithHiddenParameterAsFalse() {
        Corner corner = new Corner(false);
        assertFalse(corner.isHidden());
    }

    @Test
    void isHidden_setAsFalseWhenCreatedWithAnObject() {
        Corner corner = new Corner(CornerObject.MANUSCRIPT);
        assertFalse(corner.isHidden());
    }

    @Test
    void isHidden_setAsFalseWhenCreatedWithAResource() {
        Corner corner = new Corner(Resource.ANIMAL_KINGDOM);
        assertFalse(corner.isHidden());
    }


    @Test
    void getResource_whenCornerIsCreatedWithAResource() {
        Corner corner = new Corner(Resource.ANIMAL_KINGDOM);
        assertEquals(Resource.ANIMAL_KINGDOM, corner.getResource());
    }

    @Test
    void getObject_whenCornerIsCreatedWithAnObject() {
        Corner corner = new Corner(CornerObject.MANUSCRIPT);
        assertEquals(CornerObject.MANUSCRIPT, corner.getObject());
    }

    @Test
    void getResource_whenCornerIsCreatedWithoutAResource() {
        Corner corner = new Corner(true);
        assertNull(corner.getResource());
    }

    @Test
    void getObject_whenCornerIsCreatedWithoutAnObject() {
        Corner corner = new Corner(true);
        assertNull(corner.getObject());
    }


    @Test
    void resourceIsNotMutable() {
        Resource initialRes = Resource.ANIMAL_KINGDOM;
        Corner corner = new Corner(initialRes);
        Resource cornerRes;
        cornerRes = Resource.FUNGI_KINGDOM;
        assertEquals(initialRes, corner.getResource());
        assertNotEquals(cornerRes, corner.getResource());

    }


    @Test
    void equals_whenComparingCornerToAnotherCornerWithSameAttributes() {
        Corner corner1 = new Corner(Resource.ANIMAL_KINGDOM);
        Corner corner2 = new Corner(Resource.ANIMAL_KINGDOM);
        assertEquals(corner1, corner2);
    }

    @Test
    void equals_whenComparingCornerToAnotherCornerWithDifferentAttributes() {
        Corner corner1 = new Corner(Resource.ANIMAL_KINGDOM);
        Corner corner2 = new Corner(CornerObject.MANUSCRIPT);
        assertNotEquals(corner1, corner2);
    }


    @Test
    void isHidden_whenCornerIsCovered() {
        Corner corner = new Corner(false);
        corner.coverCorner();
        assertTrue(corner.isHidden());
    }

    @Test
    void copyConstructor_createsExactCopy() {
        Corner originalCorner = new Corner(Resource.ANIMAL_KINGDOM);
        Corner copiedCorner = new Corner(originalCorner);

        assertEquals(originalCorner, copiedCorner);
    }
}