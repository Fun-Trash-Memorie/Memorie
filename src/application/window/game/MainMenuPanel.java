package application.window.game;

import application.Main;
import application.camera.Cam;
import application.sound.SoundSystem;
import org.opencv.core.CvException;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    private JSlider volumeSwitch;

    public MainMenuPanel() {

        Main.soundSystem = new SoundSystem("musicfox_albatros.wav");
        Main.soundSystem.play();

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
        buttonPanel.setPreferredSize(new Dimension(Main.window.getWidth(), (int) (Main.window.getHeight() / 1.5)));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton b_PvE = new JButton("PvE");
        b_PvE.setPreferredSize(new Dimension((int) (Main.window.getWidth() / 2.5), (int) (Main.window.getHeight() / 2.5)));
        b_PvE.setBackground(Color.LIGHT_GRAY);
        Font buttonFont = new Font("Comic Sans MS", Font.PLAIN, 75);
        b_PvE.setFont(buttonFont);
        b_PvE.addActionListener(e -> {
            System.out.println("PvE wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);

            Main.window.repaint();

            Main.window.add(Main.gamePanel);
        });


        JButton b_PvP = new JButton("PvP");
        b_PvP.setPreferredSize(new Dimension((int) (Main.window.getWidth() / 2.5), (int) (Main.window.getHeight() / 2.5)));
        b_PvP.setBackground(Color.LIGHT_GRAY);
        b_PvP.setFont(buttonFont);
        b_PvE.addActionListener(e -> {

        });

        buttonPanel.add(b_PvE, BorderLayout.EAST);
        buttonPanel.add(b_PvP, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);

        volumeSwitch = new JSlider(JSlider.HORIZONTAL, 0, 100, 30);
        volumeSwitch.setMinimum(0);
        volumeSwitch.setMaximum(100);
        volumeSwitch.setMajorTickSpacing(20);
        volumeSwitch.setMinorTickSpacing(5);
        volumeSwitch.setPaintTicks(true);
        volumeSwitch.setPaintLabels(true);
        volumeSwitch.addChangeListener(e -> {
            int volume = volumeSwitch.getValue();
            System.out.println(volume);
                Main.soundSystem.setVolume(volume);
        });
        add(volumeSwitch, BorderLayout.SOUTH);

        // nur vorübergehend -------
        JButton camButton = new JButton("CAM");
        camButton.setPreferredSize(new Dimension((Main.window.getWidth() / 5), (Main.window.getHeight()) / 8));
        camButton.addActionListener(e -> {
            Main.cam = new Cam();
            try {
                Main.cam.startCam();
            } catch (CvException cve) {
                System.err.println("Keine Kamera gefunden.");
            }
        });
        buttonPanel.add(camButton, BorderLayout.SOUTH);
        // -------

        setVisible(true);
    }
}