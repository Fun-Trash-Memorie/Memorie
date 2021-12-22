package application.game.panels;

import application.Main;
import application.game.sound.SoundSystem;
import application.window.ConstructionHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameSelectPanel extends JPanel implements ConstructionHelper {

    private final JButton b_PvE, b_PvP, b_back;

    // Bilder für die Buttons werden geladen
    private final BufferedImage PVE_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/PvE.png")));
    private final BufferedImage PVE_SELECT_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/PvEx.png")));
    private final BufferedImage PVP_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/PvP.png")));
    private final BufferedImage PVP_SELECT_BUFIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/PvPx.png")));


    public GameSelectPanel() throws IOException {

        // Spielauswahlmenü wird eingestellt
        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);

        // Erstellen und Einstellen des Buttons "PVE"
        b_PvE = new JButton();
        b_PvE.setBounds((width-rightFiller)/2-btn_width-padding, padding, btn_width, btn_height);
        b_PvE.setIcon(new ImageIcon(PVE_BUFIMG));
        b_PvE.setFont(buttonFont);
        // Befehl auf Knopfdruck: Spielauswahlmenü wird durch Spielbildschirm ersetzt
        b_PvE.addActionListener(e -> {
            System.out.println("PvE wurde ausgewählt.");


            Main.gameSelectPanel.setVisible(false);
            Main.window.remove(Main.gameSelectPanel);
            Main.window.repaint();
            Main.gamePanel.setVisible(true);
            Main.window.add(Main.gamePanel);
        });
        // Hervorhebung des Textes beim Hovern
        b_PvE.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_PvE.setIcon(new ImageIcon(PVE_SELECT_BUFIMG));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_PvE.setIcon(new ImageIcon(PVE_BUFIMG));
            }
        });

        // Erstellen und Einstellen des Buttons "PVP"
        b_PvP = new JButton();
        b_PvP.setBounds((width-rightFiller)/2 + padding, padding, btn_width, btn_height);
        b_PvP.setIcon(new ImageIcon(PVP_BUFIMG));
        b_PvP.setFont(buttonFont);
        // Befehl auf Knopfdruck: Spielauswahlmenü wird durch Spielbildschirm ersetzt
        b_PvP.addActionListener(e -> {
            System.out.println("PvP wurde ausgewählt.");

            Main.gameSelectPanel.setVisible(false);
            Main.window.remove(Main.gameSelectPanel);
            Main.window.repaint();
            Main.gamePanel.setVisible(true);
            Main.window.add(Main.gamePanel);
        });
        // Hervorhebung des Textes beim Hovern
        b_PvP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_PvP.setIcon(new ImageIcon(PVP_SELECT_BUFIMG));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_PvP.setIcon(new ImageIcon(PVP_BUFIMG));
            }
        });

        b_back = new JButton("Zurück");
        b_back.setBounds(padding, height-btn_height-bottomFiller-padding, (int)(btn_width/1.5), btn_height);
        b_back.setBackground(buttonColor);
        b_back.setFont(buttonFont);
        // Befehl auf Knopfdruck: Spielauswahlmenü wird durch Hauptmenü ersetzt
        b_back.addActionListener(e -> {
            System.out.println("Zurück wurde ausgewählt.");
                 Main.soundSystem = new SoundSystem ("BackButtonTon.wav");
            Main.gameSelectPanel.setVisible(false);
            Main.window.remove(Main.gameSelectPanel);
            Main.window.repaint();
            Main.mainMenuPanel.setVisible(true);
            Main.window.add(Main.mainMenuPanel);
        });
        // Hervorhebung des Textes beim Hovern
        b_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_back.setText(">Zurück<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_back.setText("Zurück");            }
        });

        // Buttons werden dem Menü hinzugefügt
        add(b_PvE);
        add(b_PvP);
        add(b_back);

        setVisible(true);
    }
}
