import java.awt.*;
import java.awt.image.BufferStrategy;

public class View extends Canvas {
    private int width, height;

    View(int width, int height){
        this.width = width;
        this.height = height;
        createWindow();
    }

    void render(Model model) {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);

        model.render(graphics);
        graphics.setColor(Color.WHITE);

        graphics.dispose();
        bufferStrategy.show();
    }

    private void createWindow() {
        new Window(width, height, "Reversi", this);
    }
}
