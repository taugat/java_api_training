package fr.lernejo.navy_battle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellLocationTest {

    @Test
    void testFromString() throws Exception {
        String cellString = "B7";
        CellLocation cellLocation = new CellLocation(cellString);
        assertEquals(cellLocation.getCol(), 1);
        assertEquals(cellLocation.getLin(),6);
    }
    @Test
    void testToString() {
        CellLocation cellLocation = new CellLocation(1,6);
        assertEquals(cellLocation.toString(), "B7");
    }
    @Test
    void testIncorrectPattern(){
        assertThrows(Exception.class, () -> new CellLocation("Z0"));
    }
}
