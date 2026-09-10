package Services;

import Models.Cell;
import Models.GameState;
import java.util.Random;

/**
 * Сервис размещения еды на поле.
 */
public class FoodService {
    private final Random random = new Random();
    private final int cols;
    private final int rows;

    public FoodService(int width, int height, int cellSize) {
        this.cols = width / cellSize;
        this.rows = height / cellSize;
    }

    public void placeFood(GameState state) {
        state.food.x = random.nextInt(cols);
        state.food.y = random.nextInt(rows);
    }
}
