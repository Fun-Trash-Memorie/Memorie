package application.window;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    //Standardbildschirm und Bildschirmgröße bestimmen
    private final GraphicsDevice GD = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int WINDOW_WIDTH = (int)(GD.getDisplayMode().getWidth() / 1.5);
    private final int WINDOW_HEIGHT = (int)(GD.getDisplayMode().getHeight() / 1.5);

    public Window(){
        System.out.println("Fenster wird kreiert...");
        setTitle("Memory");
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        System.out.println("Fenster kreiert.");
    }

    public int getWidth() {
        return WINDOW_WIDTH;
    }

    public int getHeight() {
        return WINDOW_HEIGHT;
    }
}