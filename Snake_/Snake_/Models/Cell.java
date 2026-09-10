package Models;

/**
 * Модель клетки игрового поля (голова, части тела, еда).
 */
public class Cell {
    public int x;
    public int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Cell other) {
        return this.x == other.x && this.y == other.y;
    }
}
