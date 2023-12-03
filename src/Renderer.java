import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Renderer {
    private static JFrame frame;
    private static JLabel canvas;
    private static Dimension canvasSize;

    private static final int GAME_WIDTH = 400;
    private static final int GAME_HEIGHT = 300;

    private static int gameWidth = 0;
    private static int gameHeight = 0;

    private static long LastFPS = 0;
    private static int NowFPS = 0;
    private static int totalFrames = 0;

    public static void init() throws IOException {
        // making stuff har har
        Toolkit tools = Toolkit.getDefaultToolkit();
        frame = new JFrame();
        canvas = new JLabel();
        canvasSize = tools.getScreenSize();
        gameWidth = (int) canvasSize.getWidth();
        gameHeight = (int) canvasSize.getHeight();

        //set the sizes n location
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocation(0, 0);
        canvas.setPreferredSize(new Dimension(canvasSize));

        //frame stuff like adding n closing
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        // yay static method that starts the game n stuff
        startRendering();
    }

    //makes the graphics
    private static void startRendering() throws IOException {
        GraphicsConfiguration graphic = canvas.getGraphicsConfiguration();
        VolatileImage Img = graphic.createCompatibleVolatileImage(gameWidth, gameHeight);
        totalFrames++;
        //fps counter
        if (System.nanoTime() > LastFPS + 1000000000) {
            LastFPS = System.nanoTime();
            NowFPS = totalFrames;
            totalFrames = 0;
            System.out.println("FPS: " + NowFPS);
        }

        if (Img.validate(graphic) == VolatileImage.IMAGE_INCOMPATIBLE) {
            Img = graphic.createCompatibleVolatileImage(gameWidth, gameHeight); //create the image again
        }

        //the graphics creator
        Graphics graph = Img.getGraphics();
        BufferedImage bg1 = Renderer.loadImage("src/startbg.png");
        BufferedImage bg2 = Renderer.loadImage("src/startbg2.png");
        World menu = new World(Renderer.loadImage("src/startbg.png"));
        if ((System.currentTimeMillis()) % 5000 == 0) {
            menu.setBg(bg1);
        } else {
            menu.setBg(bg2);
        }
        menu.render(graph);

        //render sprite
        Sprite Image = new Sprite(100, 100);

        //fps counter drawing time
        graph.setColor(new Color(100, 70, 100));
        graph.drawString("FPS: " + NowFPS, 2, 10);

        //other stuff...
        Image.render(graph);

        //end this
        graph.dispose();

        graph = canvas.getGraphics();
        graph.drawImage(Img, 0, 0, Color.black, null);

        //disposing of the graphics
        graph.dispose();
    }

        //to create buffered images
        public static BufferedImage loadImage (String path) throws IOException {
            return ImageIO.read(new File(path));
        }
}