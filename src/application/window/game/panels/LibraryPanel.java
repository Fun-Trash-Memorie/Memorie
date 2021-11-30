package application.window.game.panels;

import application.Main;
import application.camera.Cam;
import application.window.game.ConstructionHelper;
import org.opencv.core.CvException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Scanner;

public class LibraryPanel extends JPanel implements ConstructionHelper {

    private final JButton b_cam, b_back;

    public LibraryPanel(){

        setBounds(0, 0, width, height);
        setBackground(bg_color1); //Der Hintergrund wird auf den Farbwert dunkelgrau gestellt
        setLayout(null);  //Das Layout wird als Border-Layout festgelegt

        b_cam = new JButton("Cam");
        b_cam.setBounds(width/2-btn_width/2, height-btn_height-bottomFiller-padding, btn_width, btn_height);
        b_cam.setBackground(buttonColor);
        b_cam.setFont(buttonFont);
        b_cam.addActionListener(e -> {
            Main.cam = new Cam();
            try {
                Main.cam.startCam();
            } catch (CvException | IOException cve) {
                System.err.println("Cv-Exception. Show Stack-Trace?  [y][n]");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                if (s.equals("y"))
                    cve.printStackTrace();
                scanner.close();
            }
        });
        b_cam.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_cam.setText(">Cam<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_cam.setText("Cam");
            }
        });

        b_back = new JButton("Zurück");
        b_back.setBounds(padding, height-btn_height-bottomFiller-padding, (int)(btn_width/1.5), btn_height);
        b_back.setBackground(buttonColor);
        b_back.setFont(buttonFont);
        b_back.addActionListener(e -> {
            System.out.println("Zurück wurde ausgewählt.");

            Main.libraryPanel.setVisible(false);
            Main.window.remove(Main.libraryPanel);
            Main.window.repaint();
            Main.mainMenuPanel.setVisible(true);
            Main.window.add(Main.mainMenuPanel);
        });
        b_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_back.setText(">Zurück<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_back.setText("Zurück");
            }
        });


        add(b_back);
        add(b_cam);


        setVisible(true);
    }
}
