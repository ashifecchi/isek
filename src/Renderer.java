import java.awt.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.VolatileImage;

public class Renderer {
    private static JFrame frame;
    private static JLabel canvas;
    private static int canvasWidth = 0;
    private static int canvasHeight = 0;

    private static final int GAME_WIDTH = 400;
    private static final int GAME_HEIGHT = 300;

    private static final int gameWidth = 0;
    private static final int gameHeight = 0;
    public static void init (){
        frame = new JFrame();
        canvas = new JLabel();

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
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

                    graph.setColor(Color.BLUE);
                    graph.fillRect(0,0, gameWidth, gameHeight);
                    //other stuff...
                    graph.dispose();

                    graph = canvas.getGraphics();
                    graph.drawImage(Img, 0, 0, canvasWidth, canvasHeight, null);

                    graph.dispose();
                }
            }
        };
        thread.setName("coolguy746");
        thread.start();
    }
}
