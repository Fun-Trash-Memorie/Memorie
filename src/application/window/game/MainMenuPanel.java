package application.window.game;

import application.Main;
import application.camera.CamThread;
import application.sound.SoundSystem;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private boolean pvePressed = false, pvpPressed = false;

    public MainMenuPanel() {

        Main.soundSystem = new SoundSystem("musicfox_albatros.wav");

        setPreferredSize(Main.window.getPreferredSize());
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        JPanel headPanel = new JPanel();
        headPanel.setPreferredSize(new Dimension(Main.window.getWidth(), (Main.window.getHeight()) / 4));
        headPanel.setBackground(Color.LIGHT_GRAY);

        JLabel header = new JLabel();
        header.setText("Memory!");
        Font headFont = new Font("Comic Sans MS", Font.BOLD, 100);
        header.setFont(headFont);
        header.setForeground(Color.blue);
        headPanel.add(header);
        add(headPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(Main.window.getWidth(), (int)(Main.window.getHeight() / 1.5)));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton b_PvE = new JButton("PvE");
        b_PvE.setPreferredSize(new Dimension((int)(Main.window.getWidth() / 2.5), (int)(Main.window.getHeight() / 2.5)));
        b_PvE.setBackground(Color.LIGHT_GRAY);
        Font buttonFont = new Font("Comic Sans MS", Font.PLAIN, 75);
        b_PvE.setFont(buttonFont);
        b_PvE.addActionListener(e -> pvePressed = true);


        JButton b_PvP = new JButton("PvP");
        b_PvP.setPreferredSize(new Dimension((int)(Main.window.getWidth() / 2.5), (int)(Main.window.getHeight() / 2.5)));
        b_PvP.setBackground(Color.LIGHT_GRAY);
        b_PvP.setFont(buttonFont);
        b_PvE.addActionListener(e -> pvpPressed = true);

        buttonPanel.add(b_PvE, BorderLayout.EAST);
        buttonPanel.add(b_PvP, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);

        // nur vorübergehend -------
        JButton camButton = new JButton("CAM");
        camButton.setPreferredSize(new Dimension((Main.window.getWidth() / 5), (Main.window.getHeight()) / 8));
        camButton.addActionListener(e -> {
            Main.camThread = new CamThread();
            Main.camThread.start();
        });
        buttonPanel.add(camButton, BorderLayout.SOUTH);
        // -------

        setVisible(true);
    }

    public boolean isPvpPressed() {
        return pvpPressed;
    }
    public void setPvpPressed(boolean pvpPressed) {
        this.pvpPressed = pvpPressed;
    }
    public boolean isPvePressed() {
        return pvePressed;
    }
    public void setPvePressed(boolean pvePressed) {
        this.pvePressed = pvePressed;
    }
}