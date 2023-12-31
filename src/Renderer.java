import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class Renderer implements ActionListener, KeyListener {
    public static JFrame frame;
    private static JLabel canvas;
    private static JButton startbutton;
    private static Dimension canvasSize;
    private static Graphics graph;
    private BufferedImage mcUp;
    private BufferedImage mcDown;
    private BufferedImage mcRight;
    private BufferedImage mcLeft;

    private static String world = "menu";
    private static int gameWidth = 0;
    private static int gameHeight = 0;

    private static long LastFPS = 0;
    private static int NowFPS = 0;
    private static int totalFrames = 0;
    private int counter = 0;
    private static long start; //so that the menustart method works without crying.
    static Sprite mc; //to make it exist. 

    public Renderer() throws IOException {
        init();
    }

    public void init() throws IOException {
        // making stuff har har
        Toolkit tools = Toolkit.getDefaultToolkit();
        frame = new JFrame();
        canvas = new JLabel();
        startbutton = new JButton();
        canvasSize = tools.getScreenSize();
        gameWidth = (int) canvasSize.getWidth();
        gameHeight = (int) canvasSize.getHeight();

        //set the sizes n location
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocation(600,300);
        canvas.setPreferredSize(new Dimension(canvasSize));
        startbutton.setBounds(100,100,500,200);
        startbutton.setOpaque(false);
        startbutton.setContentAreaFilled(false);
        startbutton.setBorderPainted(false);

        //adding stuff
        startbutton.addActionListener(this);
        frame.add(canvas, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        //yay method that starts the game n stuff
        startRendering();
    }

    //makes the graphics
    private void startRendering() throws IOException {
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
            }

            if (Img.validate(graphic) == VolatileImage.IMAGE_INCOMPATIBLE) {
                Img = graphic.createCompatibleVolatileImage(gameWidth, gameHeight); //create the image again
            }

            //the graphics creator
            graph = Img.getGraphics();

            //the menu screen
            if (world.equals("menu")) {
                menuScreen();
            }
            if (world.equals("bedroom")){
                if (counter==0) {
                    World mcRoom = new World(Renderer.loadImage("images/bedroom.png"));
                    mc = new Sprite(500, 300, Renderer.loadImage("images/loser.png"));
                    mcUp = Renderer.loadImage("images/loserBack.png");
                    mcDown = Renderer.loadImage("images/loser.png");
                    mcRight = Renderer.loadImage("images/loserRight.png");
                    mcLeft = Renderer.loadImage("images/loserLeft.png");
                    counter++;
                    mcRoom.render(graph);
                }
                bedroom();
            }

            //fps counter drawing time
            graph.setColor(new Color(100, 70, 100));
            graph.drawString("FPS: " + NowFPS, 2, 10);

            //other stuff...

            //end this
            graph.dispose();

            graph = canvas.getGraphics();
            graph.drawImage(Img, 0, 0, Color.black, null);

            //disposing of the graphics
            graph.dispose();
        }
    }

    public void bedroom(){
        frame.addKeyListener(this);
        frame.requestFocus();
        mc.render(graph);
    }

    //menu screen method
    public static void menuScreen() throws IOException {
        frame.add(startbutton);
        BufferedImage bg1 = Renderer.loadImage("images/startbg.png");
        BufferedImage bg2 = Renderer.loadImage("images/startbg2.png");
        World menu = new World(Renderer.loadImage("images/startbg.png"));
        frame.setSize(bg1.getWidth(), bg2.getHeight()+20);
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
        System.out.println("pizza john");
        // cast ae to a JButton object since we want to call the getText method on it;
        // casting is needed since getSource() returns Object type, NOT a JButtonF
        Object source = ae.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            if (button == startbutton){
                startbutton.setEnabled(false);
                world = "bedroom";
            }
        }
    }
    //to create buffered images
    public static BufferedImage loadImage (String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key pressed");
    }

    @Override
    public void keyPressed(KeyEvent e) { //i dont know if this works or not fuck yourself
        if (e.isActionKey()){
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                mc.setImage(mcUp);
                mc.moveUp();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                mc.setImage(mcDown);
                mc.moveDown();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                mc.setImage(mcRight);
                mc.moveRight();
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                mc.setImage(mcLeft);
                mc.moveLeft();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (world.equals("bedroom")) {
            mc.setImage(mcDown);
        }
    }
}