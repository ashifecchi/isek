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

public class Renderer implements ActionListener {
    private static JFrame frame;
    private static JLabel canvas;
    private static JPanel holder;
    private static JButton startbutton;
    private static Dimension canvasSize;
    private static Graphics graph;

    private static final int GAME_WIDTH = 400;
    private static final int GAME_HEIGHT = 300;

    private static String world = "menu";
    private static int gameWidth = 0;
    private static int gameHeight = 0;

    private static long LastFPS = 0;
    private static int NowFPS = 0;
    private static int totalFrames = 0;
    private static long start; //so that the menustart method works without crying.

    public Renderer() throws IOException {
        init();
    }

    public void init() throws IOException {
        // making stuff har har
        Toolkit tools = Toolkit.getDefaultToolkit();
        frame = new JFrame();
        holder = new JPanel();
        canvas = new JLabel();
        startbutton = new JButton();
        canvasSize = tools.getScreenSize();
        gameWidth = (int) canvasSize.getWidth();
        gameHeight = (int) canvasSize.getHeight();

        //set the sizes n location
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocation(0, 0);
        canvas.setPreferredSize(new Dimension(canvasSize));
        holder.setBounds(1000,1000,2000, (int)canvasSize.getHeight());
        startbutton.setVisible(false);
        startbutton.setEnabled(true);

        //adding stuff
        startbutton.addActionListener(this);
        frame.add(canvas);
        canvas.add(holder);
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
        while (true) {
            totalFrames++;
            long start = System.currentTimeMillis();
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
            graph = Img.getGraphics();

            //the menu screen
            if (world.equals("menu")) {
                holder.add(startbutton,BorderLayout.SOUTH);
                menuScreen();
            }
            if (world.equals("bedroom")){
                bedroom();
            }

            //render sprite
            //make sprite
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
    }

    public static void bedroom() throws IOException {
        World mcRoom = new World(Renderer.loadImage("src/bedroom.png"));
        mcRoom.render(graph);
    }

        //to create buffered images
        public static BufferedImage loadImage (String path) throws IOException {
            return ImageIO.read(new File(path));
        }
        public static void menuScreen() throws IOException {
            BufferedImage bg1 = Renderer.loadImage("src/startbg.png");
            BufferedImage bg2 = Renderer.loadImage("src/startbg2.png");
            World menu = new World(Renderer.loadImage("src/startbg.png"));
            long end = System.currentTimeMillis();  // makes the screen flash(cool!)
            long elapsedTime = end - start;
            if (elapsedTime%5 != 0) {
                menu.setBg(bg1);
            } else {
                menu.setBg(bg2);
            }
            menu.render(graph);
        }

    public void actionPerformed(ActionEvent ae) {
        // cast ae to a JButton object since we want to call the getText method on it;
        // casting is needed since getSource() returns Object type, NOT a JButton
        Object source = ae.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if (button == startbutton){
                System.out.println("clicked");
                world = "bedroom";
            }
        }
    }
}