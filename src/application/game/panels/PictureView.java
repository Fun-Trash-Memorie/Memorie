package application.game.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import application.Main;
import application.game.engine.PictureFinder;
import application.window.ConstructionHelper;
import application.window.SubWindow;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PictureView extends SubWindow implements ConstructionHelper {

    private final int WIDTH = (width-rightFiller)/2;
    private final int HEIGHT = (height-bottomFiller);

    private final JLabel DISPLAY_LBL;
    private final JButton BACK_BTN, CHECKFORDOUBLE_BTN, DELETE_BTN;

    private static Robot robot;
    private static File temp;

    public PictureView(String path, int id) throws IOException {
        super("Motiv " + id);

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
        DISPLAY_LBL = new JLabel(new ImageIcon(ImageIO.read(new File(path))));
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
            // Subfenster und der benutzte Button(Bild) wird unsichtbar gemacht
            setVisible(false);
            Main.libraryPanel.liblist_BTN.get(id).setVisible(false);
            // Das Hauptfenster wird in der Vordergrund gebracht
            setVisible(false);
            Main.window.toFront();
            Main.window.requestFocus();

            new Thread(() -> {
                try {
                    Thread.sleep(400);  // Es muss eine zeit lang gewartet werden, bis das SubFenster sich unsichtbar gemacht hat,
                    robot = new Robot();     // damit der robot den Screen vom Hauptfenster ungestört machen kann
                } catch (InterruptedException | AWTException ex) {
                    ex.printStackTrace();
                }

                // Die Position des Inneren des Hauptfensters wird bestimmt,
                // sowie der Rahmen des zu erstellenden Screenshots
                Insets insets = Main.window.getInsets();
                int x = Main.window.getLocationOnScreen().x + insets.left + margin;
                int y = Main.window.getLocationOnScreen().y + insets.top + margin;
                int w = Main.libraryPanel.LIB_PNL.getWidth();
                int h = Main.libraryPanel.LIB_PNL.getHeight();

                Rectangle captureRec = new Rectangle(x, y, w, h);

                // Der Screenshot wird erstellt und gespeichert
                BufferedImage screenshot = robot.createScreenCapture(captureRec);
                File temp = new File("temp/screenshot");
                try {
                    ImageIO.write(screenshot,"png", temp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // Nun kann das SubFenster sowie der Button wieder sichtbar gemacht werden
                setVisible(true);
                Main.libraryPanel.liblist_BTN.get(id).setVisible(true);

                // Nun wird die PictureFinder klasse neu instanziiert mit dem Pfad des screenshots und des gesuchten Bildes
                Main.picFinder = new PictureFinder(temp.toString(), path);

                // Anschließend wird der Match-Punkt sowie das Objekt was sich an dieser Stelle befindet ausgegeben
                // Der Punkt wird leicht um jeweils 5px verschoben,
                // damit das gefundene Objekt der jeweilige Button und nicht das Panel ist
                Point matchPoint = new Point(Main.picFinder.matchX + 5, Main.picFinder.matchY + 5);
                System.out.println("match x, y: " + matchPoint.x + ", " + matchPoint.y);
                Component compIWant = Main.libraryPanel.LIB_PNL.getComponentAt(matchPoint);
                System.out.println(compIWant);

                // Zur visualisierung des gefundenen knopfes, wird dieser geschrumpft
                compIWant.setSize(100, 100);

                // die URL -> der Pfad der Textur des Zielknopfes wird bestimmt und verglichen mit dem
                // Pfad des Templates
                JButton buttonIWant = (JButton)compIWant;
                int indexIWant = Main.libraryPanel.liblist_BTN.indexOf(buttonIWant);
                String URLIWant = Main.libraryPanel.liblist_URL.get(indexIWant);

                System.out.println(indexIWant);
                System.out.println(URLIWant);
                System.out.println(path);

                // Da die Bilder in der Galerie künstlich doppelt im Verzeichnis liegen,
                // wird immer der Fall immer "false" ergeben.
                // Im richtigen Spiel würde das Bild nur einmal im Verzeichnis liegen aber 2mal geladen werden.
                if ((URLIWant.equals(path))) {
                    System.out.println("Es ist ein Match!");
                } else {
                    System.out.println("Es ist kein Match. :(");
                }

                // der temporäre screenshot wird wieder freigegeben
                try {
                    Files.deleteIfExists(Paths.get(String.valueOf(temp)));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setVisible(true);
            }).start();




        });
        add(CHECKFORDOUBLE_BTN);

        // Button zum Löschen eines Bildes (momentan nicht implementiert)
        DELETE_BTN = new JButton();
        DELETE_BTN.setBackground(Color.red);
        DELETE_BTN.setText("Löschen");
        DELETE_BTN.setBounds(margin + 2*(WIDTH - 2*margin) / 4 + padding, HEIGHT - margin + padding/2 - height/7, (WIDTH-2*margin) / 4, 60);
        DELETE_BTN.addActionListener(e -> {


            this.setVisible(false);
            Main.window.remove(this);
            Main.window.repaint();
        });
        //add(DELETE_BTN);


        setVisible(true);
    }

}
