package Views;

import Models.Cell;
import Models.GameState;
import Patterns.GameController;
import java.awt.*;
import javax.swing.*;

/**
 * View — панель отрисовки игры.
 * Отвечает только за отображение, логику делегирует контроллеру.
 */
public class GamePanel extends JPanel {

    private final int width;
    private final int height;
    private final int cellSize = 40;

    private final GameController controller;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(10, 10, 30));
        setFocusable(true);

        // Контроллер получает callback для перерисовки
        this.controller = new GameController(width, height, cellSize, this::repaint);

        // Клавиатура → контроллер
        addKeyListener(controller);
    }

    public GameController getController() {
        return controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        GameState state = controller.getState();

        // Голова змеи
        g.setColor(new Color(0, 255, 255));
        g.fill3DRect(state.head.x * cellSize, state.head.y * cellSize,
                     cellSize, cellSize, true);

        // Тело змеи
        for (Cell part : state.body) {
            g.fill3DRect(part.x * cellSize, part.y * cellSize,
                         cellSize, cellSize, true);
        }

        // Еда
        g.setColor(new Color(255, 215, 0));
        g.fill3DRect(state.food.x * cellSize, state.food.y * cellSize,
                     cellSize, cellSize, true);

        // Счёт / Game Over
        g.setFont(new Font("Arial", Font.BOLD, 28));
        if (state.gameOver) {
            g.setColor(new Color(255, 50, 50));
            g.drawString("Game Over: " + state.getScore(), cellSize - 16, cellSize);
        } else {
            g.setColor(new Color(255, 255, 255));
            g.drawString("Score: " + state.getScore(), cellSize - 16, cellSize);
        }
    }
}
