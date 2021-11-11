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

    private final JLabel camLabel;
    private final JButton captureButton;
    private final JButton exitButton;

    private VideoCapture cap;
    private Mat image;

    private boolean cbClicked = false, ebClicked = false, cam = true;

    public Cam() {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(640, 720));
        mainPanel.setBackground(Color.DARK_GRAY);
        add(mainPanel);

        camLabel = new JLabel();
        camLabel.setPreferredSize(new Dimension(640, 480));
        camLabel.setBackground(Color.DARK_GRAY);
        mainPanel.add(camLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBackground(Color.lightGray);
        buttonPanel.setPreferredSize(new Dimension(640, 100));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        captureButton = new JButton("Klick!");
        captureButton.setPreferredSize(new Dimension(200, 50));
        captureButton.setBackground(Color.ORANGE);
        captureButton.addActionListener(e -> cbClicked = true);
        captureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                captureButton.setBackground(Color.gray);
                Main.soundSystem = new SoundSystem("KameraKlickSound.wav");
            }
            @Override
            public void mouseReleased(MouseEvent  e) {
                captureButton.setBackground(Color.ORANGE);
            }
        });
        buttonPanel.add(captureButton, BorderLayout.NORTH);

        exitButton = new JButton("Schließen");
        exitButton.setPreferredSize(new Dimension(200, 50));
        exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.addActionListener(e -> ebClicked = true);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exitButton.setBackground(Color.gray);

            }
            @Override
            public void mouseReleased(MouseEvent  e) {
                exitButton.setBackground(Color.LIGHT_GRAY);
            }
        });
        buttonPanel.add(exitButton, BorderLayout.SOUTH);

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

        while(cam == true) {
            cap.read(image);
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            camLabel.setIcon(icon);

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