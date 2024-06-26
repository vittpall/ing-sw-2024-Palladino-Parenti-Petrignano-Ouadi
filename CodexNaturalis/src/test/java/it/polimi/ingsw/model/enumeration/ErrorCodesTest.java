package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ErrorCodesTest {

    @Test
    void get_returnsCorrectCodeForSuccess() {
        assertEquals(0, ErrorCodes.SUCCESS.get());
    }

    @Test
    void get_returnsCorrectCodeForRequirementsNotMet() {
        assertEquals(2, ErrorCodes.REQUIREMENTS_NOT_MET.get());
    }

    @Test
    void toString_returnsCorrectStringForSuccess() {
        assertEquals("0", ErrorCodes.SUCCESS.toString());
    }

    @Test
    void toString_returnsCorrectStringForRequirementsNotMet() {
        assertEquals("2", ErrorCodes.REQUIREMENTS_NOT_MET.toString());
    }
}
