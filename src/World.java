import java.awt.*;
import java.awt.image.BufferedImage;
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

    public void render (Graphics g){
        g.drawImage(bg,0,0,null);
    }
}
