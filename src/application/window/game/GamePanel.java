package application.window.game;

import application.Main;
import application.window.Renderer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {


    public GamePanel() {
        setSize(Main.window.getPreferredSize());
        add(new JLabel("Gamepanel"), BorderLayout.CENTER);
        //Main.renderer = new Renderer();

    }
}