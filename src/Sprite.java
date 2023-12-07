import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class Sprite {
    private float posX = 0;
    private float posY = 0;
    JFrame frame = Renderer.getFrame(); //so that sprite class can access the frame and see its size n stuff. ig.
    private BufferedImage Image = null;
    public Sprite (float posX, float posY, BufferedImage image){
        this.posX = posX;
        this.posY = posY;
        Image = image;
    }

    public void update (BufferedImage img){
        Image = img;
        render(img.getGraphics());
    }
    public void setImage(BufferedImage im){
        Image = im;
    }
    public void render (Graphics g) {
        g.drawImage(Image, (int)posX,(int)posY, null);
    }
    public void moveUp(){
        clear();
        if (!(posY-1<0)) {
            posY -= 1;
            update(Image);
        }
    }
    public void moveDown() {
        clear();
        if (!(posY+1>frame.getHeight())) {
            posY += 1;
            update(Image);
        }
    }
    public void moveRight(){
        clear();
        if (!(posX+1>frame.getWidth())){
            posX += 1;
            update(Image);
        }
    }
    public void moveLeft() {
        clear();
        if (!(posX-1<0)){
            posX -= 1;
            update(Image);
        }
    }
    public void clear(){
        Image.getGraphics().drawImage(null,(int)posX,(int)posY,null);
    }
}
