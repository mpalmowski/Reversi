import javax.swing.*;
import java.awt.*;

/**
 * Main window of the application
 */
class Window extends Canvas {
    JFrame frame;

    Window(int width, int height, String title, View view) {
        frame = new JFrame(title);
        Dimension dimension = new Dimension(0, 0);

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(view);
        frame.setVisible(true);

        Insets windowInsets = frame.getInsets();
        int win_height = height + windowInsets.top + windowInsets.bottom;
        int win_width = width + windowInsets.left + windowInsets.right;
        dimension = new Dimension(win_width, win_height);

        frame.setPreferredSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setMinimumSize(dimension);
    }

    public JFrame getFrame() {
        return frame;
    }
}