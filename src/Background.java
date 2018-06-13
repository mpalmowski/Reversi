import java.awt.*;

class Background {

    private int width =0, height = 0;
    private Image image;

    Background(Image image, int width, int height){
        this.width = width;
        this.height = height;
        this.image = image;
    }

    void render(Graphics graphics){
        Double x = 0.0;
        Double y = 0.0;
        graphics.drawImage(image.getBufferedImage(), x.intValue(), y.intValue(), width, height, null);
    }

}
