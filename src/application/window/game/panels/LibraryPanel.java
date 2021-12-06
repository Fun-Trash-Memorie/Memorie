package application.window.game.panels;

import application.Main;
import application.camera.Cam;
import application.window.game.ConstructionHelper;
import org.opencv.core.CvException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LibraryPanel extends JPanel implements ConstructionHelper {

    private final JButton b_cam, b_back;

    private final JPanel libPanel;


    private final int LIB_WIDTH = width-rightFiller-2*margin;
    private final int LIB_HEIGHT = height-margin*2-btn_height-padding-bottomFiller;

    private final int PIC_SIZE = (LIB_WIDTH - 2*margin - 4*padding)/5;

    public LibraryPanel() throws IOException {

        setBounds(0, 0, width, height);
        setBackground(bg_color1); //Der Hintergrund wird auf den Farbwert dunkelgrau gestellt
        setLayout(null);  //Das Layout wird als Border-Layout festgelegt


        libPanel = new JPanel();
        libPanel.setBackground(bg_color2);
        libPanel.setBounds(margin, margin, LIB_WIDTH, LIB_HEIGHT);
        libPanel.setLayout(null);

        initLib();





        b_cam = new JButton("Cam");
        b_cam.setBounds(width/2-btn_width/2, height-btn_height-bottomFiller-margin, btn_width, btn_height);
        b_cam.setBackground(buttonColor);
        b_cam.setFont(buttonFont);
        b_cam.addActionListener(e -> {
            try {
                Main.cam = new Cam();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        b_back.setBounds(margin, height-btn_height-bottomFiller-margin, (int)(btn_width/1.5), btn_height);
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

        add(libPanel);
        add(b_back);
        add(b_cam);


        setVisible(true);
    }

    public void initLib() throws IOException {
        libPanel.removeAll();
        ArrayList<BufferedImage> liblist_BI = new ArrayList<>();
        ArrayList<JButton> liblist_BTN = new ArrayList<>();

        File dir = new File("img");
        String[] pathnames;

        pathnames = dir.list();

        for (int i = 0; i < Objects.requireNonNull(pathnames).length; i++) {
            System.out.println(pathnames[i]);

            BufferedImage bi = ImageIO.read(new File("img/" + pathnames[i]));
            //bi.getScaledInstance(PIC_SIZE, PIC_SIZE, Image.SCALE_DEFAULT);
            liblist_BI.add(resizeImage(bi, PIC_SIZE, PIC_SIZE));

        }
        int count = 0;
        for (BufferedImage bi : liblist_BI) {
            count++;

            JButton b = new JButton(new ImageIcon(bi));
            liblist_BTN.add(b);

            if (count <= 10) {
                if (count <= 5) {
                    b.setBounds(margin + padding*(count-1) + PIC_SIZE*(count-1), margin, PIC_SIZE, PIC_SIZE);
                } else {
                    int i = count - 5;
                    b.setBounds(margin + padding*(i-1) + PIC_SIZE*(i-1), margin + padding + PIC_SIZE, PIC_SIZE, PIC_SIZE);
                }
            } else {
                System.out.println("Image number " + count + " did not fit on page.");
            }

            libPanel.add(b);
            libPanel.repaint();
        }
    }

    /**
     *
     * @Source https://www.baeldung.com/java-resize-image
     *
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
