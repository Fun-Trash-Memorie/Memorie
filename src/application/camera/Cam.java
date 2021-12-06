package application.camera;

import application.Main;
import application.sound.SoundSystem;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cam extends JFrame {

    private final JLabel CAM_LBL;
    private final JButton CAPTURE_BTN, EXIT_BTN;

    private VideoCapture cap;
    private Mat image;

    private BufferedImage img;

    private MatOfByte buf;

    private boolean cbClicked = false, ebClicked = false, cam = true;

    public Cam() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(640, 720));
        mainPanel.setBackground(Color.DARK_GRAY);
        add(mainPanel);

        CAM_LBL = new JLabel();
        CAM_LBL.setPreferredSize(new Dimension(640, 480));
        CAM_LBL.setBackground(Color.DARK_GRAY);
        mainPanel.add(CAM_LBL, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.lightGray);
        buttonPanel.setPreferredSize(new Dimension(640, 100));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        CAPTURE_BTN = new JButton("Klick!");
        CAPTURE_BTN.setPreferredSize(new Dimension(200, 50));

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
        buttonPanel.add(CAPTURE_BTN, BorderLayout.NORTH);

        EXIT_BTN = new JButton("Schließen");
        EXIT_BTN.setPreferredSize(new Dimension(200, 50));
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
        buttonPanel.add(EXIT_BTN, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(640, 720));
        setLocationRelativeTo(null);
        setBackground(Color.LIGHT_GRAY);
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

    public void startCam() throws IOException {

        System.out.println("Cam gestartet");

        cap = new VideoCapture(0);
        image = new Mat();
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

                    } catch (Exception e) {
                        System.err.println("Keine Kamera gefunden. Show Stack-trace?    [y][n]");
                        Scanner scanner = new Scanner(System.in);
                        String s = scanner.nextLine();
                        if (s.equals("y"))
                            e.printStackTrace();
                        scanner.close();
                    }

                    if (cbClicked) {
                        String name = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
                        Imgcodecs.imwrite("img/" + name + ".jpg", image);
                        cbClicked = false;
                        try {
                            Main.libraryPanel.initLib();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            /*
            if(isMatching(name)){
                System.out.println("Match!");

                cam = false;
            }*/
                    }
                    if (ebClicked) {
                        imageData = null;
                        image.release();
                        cap.release();
                        buf.release();
                        cam = false;
                    }
                }
            }
        }.start();


    }

    private boolean isMatching(String name) throws IOException {
        img = ImageIO.read(new File("img/" + name + ".jpg"));

        int xRange = 20, yRange = 20, xStart = img.getWidth() / 8, xEnd = img.getWidth() - xStart, yStart = img.getHeight() / 8, yEnd = img.getHeight() - yStart;

        System.out.println("Checking Top");
        for (int x = xStart - xRange / 2; x < xEnd + xRange / 2; x++) {
            for (int y = yStart - yRange / 2; y < yStart + yRange / 2; y++) {
                System.out.println(y + " " + x);
            }
        }
        System.out.println("Checking Left");
        for (int y = yStart - yRange / 2; y < yEnd + yRange / 2; y++) {
            for (int x = xStart - xRange / 2; x < xStart + xRange / 2; x++) {
                System.out.println(y + " " + x);
            }
        }
        System.out.println("Checking Bottom");
        for (int x = xStart - xRange / 2; x < xEnd + xRange / 2; x++) {
            for (int y = yEnd - yRange / 2; y < yEnd + yRange / 2; y++) {
                System.out.println(y + " " + x);
            }
        }
        System.out.println("Checking Right");
        for (int y = yStart - yRange / 2; y < yStart + yRange / 2; y++) {
            for (int x = xEnd - xRange / 2; x < xEnd + xRange / 2; x++) {
                System.out.println(y + " " + x);
            }
        }

        //int packedInt = img.getRGB(x,y);
        //Color color = new Color(packedInt, true);
        //System.out.println(color);


        return true;
    }
}