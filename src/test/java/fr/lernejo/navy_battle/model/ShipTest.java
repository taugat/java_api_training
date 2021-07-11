package fr.lernejo.navy_battle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void setOnTable() {
        Cell[][] cells = new Cell[10][10];
        final boolean[] shipDestroy = new boolean[1];
        Ship ship = new Ship(1,() -> shipDestroy[0] = true);
        ship.setOnTable(new CellLocation(0,0), Ship.eOrientation.HORIZONTAL, cells);
        cells[0][0].onHit();

        assertTrue(shipDestroy[0]);
    }
}
