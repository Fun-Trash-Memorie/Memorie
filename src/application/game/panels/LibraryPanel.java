package application.game.panels;

import application.Main;
import application.game.camera.Cam;
import application.game.sound.SoundSystem;
import application.window.ConstructionHelper;
import org.opencv.core.CvException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LibraryPanel extends JPanel implements ConstructionHelper {

    private static int currentPage = 1;

    private final JButton CAM_BTN, BACK_BTN, NEXTPAGE_BTN, PREVPAGE_BTN, EXPLORER_BTN;

    public final JPanel LIB_PNL;

    private final JLabel INDEX_LBL;

    private JFileChooser fileChooser;


    private final int LIB_WIDTH = width-rightFiller-2*margin;
    private final int LIB_HEIGHT = height-margin*2-btn_height-padding-bottomFiller;

    //private final int PIC_SIZE = (LIB_WIDTH - 2*margin - 4*padding)/5;
    private final int PIC_SIZE = 200;

    private final int PICS_ONPAGE = 8;

    public ArrayList<JButton> liblist_BTN;

    public LibraryPanel() throws IOException {

        //Einstellungen für die Bildergalerie
        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);

        // Panel für die Bilder wird erstellt
        LIB_PNL = new JPanel();
        LIB_PNL.setBackground(bg_color2);
        LIB_PNL.setBounds(margin, margin, LIB_WIDTH, LIB_HEIGHT);
        LIB_PNL.setLayout(null);

        // Erstellen und Einstellen des Buttons "Cam"
        CAM_BTN = new JButton("Cam");
        CAM_BTN.setBounds(width/2, height-btn_height-bottomFiller-margin, btn_width/2, btn_height);
        CAM_BTN.setBackground(buttonColor);
        CAM_BTN.setFont(buttonFont);
        // Befehl auf Knopfdruck: Das Kamera-Fenster wird geöffnet
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
        // Hervorhebung des Textes beim Hovern
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

        //Erstellen und Einstellen des Buttons "Explorer"
        EXPLORER_BTN = new JButton("Explorer");
        EXPLORER_BTN.setBounds(width/2 - (btn_width*2)/3 - padding, height-btn_height-bottomFiller-margin, (2*btn_width)/3, btn_height);
        EXPLORER_BTN.setBackground(buttonColor);
        EXPLORER_BTN.setFont(buttonFont);

        // Befehl auf Knopfdruck: Explorer zum Einfügen neuer Bilder wird geöffnet
        EXPLORER_BTN.addActionListener(e -> {
            fileChooser = new JFileChooser("c:/");
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    } else {
                        return f.getName().toLowerCase().endsWith(".jpg") | f.getName().toLowerCase().endsWith(".png");
                    }
                }
                @Override
                public String getDescription() {
                    return "JPEG/PNG (*.jpg;*.jpeg;*.jpe;*.jfif;*.png;*.PNG)";
                }
            });

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();

                System.out.println(selectedPath + " wurde geöffnet.");

                try {
                    Files.copy(selectedFile.toPath(), (new File("img/" + selectedFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Erstellen und Einstellen des Buttons "Zurück"
        BACK_BTN = new JButton("Zurück");

        BACK_BTN.setBounds(margin, height-btn_height-bottomFiller-margin, (int)(btn_width/1.5), btn_height);
        BACK_BTN.setBackground(buttonColor);
        BACK_BTN.setFont(buttonFont);
        // Befehl auf Knopfdruck: Bildergalerie wird durch Hauptmenü ersetzt
        BACK_BTN.addActionListener(e -> {
            System.out.println("Zurück wurde ausgewählt.");
            Main.soundSystem = new SoundSystem("BackButtonTon.wav");

            Main.libraryPanel.setVisible(false);
            Main.window.remove(Main.libraryPanel);
            Main.window.repaint();
            Main.mainMenuPanel.setVisible(true);
            Main.window.add(Main.mainMenuPanel);
        });
        // Hervorhebung des Textes beim Hovern
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

        // Erstellen und Einstellen des Buttons ">"
        NEXTPAGE_BTN = new JButton(">");
        NEXTPAGE_BTN.setBounds(width - margin - rightFiller - btn_width/2, height - bottomFiller - margin - btn_height, btn_width/2, btn_height);
        NEXTPAGE_BTN.setBackground(buttonColor);
        NEXTPAGE_BTN.setFont(buttonFont);
        // Befehl auf Knopfdruck: Die nächste Seite der Bildergalerie wird angezeigt
        NEXTPAGE_BTN.addActionListener(e -> {
            try {
                initLib(currentPage + 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        // Hervorhebung des Textes beim Hovern
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

        // Erstellen und Einstellen des Buttons "<"
        PREVPAGE_BTN = new JButton("<");
        PREVPAGE_BTN.setBounds(width - margin - rightFiller - btn_width - margin, height - bottomFiller - margin - btn_height, btn_width/2, btn_height);
        PREVPAGE_BTN.setBackground(buttonColor);
        PREVPAGE_BTN.setFont(buttonFont);
        // Befehl auf Knopfdruck: Die vorherige Seite der Bildergalerie wird angezeigt
        PREVPAGE_BTN.addActionListener(e -> {
            try {
                initLib(currentPage - 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        // Hervorhebung des Textes beim Hovern
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

        // Label zum Anzeigen der aktuellen Seite wird erstellt
        INDEX_LBL = new JLabel();
        INDEX_LBL.setBounds(LIB_WIDTH / 2, padding*3 + PIC_SIZE*2, 15, 30);
        INDEX_LBL.setBackground(bg_color2);
        INDEX_LBL.setForeground(Color.WHITE);

        // (fast) alle Inhalte der Bildergalerie werden eingefügt (Bilder im Bilder-Panel sowie die Seitenzahl kommen mit der Methode "initLib")
        add(NEXTPAGE_BTN);
        add(PREVPAGE_BTN);
        add(LIB_PNL);
        add(BACK_BTN);
        add(CAM_BTN);
        add(EXPLORER_BTN);

        initLib(1);
        setVisible(true);
    }

    // Bilder der aktuellen Seite werden geladen und die aktuelle Seitenzahl wird angezeigt
    public void initLib(int page) throws IOException {

        // aktuelle Seitenzahl wird gespeichert
        currentPage = page;
        INDEX_LBL.setText(Integer.toString(currentPage));

        // aktuell angezeigte Bilder werden entfernt
        LIB_PNL.removeAll();

        // drei Listen werden erstellt:
        //      "liblist_BI" für die Bilder
        //      "liblist_BTN" für die Buttons, auf denen nachher die Bilder dargestellt werden
        //  und "liblist_URL" für die Strings der die URL -> den Pfad zu den Bilddateien
        ArrayList<BufferedImage> liblist_BI = new ArrayList<>();
        liblist_BTN = new ArrayList<>();
        ArrayList<String> liblist_URL = new ArrayList<>();

        // Pfad für die Bilder wird auf den "img" Ordner gestellt und Bildpfade werden in die "pathnames" Liste gespeichert
        File dir = new File("img");
        String[] pathnames;
        pathnames = dir.list();

        // Bilder werden in die "liblist_BI" Liste gespeichert
        for (int i = 0; i < Objects.requireNonNull(pathnames).length; i++) {
            //System.out.println(pathnames[i]);

            BufferedImage bi = ImageIO.read(new File("img/" + pathnames[i]));
            liblist_URL.add("img/" + pathnames[i]);
            liblist_BI.add(resizeImage(bi, PIC_SIZE, PIC_SIZE));

        }

        // Buttons werden mit den Bildern in die Liste "liblist_BTN" eingefügt
        int count = 0;
        for (BufferedImage bi : liblist_BI) {
            int c = count;
            count++;

            // einzelner Button wird mit einem Bild erstellt
            JButton b = new JButton(new ImageIcon(bi));
            // Befehl auf Knopfdruck: Bildansicht wird in einem neuen Fenster geöffnet
            b.addActionListener(e -> {
                Main.window.setEnabled(false);
                try {
                    Main.subWindow = new PictureView(liblist_URL.get(c), c);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            // Button wird in die Liste "liblist_BTN" gespeichert
            liblist_BTN.add(b);

            // Button wird in die richtige Position gebracht
            if (count > PICS_ONPAGE*(page-1) && count <= PICS_ONPAGE*page) {
                if (count <= PICS_ONPAGE*(page-1) + PICS_ONPAGE/2) {
                    b.setBounds(margin + padding*(count-1-PICS_ONPAGE*(page-1)) + PIC_SIZE*(count-1-PICS_ONPAGE*(page-1)), margin, PIC_SIZE, PIC_SIZE);
                } else {
                    int i = count-PICS_ONPAGE/2-PICS_ONPAGE*(page-1);
                    b.setBounds(margin + padding*(i-1) + PIC_SIZE*(i-1), margin + padding + PIC_SIZE, PIC_SIZE, PIC_SIZE);
                }
            }

            // Blättern der Seiten wird auf Verfügbarkeit überprüft
            NEXTPAGE_BTN.setEnabled(liblist_BI.size() > page * 10);
            PREVPAGE_BTN.setEnabled(page != 1);

            // Seitenzahl sowie Bilder werden angezeigt
            LIB_PNL.add(b);
            LIB_PNL.add(INDEX_LBL);
            LIB_PNL.repaint();
        }
    }

    /**
     *
     * @Source https://www.baeldung.com/java-resize-image
     *
     */
    // Methode zum korrekten zuschneiden von Bildern
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {

        // Neues Bild wird erstellt
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        // Neues Bild wird gezeichnet
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        // Neues Bild wird zurückgegeben
        return resizedImage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
