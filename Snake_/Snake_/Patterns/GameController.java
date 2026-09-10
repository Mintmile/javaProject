package Patterns;

import Models.Cell;
import Models.GameState;
import Services.FoodService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

/**
 * Контроллер / ViewModel игры.
 * Управляет таймером, движением, столкновениями и обработкой клавиш.
 */
public class GameController implements ActionListener, KeyListener {

    private final GameState state;
    private final FoodService foodService;
    private final Timer timer;
    private final Runnable onUpdate;   // callback для перерисовки View

    private final int width;
    private final int height;
    private final int cellSize;

    public GameController(int width, int height, int cellSize, Runnable onUpdate) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.onUpdate = onUpdate;

        this.state = new GameState();
        this.foodService = new FoodService(width, height, cellSize);
        this.foodService.placeFood(state);

        // Таймер: шаг змеи каждые 100 мс
        this.timer = new Timer(100, this);
        this.timer.start();
    }

    public GameState getState() {
        return state;
    }

    public void stop() {
        timer.stop();
    }

    // ==================== Игровая логика ====================

    private void move() {
        // Поедание еды
        if (collision(state.head, state.food)) {
            state.body.add(new Cell(state.food.x, state.food.y));
            foodService.placeFood(state);
        }

        // Движение тела
        for (int i = state.body.size() - 1; i >= 0; i--) {
            Cell part = state.body.get(i);
            if (i == 0) {
                part.x = state.head.x;
                part.y = state.head.y;
            } else {
                Cell prev = state.body.get(i - 1);
                part.x = prev.x;
                part.y = prev.y;
            }
        }

        // Движение головы
        state.head.x += state.dx;
        state.head.y += state.dy;

        // Столкновение с телом
        for (Cell part : state.body) {
            if (collision(state.head, part)) {
                state.gameOver = true;
            }
        }

        // Выход за границы
        if (state.head.x * cellSize < 0 || state.head.x * cellSize >= width ||
            state.head.y * cellSize < 0 || state.head.y * cellSize >= height) {
            state.gameOver = true;
        }
    }

    private boolean collision(Cell a, Cell b) {
        return a.x == b.x && a.y == b.y;
    }

    // ==================== ActionListener (таймер) ====================

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!state.gameOver) {
            move();
        } else {
            timer.stop();
        }
        onUpdate.run();   // просим View перерисоваться
    }

    // ==================== KeyListener ====================

    @Override
    public void keyPressed(KeyEvent e) {
        if (state.gameOver) return;

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP && state.dy != 1) {
            state.dx = 0;
            state.dy = -1;
        } else if (code == KeyEvent.VK_DOWN && state.dy != -1) {
            state.dx = 0;
            state.dy = 1;
        } else if (code == KeyEvent.VK_LEFT && state.dx != 1) {
            state.dx = -1;
            state.dy = 0;
        } else if (code == KeyEvent.VK_RIGHT && state.dx != -1) {
            state.dx = 1;
            state.dy = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
