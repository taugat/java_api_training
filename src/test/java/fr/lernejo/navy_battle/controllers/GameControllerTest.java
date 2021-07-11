package fr.lernejo.navy_battle.controllers;

import fr.lernejo.navy_battle.model.CellLocation;
import fr.lernejo.navy_battle.model.RoundStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void getRoundStatus() {
        GameController gameController = new GameController(UUID.randomUUID());
        RoundStatus roundStatus = gameController.getRoundStatus(new CellLocation(0,0));

        assertEquals(roundStatus.getConsequence(), RoundStatus.eConsequence.HIT);
        assertTrue(roundStatus.isShipLeft());
    }

    @Test
    void destroyAShip() {
        GameController gameController = new GameController(UUID.randomUUID());
        assertEquals(gameController.getRoundStatus(new CellLocation(1,0)).getConsequence(), RoundStatus.eConsequence.HIT);
        assertEquals(gameController.getRoundStatus(new CellLocation(1,1)).getConsequence(), RoundStatus.eConsequence.STUNK);
    }

    @Test
    void destroyAllShip() {
        GameController gameController = new GameController(UUID.randomUUID());
        RoundStatus lastRound = null;
        for (int i = 0; i < 10 && gameController.isShipLeft(); i++) {
            for (int j = 0; j < 10 && gameController.isShipLeft(); j++) {
                lastRound = gameController.getRoundStatus(new CellLocation(i,j));
            }
        }
        assert lastRound != null;
        assertEquals(lastRound.getConsequence(), RoundStatus.eConsequence.STUNK);
        assertFalse(lastRound.isShipLeft());
    }
    @Test
    void resultFire() {
        GameController gameController = new GameController(UUID.randomUUID());
        gameController.resultFire(RoundStatus.eConsequence.HIT,new CellLocation(0,0));
        assertEquals(gameController.getAdversarySea()[0][0], GameController.CellStatus.HIT);
    }
}
