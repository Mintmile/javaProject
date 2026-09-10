import Views.GamePanel;
import javax.swing.*;

/**
 * Точка входа приложения.
 */
public class App {
    public static void main(String[] args) {
        int width = 800;
        int height = width;

        JFrame frame = new JFrame("Snake");
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel game = new GamePanel(width, height);
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        game.requestFocus();
    }
}
