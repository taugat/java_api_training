package fr.lernejo.navy_battle.controllers;

import fr.lernejo.navy_battle.model.Cell;
import fr.lernejo.navy_battle.model.CellLocation;
import fr.lernejo.navy_battle.model.RoundStatus;
import fr.lernejo.navy_battle.model.Ship;

import java.util.UUID;

public class GameController {

    private final UUID adversaryID;
    private final int seaSize = 10;
    private final Cell[][] mySea = new Cell[seaSize][seaSize];
    private final CellStatus[][] adversarySea = new CellStatus[seaSize][seaSize];
    private final boolean[] shipsLeft;
    private final Ship[] ships = new Ship[5];
    private final int nextTarget[] = {0,0};
    public enum CellStatus
    {
        HIT,
        FAIL,
        NOT_HIT
    }
    public GameController(UUID adversaryID) {
        this.adversaryID = adversaryID;
        for (int i = 0; i < seaSize; i++) {
            for (int j = 0; j < seaSize; j++) {
                mySea[i][j] = new Cell();
                adversarySea[i][j] = CellStatus.NOT_HIT;
            }
        }
        shipsLeft = new boolean[]{true, true, true, true, true};
        ships[0] = new Ship(5, () -> {shipsLeft[0] = false;});
        ships[1] = new Ship(2, () -> {shipsLeft[1] = false;});
        ships[2] = new Ship(3, () -> {shipsLeft[2] = false;});
        ships[3] = new Ship(3, () -> {shipsLeft[3] = false;});
        ships[4] = new Ship(2, () -> {shipsLeft[4] = false;});
        setUpBoat();
    }
    public void setUpBoat(){
        for (int i = 0; i < ships.length; i++) {
            ships[i].setOnTable(new CellLocation(i,0), Ship.eOrientation.VERTICAL, mySea);
        }
    }
    public void endGame(boolean b) {
        System.out.println("Game Finish " + (b ? "WIN":"LOSE"));
    }
    public RoundStatus getRoundStatus(CellLocation cellLocation) {
        RoundStatus.eConsequence consequence = mySea[cellLocation.getLin()][cellLocation.getCol()].onHit();
        boolean shipLeft = isShipLeft();
        return new RoundStatus(consequence, shipLeft);
    }
    public boolean isShipLeft() {
        boolean result = false;
        for (boolean shipLeft:shipsLeft) {
            result |= shipLeft;
        }
        return result;
    }
    public CellLocation doFire() {
        CellLocation cellLocation = null;
        try {
            cellLocation = new CellLocation(nextTarget[0],nextTarget[1]);
            if (nextTarget[0] == 9) {
                nextTarget[0] = 0;
                nextTarget[1] += 1;
            } else
                nextTarget[0] += 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellLocation;
    }
    public void resultFire(RoundStatus.eConsequence consequence, CellLocation cellLocation) {
        adversarySea[cellLocation.getLin()][cellLocation.getCol()] = switch (consequence){
            case MISS -> CellStatus.FAIL;
            case STUNK, HIT -> CellStatus.HIT;
        };
    }
    public CellStatus[][] getAdversarySea() {
        return adversarySea;
    }
    public Cell[][] getMySea() {
        return mySea;
    }
}
