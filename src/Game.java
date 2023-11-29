import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game {
    public static void main(String[] args){
        Renderer.init();

        try {
            Renderer.loadImage("Resources/image-removebg-preview.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
