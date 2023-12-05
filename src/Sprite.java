import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class Sprite {
    public float posX = 0;
    public float posY = 0;

    public BufferedImage Image = null;

    public Sprite (float posX, float posY, BufferedImage image){
        this.posX = posX;
        this.posY = posY;
        Image = image;
    }

    public void update (float deltaTime){
        //
    }
    public void setImage(BufferedImage im){
        Image = im;
    }
    public void render (Graphics g) {
        g.drawImage(Image, (int)posX,(int)posY, null);
    }
    public void moveUp(){
        posY--;
        render(Image.getGraphics());
    }
    public void moveDown(){
        posY++;
        render(Image.getGraphics());
    }
    public void moveRight(){
        posX++;
        render(Image.getGraphics());
    }
    public void moveLeft() {
        posX--;
        render(Image.getGraphics());
    }
}
