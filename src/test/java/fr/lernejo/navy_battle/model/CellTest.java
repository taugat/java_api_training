package fr.lernejo.navy_battle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testFromString() throws Exception {
        String cellString = "B7";
        Cell cell = new Cell(cellString);
        assertEquals(cell.getCol(), 2);
        assertEquals(cell.getLin(),7);
    }
    @Test
    void testToString() {
        Cell cell = new Cell(2,7);
        assertEquals(cell.toString(), "B7");
    }
    @Test
    void testIncorrectPattern(){
        assertThrows(Exception.class, () -> new Cell("Z0"));
    }
}
