import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;

public class Sprite {
    private int posX = 0;
    private int posY = 0;
    JFrame frame = Renderer.frame; //so that sprite class can access the frame and see its size n stuff. ig.
    private BufferedImage img = null;
    public Sprite (int posX, int posY, BufferedImage image){
        this.posX = posX;
        this.posY = posY;
        img = image;
    }

    public void update (BufferedImage img){
        // idk
    }
    public void setImage(BufferedImage im){
        img = im;
    }
    public void render (Graphics g) {
        g.drawImage(img, posX,posY, null);
    }
    public void moveUp(){
        if (!(posY-1<0)) {
            posY -= 1;
        }
    }
    public void moveDown() {
        if (!(posY+1>frame.getHeight())) {
            posY += 1;
        }
    }
    public void moveRight() {
        System.out.println(frame.getWidth());
        System.out.println(posX);
        if ((posX + 1 > frame.getWidth())) {

        } else {
            posX += 1;
        }
    }
    public void moveLeft() {
        if (!(posX-1<0)){
            posX -= 1;
        }
    }
    public void paint(Graphics g){
        g.drawImage(img, posX, posY, null);
    }
}
