package fr.lernejo.navy_battle.model;

public class GameController {
    public CellStatus getCellStatus(Cell cell) {
        return new CellStatus(CellStatus.eConsequence.MISS,true);
    }

    public Cell doFire() {
        try {
            return new Cell("B7");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void resultFire(CellStatus.eConsequence consequence, Cell cell) {

    }
    public void endGame(boolean b) {

    }


}
