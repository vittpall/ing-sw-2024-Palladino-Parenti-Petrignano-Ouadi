package it.polimi.ingsw.model.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenColorTest {

    @Test
    void get_returnsCorrectTextForRed() {
        assertEquals("Red", TokenColor.RED.get());
    }

    @Test
    void get_returnsCorrectTextForBlue() {
        assertEquals("Blue", TokenColor.BLUE.get());
    }

    @Test
    void get_returnsCorrectTextForYellow() {
        assertEquals("Yellow", TokenColor.YELLOW.get());
    }

    @Test
    void get_returnsCorrectTextForGreen() {
        assertEquals("Green", TokenColor.GREEN.get());
    }

    @Test
    void getImageName_returnsCorrectImageNameForRed() {
        assertEquals("CODEX_pion_rouge.png", TokenColor.RED.getImageName());
    }

    @Test
    void getImageName_returnsCorrectImageNameForBlue() {
        assertEquals("CODEX_pion_bleu.png", TokenColor.BLUE.getImageName());
    }

    @Test
    void getImageName_returnsCorrectImageNameForYellow() {
        assertEquals("CODEX_pion_jaune.png", TokenColor.YELLOW.getImageName());
    }

    @Test
    void getImageName_returnsCorrectImageNameForGreen() {
        assertEquals("CODEX_pion_vert.png", TokenColor.GREEN.getImageName());
    }

    @Test
    void toString_returnsCorrectTextForRed() {
        assertEquals("Red", TokenColor.RED.toString());
    }

    @Test
    void toString_returnsCorrectTextForBlue() {
        assertEquals("Blue", TokenColor.BLUE.toString());
    }

    @Test
    void toString_returnsCorrectTextForYellow() {
        assertEquals("Yellow", TokenColor.YELLOW.toString());
    }

    @Test
    void toString_returnsCorrectTextForGreen() {
        assertEquals("Green", TokenColor.GREEN.toString());
    }

    @Test
    void getColorValueANSII_returnsCorrectValueForRed() {
        assertEquals("\033[0;31m", TokenColor.RED.getColorValueANSII());
    }

    @Test
    void getColorValueANSII_returnsCorrectValueForBlue() {
        assertEquals("\033[0;34m", TokenColor.BLUE.getColorValueANSII());
    }

    @Test
    void getColorValueANSII_returnsCorrectValueForYellow() {
        assertEquals("\033[0;33m", TokenColor.YELLOW.getColorValueANSII());
    }

    @Test
    void getColorValueANSII_returnsCorrectValueForGreen() {
        assertEquals("\033[0;32m", TokenColor.GREEN.getColorValueANSII());
    }
}