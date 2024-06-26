package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CornerObjectTest {

    @Test
    void get_returnsCorrectTextForManuscript() {
        assertEquals("Manuscript", CornerObject.MANUSCRIPT.get());
    }

    @Test
    void get_returnsCorrectTextForQuill() {
        assertEquals("Quill", CornerObject.QUILL.get());
    }

    @Test
    void get_returnsCorrectTextForInkwell() {
        assertEquals("Inkwell", CornerObject.INKWELL.get());
    }

    @Test
    void toString_returnsCorrectTextForManuscript() {
        assertEquals("Manuscript", CornerObject.MANUSCRIPT.toString());
    }

    @Test
    void toString_returnsCorrectTextForQuill() {
        assertEquals("Quill", CornerObject.QUILL.toString());
    }

    @Test
    void toString_returnsCorrectTextForInkwell() {
        assertEquals("Inkwell", CornerObject.INKWELL.toString());
    }
}