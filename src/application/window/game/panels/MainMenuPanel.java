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
    private final JPanel HEAD_PNL, BUTTON_PNL, VOLUME_PNL;
    private final JLabel HEADER_LBL = new JLabel("Memory!");
    private final JButton PLAY_BTN, LIB_BTN, OPT_BTN, EXIT_BTN;
    private final Font SLIDER_FNT;
    private final JSlider VOLUME_SLIDER;
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

    private final BufferedImage PLAY_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/play.png")));
    private final BufferedImage PLAY_SELECT_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/playx.png")));
    private final BufferedImage LIB_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sammlung.png")));
    private final BufferedImage LIB_SELECT_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Sammlungx.png")));
    private final BufferedImage OPT_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Einstellungen.png")));
    private final BufferedImage OPT_SELECT_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Einstellungenx.png")));


    public MainMenuPanel() throws IOException {    //Hauptmenü-Panel Konstruktor

        Main.soundSystem = new SoundSystem("musicfox_albatros.wav");    //neues Sound-System wird mit dem Hintergrund-Song geladen

        setBounds(0, 0, width, height);
        setBackground(bg_color1); //Der Hintergrund wird auf den Farbwert dunkelgrau gestellt
        setLayout(null);  //Das Layout wird auf den Wert null festgelegt

        HEAD_PNL = new JPanel();    //Das Head-Panel wird ein neues J-Panel
        HEAD_PNL.setBounds(0, 0, hp_width, hp_height);
        HEAD_PNL.setBackground(bg_color2);
        HEAD_PNL.setLayout(null);

        HEADER_LBL.setBounds(hp_width/2-header_width/2, 0, header_width, header_height);
        HEADER_LBL.setFont(headFont);
        HEADER_LBL.setForeground(headColor);
        HEAD_PNL.add(HEADER_LBL);
        add(HEAD_PNL);

        BUTTON_PNL = new JPanel();
        BUTTON_PNL.setBounds(0, hp_height, BP_WIDTH, BP_HEIGHT);
        BUTTON_PNL.setBackground(bg_color1);
        BUTTON_PNL.setLayout(null);


        PLAY_BTN = new JButton();
        PLAY_BTN.setBounds(BP_WIDTH/2-btn_width/2, padding, btn_width, btn_height);
        PLAY_BTN.setIcon(new ImageIcon(PLAY_BUFIMG));
        PLAY_BTN.addActionListener(e -> {
            System.out.println("Spielen wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);
            Main.window.remove(Main.mainMenuPanel);
            Main.window.repaint();
            Main.gameSelectPanel.setVisible(true);
            Main.window.add(Main.gameSelectPanel);
        });
        PLAY_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                PLAY_BTN.setIcon(new ImageIcon(PLAY_SELECT_BUFIMG));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                PLAY_BTN.setIcon(new ImageIcon(PLAY_BUFIMG));
            }
        });


        LIB_BTN = new JButton();
        LIB_BTN.setBounds(BP_WIDTH/2-btn_width/2, btn_height + 2* padding, btn_width, btn_height);
        LIB_BTN.setIcon(new ImageIcon(LIB_BUFIMG));
        LIB_BTN.addActionListener(e -> {
            System.out.println("Sammlung wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);
            Main.window.remove(Main.mainMenuPanel);
            Main.window.repaint();
            Main.libraryPanel.setVisible(true);
            Main.window.add(Main.libraryPanel);
        });
        LIB_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                LIB_BTN.setIcon(new ImageIcon(LIB_SELECT_BUFIMG));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                LIB_BTN.setIcon(new ImageIcon(LIB_BUFIMG));
            }
        });

        OPT_BTN = new JButton();
        OPT_BTN.setBounds(BP_WIDTH/2-btn_width/2, 2*btn_height + 3* padding, btn_width, btn_height);
        OPT_BTN.setIcon(new ImageIcon(OPT_BUFIMG));
        OPT_BTN.addActionListener(e -> {
            System.out.println("Einstellungen wurde ausgewählt.");

            Main.mainMenuPanel.setVisible(false);
            Main.window.remove(Main.mainMenuPanel);
            Main.window.repaint();
            Main.settingPanel.setVisible(true);
            Main.window.add(Main.settingPanel);
        });
        OPT_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                OPT_BTN.setIcon(new ImageIcon(OPT_SELECT_BUFIMG));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                OPT_BTN.setIcon(new ImageIcon(OPT_BUFIMG));
            }
        });

        EXIT_BTN = new JButton("Beenden");
        EXIT_BTN.setBounds(BP_WIDTH/2-btn_width/2, 3*btn_height + 4* padding, btn_width, btn_height);
        EXIT_BTN.setBackground(buttonColor);
        EXIT_BTN.setFont(buttonFont);
        // b_exit.setIcon(new ImageIcon(exitIcon));
        EXIT_BTN.addActionListener(e -> {
            System.out.println("Beenden wurde ausgewählt.");
            System.exit(0);
        });
        EXIT_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                EXIT_BTN.setText(">Beenden<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                EXIT_BTN.setText("Beenden");            }
        });

        BUTTON_PNL.add(PLAY_BTN);
        BUTTON_PNL.add(LIB_BTN);
        BUTTON_PNL.add(OPT_BTN);
        BUTTON_PNL.add(EXIT_BTN);
        add(BUTTON_PNL);

        VOLUME_PNL = new JPanel();
        VOLUME_PNL.setBounds((width/2)-(VP_WIDTH/2), hp_height + BP_HEIGHT, VP_WIDTH, VP_HEIGHT);
        VOLUME_PNL.setLayout(null);
        VOLUME_PNL.setBackground(bg_color2);

        VOLUME_SLIDER = new JSlider(JSlider.HORIZONTAL, V_MIN, V_MAX, V_VALUE);
        VOLUME_SLIDER.setBounds(padding, padding, VSL_WIDTH, VSL_HEIGHT);
        VOLUME_SLIDER.setBackground(bg_color1);
        VOLUME_SLIDER.setForeground(fg_color1);
        SLIDER_FNT = new Font("Comic Sans MS", Font.PLAIN, 20);
        VOLUME_SLIDER.setFont(SLIDER_FNT);
        VOLUME_SLIDER.setMajorTickSpacing(20);
        VOLUME_SLIDER.setMinorTickSpacing(5);
        VOLUME_SLIDER.setPaintTicks(true);
        VOLUME_SLIDER.setPaintLabels(true);
        VOLUME_SLIDER.addChangeListener(e -> {
            volume = VOLUME_SLIDER.getValue();
            System.out.println(volume);
            SoundSystem.setVolume(volume);
        });

        VOLUME_PNL.add(VOLUME_SLIDER);
        add(VOLUME_PNL);



        setVisible(true);
    }
}