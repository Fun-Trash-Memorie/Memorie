package application.game.camera;

import application.Main;
import application.game.sound.SoundSystem;
import application.window.ConstructionHelper;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cam extends JFrame implements ConstructionHelper {

    private final JPanel MAIN_PNL, BTN_PNL;
    private final JLabel CAM_LBL;
    private final JButton CAPTURE_BTN, EXIT_BTN;

    private VideoCapture cap;
    private Mat image, cropImage;
    private BufferedImage img;
    private MatOfByte buf;

    private boolean cbClicked = false, ebClicked = false, cam = true;

    private final BufferedImage FILTER_OVERLAY;

    public Cam() throws IOException {

        //Rahmen für das Auschneiden des Bildes
        FILTER_OVERLAY = ImageIO.read(new File("src-img/Filter_Overlay.png"));

        //Anpassungen für das Kamera-Fenster
        setSize(new Dimension(640 + 2*margin + 2*padding + rightFiller, 580 + 2*margin + 5*padding + bottomFiller));
        setBackground(bg_color1);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel für das Kamerabild wird erstellt
        MAIN_PNL = new JPanel();
        MAIN_PNL.setBounds(margin, margin, 640 + 2*padding, 580 + 5*padding);
        MAIN_PNL.setBackground(bg_color2);
        MAIN_PNL.setLayout(null);
        add(MAIN_PNL);

        // Rahmen zum Ausschneiden des Bildes wird erstellt
        JButton capBTN = new JButton(new ImageIcon(FILTER_OVERLAY));
        capBTN.setBounds(116, 36, 408, 408);
        capBTN.setOpaque(false);
        capBTN.setContentAreaFilled(false);
        capBTN.setBorderPainted(false);

        // Label wird erstellt (wird später für den Kamera-Output benutzt)
        CAM_LBL = new JLabel();
        CAM_LBL.setBounds(padding, padding, 640, 480);
        CAM_LBL.setBackground(bg_color1);

        // Panel für die Kamera wird mit Label ins Fenster eingefügt
        CAM_LBL.add(capBTN);
        MAIN_PNL.add(CAM_LBL);

        // Label für die Buttons wird erstellt und ins Fenster eingefügt
        BTN_PNL = new JPanel();
        BTN_PNL.setLayout(null);
        BTN_PNL.setBackground(bg_color1);
        BTN_PNL.setBounds(padding, 2*padding + 480, 640, 100 + 3*padding);
        MAIN_PNL.add(BTN_PNL);

        // Erstellen und Einstellen des Buttons "Klick!"
        CAPTURE_BTN = new JButton("Klick!");
        CAPTURE_BTN.setBounds(padding, padding, 200, 50);
        CAPTURE_BTN.setBackground(Color.ORANGE);
        // Befehl auf Knopfdruck: "cbClicked" wird auf true gesetzt (resultiert in der Aufnahme des Bildes)
        CAPTURE_BTN.addActionListener(e -> cbClicked = true);
        // Während der Button gedrückt ist wird dessen Hintergrund verändert und ein Soundeffekt wird abgespielt
        CAPTURE_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                CAPTURE_BTN.setBackground(Color.gray);
                Main.soundSystem = new SoundSystem("KameraKlickSound.wav");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                CAPTURE_BTN.setBackground(Color.ORANGE);
            }
        });
        // Der Button "Klick!" wird hinzugefügt
        BTN_PNL.add(CAPTURE_BTN, BorderLayout.NORTH);

        // Erstellen und Einstellen des Buttons "Schließen"
        EXIT_BTN = new JButton("Schließen");
        EXIT_BTN.setBounds(padding, 2*padding + 50, 200, 50);
        EXIT_BTN.setBackground(buttonColor);
        // Während der Knopf gedrückt wird verändert sich der Hintergrund des Buttons; Danach wird das Fenster geschlossen
        EXIT_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                EXIT_BTN.setBackground(Color.gray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                EXIT_BTN.setBackground(buttonColor);
                ebClicked = true;
                Main.cam.dispose();
            }
        });
        // Button "Schließen" wird hinzugefügt
        BTN_PNL.add(EXIT_BTN);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                image.release();
                cap.release();
                buf.release();
                cam = false;
                System.out.println("Cam released");
            }
        });
    }

    public void startCam() {

        System.out.println("Cam gestartet");

        // Standard Webcam wird ausgewählt
        cap = new VideoCapture(0);
        image = new Mat();
        cropImage = new Mat();
        // Hauptteil der Methode wird als Thread ausgeführt
        new Thread() {
            byte[] imageData;
            ImageIcon icon;

            public void run() {
                boolean cam = Main.cam.cam;
                while (cam) {
                    // Aktueller Frame der Webcam wird gespeichert
                    cap.read(image);

                    //Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY, 0);    // sets the image to gray-values

                    buf = new MatOfByte();
                    // Frame wird umgewandelt und in das "CAM_LBL" Label gesetzt
                    try {
                        Imgcodecs.imencode(".jpg", image, buf);

                        imageData = buf.toArray();

                        icon = new ImageIcon(imageData);

                        CAM_LBL.setIcon(icon);
                        //CAM_LBL.setBounds(padding, padding, icon.getIconWidth(), icon.getIconHeight());

                    } catch (Exception e) {
                        System.err.println("Keine Kamera gefunden. Show Stack-trace?    [y][n]");
                        Scanner scanner = new Scanner(System.in);
                        String s = scanner.nextLine();
                        if (s.equals("y"))
                            e.printStackTrace();
                        scanner.close();
                    }

                    // Beim Drücken vom "Zurück" Button wird das Bild in der Bildergalerie gespeichert
                    if (cbClicked) {
                        CAPTURE_BTN.setEnabled(false);

                        // aktueller Frame wird zwischengespeichert
                        InputStream in = new ByteArrayInputStream(buf.toArray());
                        try {
                            img = ImageIO.read(in);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Bild wird überprüft und im "img" Ordner abgespeichert
                        if (isMatching(img)) {

                            BufferedImage subimg = img.getSubimage((640-400)/2, (480-400)/2, 400, 400);

                            byte[] pixels = ((DataBufferByte) subimg.getRaster().getDataBuffer()).getData();
                            cropImage.put(0, 0, pixels);

                            String name = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());

                            try {
                                ImageIO.write(subimg, "jpg", new File("img/" + name + ".jpg"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        cbClicked = false;
                        CAPTURE_BTN.setEnabled(true);
                        // Seite in der Bildergalerie wird neu geladen mit neuem Bild
                        try {
                            Main.libraryPanel.initLib(Main.libraryPanel.getCurrentPage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // Fenster wird zum Schließen vorbereitet
                    if (ebClicked) {
                        imageData = null;
                        image.release();
                        cropImage.release();
                        cap.release();
                        buf.release();
                        cam = false;
                    }
                }
            }
        }.start();


    }



    private boolean isMatching(BufferedImage image) {

        int cap_width = 640;
        int cap_height = 480;

        int x = FILTER_OVERLAY.getWidth();  // 408px
        int y = FILTER_OVERLAY.getHeight(); // 408px

        boolean match = false;
        int matches = 0;

        for (int i = (cap_width-x)/2; i < x; i++) {
            for (int j = (cap_height-y)/2; j < y; j++) {

                int f_pixel = FILTER_OVERLAY.getRGB(i, j);
                Color f_c = new Color(f_pixel, true);

                int pixel = image.getRGB(i, j);
                Color c = new Color(pixel, true);

                if (f_c.getRed() == 229) {

                    if (c.getRed()-10 > c.getGreen() && c.getRed()-10 > c.getBlue()) {
                        match = true;
                    }

                    if (match) {
                        matches++;
                        match = false;
                    }
                }
            }
        }
        System.out.println(matches);
        System.out.println(FILTER_OVERLAY.getWidth() + " " + FILTER_OVERLAY.getHeight());
        return matches > 1000;
    }


    public boolean picMatcher(BufferedImage filter, BufferedImage screenshot) {

        int w_filter = filter.getWidth();
        int h_filter = filter.getHeight();

        int w_screenshot = screenshot.getWidth();
        int h_screenshot = screenshot.getHeight();

        int size_filter = w_filter * h_filter;
        int matches = 0;

        System.out.println("Size: " + size_filter);

        for (int moveX = 0; moveX < w_screenshot-w_filter && matches<size_filter; moveX++) {
            for (int moveY = 0; moveY < h_screenshot-h_filter && matches<size_filter; moveY++) {

                for (int x = moveX; x < w_filter; x++) {
                    for (int y = moveY; y < h_filter; y++) {

                        if (screenshot.getRGB(x,y) == filter.getRGB(x-moveX,y-moveY)) {
                            matches++;
                        }

                    }
                }

            }
        }

        System.out.println("Matches: " + matches);

        if (matches == size_filter) {
            return true;
        } else {
            return false;
        }
    }
}