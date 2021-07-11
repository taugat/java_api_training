package fr.lernejo.navy_battle.model;

public class Ship {

    private final int size;
    private final boolean[] cellsShot;
    private final iShipListener shipListener;

    public interface iShipListener {
        void onDown();
    }

    public Ship(int size, iShipListener shipListener) {
        this.size = size;
        cellsShot = new boolean[size];
        this.shipListener = shipListener;
    }

    public void setOnTable(CellLocation topCellLocation, eOrientation orientation, Cell[][] table){
        for (int i = topCellLocation.getLin(), y= topCellLocation.getCol(), s = 0; s < size; s++) {
            int finalS = s;
            cellsShot[s] = false;
            Cell cCell = new Cell() {
                @Override
                public RoundStatus.eConsequence onHit() {
                    cellsShot[finalS] = true;
                    return isDown() ? RoundStatus.eConsequence.STUNK : RoundStatus.eConsequence.HIT;
                }
            };
            table[i][y] = cCell;
            switch (orientation){ case VERTICAL -> i++; case HORIZONTAL -> y++; }
        }
    }

    private boolean isDown() {
        boolean result = true;
        for (boolean cellShot : cellsShot
             ) {
            result &= cellShot;
        }
        if (result)
            shipListener.onDown();
        return result;
    }

    public int getSize() {
        return size;
    }

    public enum eOrientation{
        HORIZONTAL,
        VERTICAL
    }
}
