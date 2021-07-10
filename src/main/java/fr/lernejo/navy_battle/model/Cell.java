package fr.lernejo.navy_battle.model;

public class Cell {
    private final int col;
    private final int lin;

    public Cell(int col, int lin) {
        this.col = col;
        this.lin = lin;
    }
    public Cell(String cell) throws Exception {
        if (null == cell || !cell.matches("[A-J]([1-9]|10)"))
            throw new Exception("Incorrect cell format");
        this.col = cell.charAt(0) - 'A' + 1;
        this.lin = cell.charAt(1) - 48;
    }

    public int getCol() {
        return col;
    }

    public int getLin() {
        return lin;
    }

    @Override
    public String toString() {
        char colChar = (char) (this.col + 'A' - 1);
        return "" + colChar + this.lin;
    }
}
