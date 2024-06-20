package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;
import static org.junit.jupiter.api.Assertions.*;

class CornerTest {

    @Test
    void isHidden_whenCreatedWithHiddenParameter() {
        Corner corner= new Corner(true);
        assertTrue(corner.isHidden());
    }
    @Test
    void isHidden_whenCreatedWithHiddenParameterAsFalse() {
        Corner corner= new Corner(false);
        assertFalse(corner.isHidden());
    }

    @Test
    void isHidden_setAsFalseWhenCreatedWithAnObject(){
        Corner corner= new Corner(CornerObject.MANUSCRIPT);
        assertFalse(corner.isHidden());
    }
    @Test
    void isHidden_setAsFalseWhenCreatedWithAResource(){
        Corner corner= new Corner(Resource.ANIMAL_KINGDOM);
        assertFalse(corner.isHidden());
    }

    @Test
    void getResource() {
        for(Resource res : Resource.values()){
            Corner corner = new Corner(res);
            assertEquals(res, corner.getResource());
            assertNull(corner.getObject());
        }
    }

    @Test
    void getObject() {
        for(CornerObject obj : CornerObject.values()){
            Corner corner = new Corner(obj);
            assertEquals(obj, corner.getObject());
            assertNull(corner.getResource());
        }
    }

    @Test
    void resourceIsNotMutable(){
        Resource initialRes = Resource.ANIMAL_KINGDOM;
        Corner corner = new Corner(initialRes);
        Resource cornerRes;
        cornerRes = Resource.FUNGI_KINGDOM;
        assertEquals(initialRes, corner.getResource());
        assertNotEquals(cornerRes, corner.getResource());

    }
    @Test
    void objectIsNotMutable(){
        CornerObject initialObj = CornerObject.INKWELL;
        Corner corner = new Corner(initialObj);
        CornerObject cornerObj;
        cornerObj = CornerObject.QUILL;
        assertEquals(initialObj, corner.getObject());
        assertNotEquals(cornerObj, corner.getObject());
    }

    @Test
    void coverCorner() {
        Corner corner=new Corner(false);
        corner.coverCorner();
        assertTrue(corner.isHidden());
    }
}