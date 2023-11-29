import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {
    public static void main(String[] args) throws IOException {
        Renderer.init();

        Renderer.loadImage("src/tsukasanui.jpg");
    }
}
