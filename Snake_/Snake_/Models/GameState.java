package Models;

import java.util.ArrayList;

/**
 * Состояние игры: змея, еда, направление, флаг окончания.
 * Хранит все данные, необходимые для отрисовки и логики.
 */
public class GameState {
    public Cell head;
    public ArrayList<Cell> body;
    public Cell food;

    public int dx;
    public int dy;

    public boolean gameOver = false;

    public GameState() {
        head = new Cell(5, 5);
        body = new ArrayList<>();
        food = new Cell(10, 10);
        dx = 1;  // начальное движение вправо
        dy = 0;
    }

    public int getScore() {
        return body.size();
    }
}
