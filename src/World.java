import java.awt.*;
import java.util.ArrayList;

public class World {
    private static long lastTime = 0;
    public static World currentSetting = null;
    public ArrayList<Sprite> mc = new ArrayList<Sprite>();
    public static void update(){
        float deltaTime = (System.nanoTime() - lastTime) / 1000000000.0f;
        for (Sprite sprite : currentSetting.mc) {
            sprite.update(deltaTime);
        }
    }

    public static void render (Graphics g){
        for (Sprite sprite : currentSetting.mc) {
            sprite.render(g);
        }
    }
}
