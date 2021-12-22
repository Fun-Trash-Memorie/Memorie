package application.game.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import application.Main;
import application.window.ConstructionHelper;
import application.window.SubWindow;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class PictureView extends SubWindow implements ConstructionHelper {

    private final int WIDTH = (width-rightFiller)/2;
    private final int HEIGHT = (height-bottomFiller)/2;

    private final JLabel DISPLAY_LBL;
    private final JButton BACK_BTN, CHECKFORDOUBLE_BTN, DELETE_BTN;

    public PictureView(BufferedImage bufferedImage) {
        super("Motiv");

        // Anpassen des Fenster
        setBounds((width-rightFiller)/2 - WIDTH/2, (height-bottomFiller)/2 - HEIGHT/2, WIDTH, HEIGHT);
        setBackground(bg_color2);
        setLayout(null);

        // Thread für das Schließen des Fensters
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Main.window.setEnabled(true);
                Main.subWindow.removeAll();
            }
        });

        // Label für das Darstellen des Bildes
        DISPLAY_LBL = new JLabel(new ImageIcon(bufferedImage));
        DISPLAY_LBL.setBackground(bg_color1);
        DISPLAY_LBL.setBounds(margin, margin, WIDTH-2*margin, HEIGHT - margin - padding/2 - height/7);
        add(DISPLAY_LBL);

        // Button für das Schließen des Fensters
        BACK_BTN = new JButton();
        BACK_BTN.setBackground(buttonColor);
        BACK_BTN.setText("Zurück");
        BACK_BTN.setBounds(margin, HEIGHT - margin + padding/2 - height/7, (WIDTH-2*margin) / 4, 60);
        BACK_BTN.addActionListener(e -> {
            Main.window.setEnabled(true);
            Main.subWindow.removeAll();
            Main.subWindow.setVisible(false);
        });
        add(BACK_BTN);

        // Button für die Überprüfung eines doppelten Bildes (momentan nicht implementier)
        CHECKFORDOUBLE_BTN = new JButton();
        CHECKFORDOUBLE_BTN.setBackground(buttonColor);
        CHECKFORDOUBLE_BTN.setText("Doppelt?");
        CHECKFORDOUBLE_BTN.setBounds(margin + (WIDTH - 2*margin) / 4 + padding, HEIGHT - margin + padding/2 - height/7, (WIDTH-2*margin) / 4, 60);
        CHECKFORDOUBLE_BTN.addActionListener(e -> {
            //setVisible(false);
            BufferedImage screenshot = new BufferedImage(Main.libraryPanel.getWidth(), Main.libraryPanel.getHeight(), BufferedImage.TYPE_INT_RGB);

            System.out.println(bufferedImage);
            System.out.println(screenshot);

            if (Main.cam.picMatcher(bufferedImage, screenshot)) {
                System.out.println("Übereinstimmung gefunden!");
            } else {
                System.out.println("Keine Übereinstimmung gefunden!");
            }

        });
        //add(CHECKFORDOUBLE_BTN);

        // Button zum Löschen eines Bildes (momentan nicht implementiert)
        DELETE_BTN = new JButton();
        DELETE_BTN.setBackground(Color.red);
        DELETE_BTN.setText("Löschen");
        CHECKFORDOUBLE_BTN.setBounds(margin + 2*(WIDTH - 2*margin) / 4 + padding, HEIGHT - margin + padding/2 - height/7, (WIDTH-2*margin) / 4, 60);
        DELETE_BTN.addActionListener(e -> {


            this.setVisible(false);
            Main.window.remove(this);
            Main.window.repaint();
        });
        //add(DELETE_BTN);


        setVisible(true);
    }

}
