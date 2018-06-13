import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Image {

    private BufferedImage bufferedImage;

    Image(String path) {
        this.bufferedImage = loadImage(path);
    }

    /**
     * Loads an image from a specified file path.
     *
     * @param path Specified file path
     * @return Loaded image
     */
    private BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
            System.out.print("Loading image failed: " + e.getMessage());
            System.exit(1);
        }
        return image;
    }

    BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
