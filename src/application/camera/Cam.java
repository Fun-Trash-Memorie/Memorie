package application.camera;

import application.Main;
import application.sound.SoundSystem;
import application.window.game.ConstructionHelper;
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

    private final JPanel MAIN_PANEL;
    private final JPanel BTN_PANEL;
    private final JLabel CAM_LBL;
    private final JButton CAPTURE_BTN, EXIT_BTN;

    private VideoCapture cap;
    private Mat image, cropImage;
    private BufferedImage img;
    private MatOfByte buf;

    private boolean cbClicked = false, ebClicked = false, cam = true;

    private final BufferedImage filter_overlay;

    public Cam() throws IOException {


        filter_overlay = ImageIO.read(new File("src-img/Filter_Overlay.png"));

        setSize(new Dimension(640 + 2*margin + 2*padding + rightFiller, 580 + 2*margin + 5*padding + bottomFiller));
        setBackground(bg_color1);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MAIN_PANEL = new JPanel();
        MAIN_PANEL.setBounds(margin, margin, 640 + 2*padding, 580 + 5*padding);
        MAIN_PANEL.setBackground(bg_color2);
        MAIN_PANEL.setLayout(null);
        add(MAIN_PANEL);

        JButton capBTN = new JButton(new ImageIcon(filter_overlay));
        capBTN.setBounds(116, 36, 408, 408);
        capBTN.setOpaque(false);
        capBTN.setContentAreaFilled(false);
        capBTN.setBorderPainted(false);

        CAM_LBL = new JLabel();
        CAM_LBL.setBounds(padding, padding, 640, 480);
        CAM_LBL.setBackground(bg_color1);
        CAM_LBL.add(capBTN);

        MAIN_PANEL.add(CAM_LBL);

        BTN_PANEL = new JPanel();
        BTN_PANEL.setLayout(null);
        BTN_PANEL.setBackground(bg_color1);
        BTN_PANEL.setBounds(padding, 2*padding + 480, 640, 100 + 3*padding);
        MAIN_PANEL.add(BTN_PANEL);

        CAPTURE_BTN = new JButton("Klick!");
        CAPTURE_BTN.setBounds(padding, padding, 200, 50);

        CAPTURE_BTN.setBackground(Color.ORANGE);
        CAPTURE_BTN.addActionListener(e -> cbClicked = true);
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
        BTN_PANEL.add(CAPTURE_BTN, BorderLayout.NORTH);

        EXIT_BTN = new JButton("Schließen");
        EXIT_BTN.setBounds(padding, 2*padding + 50, 200, 50);
        EXIT_BTN.setBackground(Color.LIGHT_GRAY);
        EXIT_BTN.addActionListener(e -> {
        });
        EXIT_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                EXIT_BTN.setBackground(Color.gray);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                EXIT_BTN.setBackground(Color.LIGHT_GRAY);
                ebClicked = true;
                Main.cam.dispose();
            }
        });
        BTN_PANEL.add(EXIT_BTN);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
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

        cap = new VideoCapture(0);
        image = new Mat();
        cropImage = new Mat();
        new Thread() {
            byte[] imageData;
            ImageIcon icon;

            public void run() {
                boolean cam = Main.cam.cam;
                while (cam) {
                    cap.read(image);
                    //Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY, 0);
                    buf = new MatOfByte();
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

                    if (cbClicked) {
                        CAPTURE_BTN.setEnabled(false);

                        InputStream in = new ByteArrayInputStream(buf.toArray());
                        try {
                            img = ImageIO.read(in);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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
                        try {
                            Main.libraryPanel.initLib(Main.libraryPanel.getCurrentPage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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

        int x = filter_overlay.getWidth();  // 408px
        int y = filter_overlay.getHeight(); // 408px

        boolean match = false;
        int matches = 0;

        for (int i = (cap_width-x)/2; i < x; i++) {
            for (int j = (cap_height-y)/2; j < y; j++) {
                int f_pixel = filter_overlay.getRGB(i, j);
                Color f_c = new Color(f_pixel, true);

                int pixel = image.getRGB(i, j);
                Color c = new Color(pixel, true);

                //System.out.println(i + " " + j);
                //System.out.println(f_c.getRed() + " " + f_c.getGreen() + " " + f_c.getBlue());
                if (f_c.getRed() == 229) {

                    //System.out.println(c.getRed() + " " + c.getGreen() + " " + c.getBlue());

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
        System.out.println(filter_overlay.getWidth() + " " + filter_overlay.getHeight());
        return matches > 1000;
    }


}