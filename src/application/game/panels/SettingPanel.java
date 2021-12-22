package application.game.panels;

import application.Main;
import application.game.sound.SoundSystem;
import application.window.ConstructionHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingPanel extends JPanel implements ConstructionHelper {

    private final JButton b_back;

    public SettingPanel() {

        // Einstellungsmenü wird eingestellt
        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);

        // Erstellen und Einstellen des Buttons "Zurück"
        b_back = new JButton("Zurück");
        b_back.setBounds(padding, height-btn_height-bottomFiller-padding, (int)(btn_width/1.5), btn_height);
        b_back.setBackground(buttonColor);
        b_back.setFont(buttonFont);
        // Befehl auf Knopfdruck: Einstellungsmenü wird durch Hauptmenü ersetzt
        b_back.addActionListener(e -> {
            System.out.println("Zurück wurde ausgewählt.");
            Main.soundSystem = new SoundSystem("BackButtonTon.wav");

            Main.settingPanel.setVisible(false);
            Main.window.remove(Main.settingPanel);
            Main.window.repaint();
            Main.mainMenuPanel.setVisible(true);
            Main.window.add(Main.mainMenuPanel);
        });
        // Hervorhebungs des Textes beim Hovern
        b_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_back.setText(">Zurück<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_back.setText("Zurück");            }
        });

        // "Zurück" Button wird eingefügt
        add(b_back);

        setVisible(true);
    }
}
