import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class World {
    private static long lastTime = 0;
    public static World currentSetting = null;
    private BufferedImage bg;
    public ArrayList<Sprite> mc = new ArrayList<Sprite>();

    public World(BufferedImage back){
            bg = back;
    }

    public void setBg(BufferedImage newbg){
        bg = newbg;}
    public static void update(){
        float deltaTime = (System.nanoTime() - lastTime) / 1000000000.0f;
        for (Sprite sprite : currentSetting.mc) {
            sprite.update(deltaTime);
        }
    }
    public void render (Graphics g){
        g.drawImage(bg,0,0,null);
    }
}
