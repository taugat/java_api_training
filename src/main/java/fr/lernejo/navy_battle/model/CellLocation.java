package fr.lernejo.navy_battle.model;

public class CellLocation {
    private final int col;
    private final int lin;

    public CellLocation(int col, int lin) {
        this.col = col;
        this.lin = lin;
    }
    public CellLocation(String cell) throws Exception {
        if (null == cell || !cell.matches("[A-J]([1-9]|10)"))
            throw new Exception("Incorrect cell format");
        this.col = cell.charAt(0) - 'A';
        this.lin = cell.charAt(1) - 49;
    }

    public int getCol() {
        return col;
    }

    public int getLin() {
        return lin;
    }

    @Override
    public String toString() {
        char colChar = (char) (this.col + 'A');
        return "" + colChar + (this.lin + 1);
    }
}
