import java.awt.*;
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
    public static void init (){
        Toolkit tools = Toolkit.getDefaultToolkit();
        frame = new JFrame();
        canvas = new JLabel();
        canvasSize = tools.getScreenSize();
        gameWidth = (int)canvasSize.getWidth();
        gameHeight = (int)canvasSize.getHeight();

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocation(0,0);
        canvas.setPreferredSize(new Dimension(canvasSize));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setResizable(false);
        //frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        startRendering();
    }
    private static void startRendering() {
        Thread thread = new Thread() {
            public void run(){
                boolean goober = true;
                GraphicsConfiguration graphic = canvas.getGraphicsConfiguration();
                VolatileImage Img = graphic.createCompatibleVolatileImage(gameWidth, gameHeight);
                while (goober){
                    //counts fps... might remove if not vital to my thing.
                    totalFrames++;
                    if (System.nanoTime() > LastFPS + 1000000000){
                        LastFPS = System.nanoTime();
                        NowFPS = totalFrames;
                        totalFrames = 0;
                        System.out.println("FPS: "+ NowFPS);
                    }

                    if (Img.validate(graphic) == VolatileImage.IMAGE_INCOMPATIBLE){
                        Img = graphic.createCompatibleVolatileImage(gameWidth, gameHeight); //create the image again
                    }

                    Graphics graph = Img.getGraphics();
                    //screen clear
                    graph.setColor(new Color(170, 224, 242));
                    graph.fillRect(0,0, gameWidth, gameHeight);

                    //render stuff
                    graph.setColor(new Color(80, 255, 143));
                    graph.fillRect(0,0,100,100);

                    //fps counter drawing time
                    graph.setColor(new Color(100,70,100));
                    graph.drawString("FPS: " + NowFPS,2,10);

                    //other stuff...

                    graph.dispose();

                    graph = canvas.getGraphics();
                    graph.drawImage(Img, 0, 0, new Color(188, 240, 173), null);

                    graph.dispose();
                }
            }
        };
        thread.setName("coolguy746");
        thread.start();
    }
}
