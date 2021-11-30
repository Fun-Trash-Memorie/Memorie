package application.window.game.panels;

import application.Main;
import application.sound.SoundSystem;
import application.window.game.ConstructionHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainMenuPanel extends JPanel implements ConstructionHelper { //Das Hauptmenü-Panel erweitert ein J-Panel und implementiert das Interface ConstructionHelper

    /* Der Übersicht halber für das Klassen-Diagramm hier schon vorgemerkt */
    private final JPanel headPanel;
    private final JLabel header = new JLabel("Memory!");
    private final JPanel buttonPanel;
    private final JButton b_play;
    private final JButton b_lib;
    private final JButton b_opt;
    private final JButton b_exit;
    private final Font sliderFont;
    private final JPanel volumePanel;
    private final JSlider volumeSwitch;
    private static int volume;
    /*                                                                      */

    final int BP_WIDTH = width;
    final int BP_HEIGHT = height-header_height - height/4;

    final int VP_WIDTH = width / 2;
    final int VP_HEIGHT = height / 4;

    final int VSL_WIDTH = VP_WIDTH - 2*padding;
    final int VSL_HEIGHT = VP_HEIGHT - 2*padding;

    final int V_MIN = 0;
    final int V_MAX = 100;
    final int V_VALUE = 30;

    BufferedImage playIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/play.png")));
    BufferedImage playSelectedIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/playx.png")));
    BufferedImage libIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sammlung.png")));
    BufferedImage libSelectedIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sammlungx.png")));
    BufferedImage optIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Einstellungen.png")));
    BufferedImage optSelectedIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Einstellungenx.png")));


    public MainMenuPanel() throws IOException {    //Hauptmenü-Panel Konstruktor

        Main.soundSystem = new SoundSystem("musicfox_albatros.wav");    //neues Sound-System wird mit dem Hintergrund-Song geladen
        Main.soundSystem.play();    //neues Sound-System spielt geladene Datei ab

        setBounds(0, 0, width, height);
        setBackground(bg_color1); //Der Hintergrund wird auf den Farbwert dunkelgrau gestellt
        setLayout(null);  //Das Layout wird auf den Wert null festgelegt

        headPanel = new JPanel();    //Das Head-Panel wird ein neues J-Panel
        headPanel.setBounds(0, 0, hp_width, hp_height);
        headPanel.setBackground(bg_color2);
        headPanel.setLayout(null);

        header.setBounds(hp_width/2-header_width/2, 0, header_width, header_height);
        header.setFont(headFont);
        header.setForeground(headColor);
        headPanel.add(header);
        add(headPanel);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(0, hp_height, BP_WIDTH, BP_HEIGHT);
        buttonPanel.setBackground(bg_color1);
        buttonPanel.setLayout(null);


        b_play = new JButton();
        b_play.setBounds(BP_WIDTH/2-btn_width/2, padding, btn_width, btn_height);
        b_play.setIcon(new ImageIcon(playIcon));
        b_play.addActionListener(e -> {
            System.out.println("Spielen wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);
            Main.window.remove(Main.mainMenuPanel);
            Main.window.repaint();
            Main.gameSelectPanel.setVisible(true);
            Main.window.add(Main.gameSelectPanel);
        });
        b_play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_play.setIcon(new ImageIcon(playSelectedIcon));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_play.setIcon(new ImageIcon(playIcon));
            }
        });


        b_lib = new JButton();
        b_lib.setBounds(BP_WIDTH/2-btn_width/2, btn_height + 2* padding, btn_width, btn_height);
        b_lib.setIcon(new ImageIcon(libIcon));
        b_lib.addActionListener(e -> {
            System.out.println("Sammlung wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);
            Main.window.remove(Main.mainMenuPanel);
            Main.window.repaint();
            Main.window.add(Main.libraryPanel);
        });
        b_lib.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_lib.setIcon(new ImageIcon(libSelectedIcon));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_lib.setIcon(new ImageIcon(libIcon));
            }
        });

        b_opt = new JButton();
        b_opt.setBounds(BP_WIDTH/2-btn_width/2, 2*btn_height + 3* padding, btn_width, btn_height);
        b_opt.setIcon(new ImageIcon(optIcon));
        b_opt.addActionListener(e -> {
            System.out.println("Einstellungen wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);
            Main.window.remove(Main.mainMenuPanel);
            Main.window.repaint();
            Main.window.add(Main.settingPanel);
        });
        b_opt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_opt.setIcon(new ImageIcon(optSelectedIcon));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_opt.setIcon(new ImageIcon(optIcon));
            }
        });

        b_exit = new JButton("Beenden");
        b_exit.setBounds(BP_WIDTH/2-btn_width/2, 3*btn_height + 4* padding, btn_width, btn_height);
        b_exit.setBackground(buttonColor);
        b_exit.setFont(buttonFont);
        // b_exit.setIcon(new ImageIcon(exitIcon));
        b_exit.addActionListener(e -> {
            System.out.println("Beenden wurde ausgewählt.");
            System.exit(0);
        });
        b_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_exit.setText(">Beenden<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_exit.setText("Beenden");            }
        });

        buttonPanel.add(b_play);
        buttonPanel.add(b_lib);
        buttonPanel.add(b_opt);
        buttonPanel.add(b_exit);
        add(buttonPanel);

        volumePanel = new JPanel();
        volumePanel.setBounds((width/2)-(VP_WIDTH/2), hp_height + BP_HEIGHT, VP_WIDTH, VP_HEIGHT);
        volumePanel.setLayout(null);
        volumePanel.setBackground(bg_color2);

        volumeSwitch = new JSlider(JSlider.HORIZONTAL, V_MIN, V_MAX, V_VALUE);
        volumeSwitch.setBounds(padding, padding, VSL_WIDTH, VSL_HEIGHT);
        volumeSwitch.setBackground(bg_color1);
        volumeSwitch.setForeground(fg_color1);
        sliderFont = new Font("Comic Sans MS", Font.PLAIN, 20);
        volumeSwitch.setFont(sliderFont);
        volumeSwitch.setMajorTickSpacing(20);
        volumeSwitch.setMinorTickSpacing(5);
        volumeSwitch.setPaintTicks(true);
        volumeSwitch.setPaintLabels(true);
        volumeSwitch.addChangeListener(e -> {
            volume = volumeSwitch.getValue();
            System.out.println(volume);
            SoundSystem.setVolume(volume);
        });

        volumePanel.add(volumeSwitch);
        add(volumePanel);



        setVisible(true);
    }
}