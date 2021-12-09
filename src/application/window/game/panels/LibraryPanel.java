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

    private static int currentPage = 1;

    private final JButton CAM_BTN, BACK_BTN, NEXTPAGE_BTN, PREVPAGE_BTN;

    private final JPanel LIB_PNL;


    private final int LIB_WIDTH = width-rightFiller-2*margin;
    private final int LIB_HEIGHT = height-margin*2-btn_height-padding-bottomFiller;

    private final int PIC_SIZE = (LIB_WIDTH - 2*margin - 4*padding)/5;

    private final int PICS_ONPAGE = 10;

    public LibraryPanel() throws IOException {

        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);


        LIB_PNL = new JPanel();
        LIB_PNL.setBackground(bg_color2);
        LIB_PNL.setBounds(margin, margin, LIB_WIDTH, LIB_HEIGHT);
        LIB_PNL.setLayout(null);


        CAM_BTN = new JButton("Cam");
        CAM_BTN.setBounds(width/2-btn_width/2, height-btn_height-bottomFiller-margin, btn_width, btn_height);
        CAM_BTN.setBackground(buttonColor);
        CAM_BTN.setFont(buttonFont);
        CAM_BTN.addActionListener(e -> {
            try {
                Main.cam = new Cam();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Main.cam.startCam();
            } catch (CvException cve) {
                System.err.println("Cv-Exception. Show Stack-Trace?  [y][n]");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();
                if (s.equals("y"))
                    cve.printStackTrace();
                scanner.close();
            }
        });
        CAM_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                CAM_BTN.setText(">Cam<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                CAM_BTN.setText("Cam");
            }
        });

        BACK_BTN = new JButton("Zurück");
        BACK_BTN.setBounds(margin, height-btn_height-bottomFiller-margin, (int)(btn_width/1.5), btn_height);
        BACK_BTN.setBackground(buttonColor);
        BACK_BTN.setFont(buttonFont);
        BACK_BTN.addActionListener(e -> {
            System.out.println("Zurück wurde ausgewählt.");

            Main.libraryPanel.setVisible(false);
            Main.window.remove(Main.libraryPanel);
            Main.window.repaint();
            Main.mainMenuPanel.setVisible(true);
            Main.window.add(Main.mainMenuPanel);
        });
        BACK_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                BACK_BTN.setText(">Zurück<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                BACK_BTN.setText("Zurück");
            }
        });

        NEXTPAGE_BTN = new JButton(">");
        NEXTPAGE_BTN.setBounds(width - margin - rightFiller - btn_width/2, height - bottomFiller - margin - btn_height, btn_width/2, btn_height);
        NEXTPAGE_BTN.setBackground(buttonColor);
        NEXTPAGE_BTN.addActionListener(e -> {
            try {
                initLib(currentPage + 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NEXTPAGE_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (CAM_BTN.isEnabled())
                    NEXTPAGE_BTN.setText("> > <");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                    NEXTPAGE_BTN.setText(">");
            }
        });

        PREVPAGE_BTN = new JButton("<");
        PREVPAGE_BTN.setBounds(width - margin - rightFiller - btn_width - margin, height - bottomFiller - margin - btn_height, btn_width/2, btn_height);
        PREVPAGE_BTN.setBackground(buttonColor);
        PREVPAGE_BTN.addActionListener(e -> {
            try {
                initLib(currentPage - 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        PREVPAGE_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (CAM_BTN.isEnabled())
                    PREVPAGE_BTN.setText("> < <");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                PREVPAGE_BTN.setText("<");
            }
        });

        add(NEXTPAGE_BTN);
        add(PREVPAGE_BTN);
        add(LIB_PNL);
        add(BACK_BTN);
        add(CAM_BTN);

        initLib(1);
        setVisible(true);
    }

    public void initLib(int page) throws IOException {
        currentPage = page;
        LIB_PNL.removeAll();
        ArrayList<BufferedImage> liblist_BI = new ArrayList<>();
        ArrayList<JButton> liblist_BTN = new ArrayList<>();

        File dir = new File("img");
        String[] pathnames;

        pathnames = dir.list();

        for (int i = 0; i < Objects.requireNonNull(pathnames).length; i++) {
            //System.out.println(pathnames[i]);

            BufferedImage bi = ImageIO.read(new File("img/" + pathnames[i]));
            liblist_BI.add(resizeImage(bi, PIC_SIZE, PIC_SIZE));

        }
        int count = 0;
        for (BufferedImage bi : liblist_BI) {
            count++;

            JButton b = new JButton(new ImageIcon(bi));
            liblist_BTN.add(b);

            if (count > PICS_ONPAGE*(page-1) && count <= PICS_ONPAGE*page) {
                if (count <= PICS_ONPAGE*(page-1) + PICS_ONPAGE/2) {

                    b.setBounds(margin + padding*(count-1-PICS_ONPAGE*(page-1)) + PIC_SIZE*(count-1-PICS_ONPAGE*(page-1)), margin, PIC_SIZE, PIC_SIZE);

                } else {
                    int i = count-5-PICS_ONPAGE*(page-1);
                    b.setBounds(margin + padding*(i-1) + PIC_SIZE*(i-1), margin + padding + PIC_SIZE, PIC_SIZE, PIC_SIZE);

                }
            }


            NEXTPAGE_BTN.setEnabled(liblist_BI.size() > page * 10);

            PREVPAGE_BTN.setEnabled(page != 1);


            LIB_PNL.add(b);
            LIB_PNL.repaint();
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

    public int getCurrentPage() {
        return currentPage;
    }
}
