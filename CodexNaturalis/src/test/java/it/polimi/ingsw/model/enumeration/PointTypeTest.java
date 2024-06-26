package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTypeTest {
    @Test
    void get_returnsCorrectTextForGeneral() {
        assertEquals("General", PointType.GENERAL.get());
    }

    @Test
    void get_returnsCorrectTextForCorner() {
        assertEquals("Corner", PointType.CORNER.get());
    }

    @Test
    void get_returnsCorrectTextForManuscript() {
        assertEquals("Manuscript", PointType.MANUSCRIPT.get());
    }

    @Test
    void get_returnsCorrectTextForQuill() {
        assertEquals("Quill", PointType.QUILL.get());
    }

    @Test
    void get_returnsCorrectTextForInkwell() {
        assertEquals("Inkwell", PointType.INKWELL.get());
    }

    @Test
    void toString_returnsCorrectTextForGeneral() {
        assertEquals("General", PointType.GENERAL.toString());
    }

    @Test
    void toString_returnsCorrectTextForCorner() {
        assertEquals("Corner", PointType.CORNER.toString());
    }

    @Test
    void toString_returnsCorrectTextForManuscript() {
        assertEquals("Manuscript", PointType.MANUSCRIPT.toString());
    }

    @Test
    void toString_returnsCorrectTextForQuill() {
        assertEquals("Quill", PointType.QUILL.toString());
    }

    @Test
    void toString_returnsCorrectTextForInkwell() {
        assertEquals("Inkwell", PointType.INKWELL.toString());
    }
}