import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    private Controller controller;

    MouseListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        controller.handleMousePressed(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}