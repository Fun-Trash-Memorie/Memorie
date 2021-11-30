package application.window;

import application.window.game.ConstructionHelper;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements ConstructionHelper {

    public Window(){
        System.out.println("Fenster wird kreiert...");
        setTitle("Memory");
        setSize(new Dimension(width, height));
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        System.out.println("Fenster kreiert.");
    }
}