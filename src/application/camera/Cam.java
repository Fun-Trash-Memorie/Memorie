package application.camera;

import application.Main;
import application.sound.SoundSystem;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cam extends JFrame {

    private final JLabel CAM_LBL;
    private final JButton CAPTURE_BTN, EXIT_BTN;

    private VideoCapture cap;
    private Mat image;

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
            public void mouseReleased(MouseEvent  e) {
                CAPTURE_BTN.setBackground(Color.ORANGE);
            }
        });
        buttonPanel.add(CAPTURE_BTN, BorderLayout.NORTH);

        EXIT_BTN = new JButton("Schließen");
        EXIT_BTN.setPreferredSize(new Dimension(200, 50));
        EXIT_BTN.setBackground(Color.LIGHT_GRAY);
        EXIT_BTN.addActionListener(e -> ebClicked = true);
        EXIT_BTN.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                EXIT_BTN.setBackground(Color.gray);
            }
            @Override
            public void mouseReleased(MouseEvent  e) {
                EXIT_BTN.setBackground(Color.LIGHT_GRAY);
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
                System.out.println("Cam released");
            }
        });
    }

    public void startCam(){

        cap = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;

        while(cam) {
            cap.read(image);
            final MatOfByte buf = new MatOfByte();
            try {
                Imgcodecs.imencode(".jpg", image, buf);

            } catch (Exception e) {
                e.printStackTrace();
            }

            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            CAM_LBL.setIcon(icon);

            if(cbClicked) {
                String name = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
                Imgcodecs.imwrite("img/" + name + ".jpg", image);
                cbClicked = false;
            }

            if(ebClicked) {
                image.release();
                cap.release();
                buf.release();
                System.out.println("CamThread beendet.");
                ebClicked = false;
                cam = false;
                this.dispose();
            }
        }
    }
}