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
                    if (Img.validate(graphic) == VolatileImage.IMAGE_INCOMPATIBLE){
                        Img = graphic.createCompatibleVolatileImage(gameWidth, gameHeight); //create the image again
                    }

                    Graphics graph = Img.getGraphics();

                    graph.setColor(new Color(170, 224, 242));
                    graph.fillRect(0,0, gameWidth, gameHeight);
                    graph.setColor(new Color(188, 240, 173));
                    graph.drawRect(0,100,gameWidth,gameHeight);
                    graph.drawImage(new BufferedImage(0,0,"png")), 0,0,gameWidth,gameHeight,null);
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
